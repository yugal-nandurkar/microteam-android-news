
package microteam.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import microteam.data.local.ArticleEntity
import microteam.data.local.ArticlesDatabase
import microteam.data.mapper.toArticleEntities
import microteam.data.mapper.toArticleEntity
import microteam.data.mapper.toArticleRepo
import microteam.data.mapper.toArticleRepoList
import microteam.data.remote.ArticleFeed
import microteam.data.remote.FeedParser
import microteam.data.remote.OkHttpWebService
import microteam.data.remote.WebService

@Suppress("function-start-of-body-spacing")
class ArticlesRepositoryImpl private constructor(
    private val database: ArticlesDatabase,
    private val webService: WebService,
) : ArticlesRepository {
    companion object {
        @Volatile
        private lateinit var instance: ArticlesRepository

        fun getInstance(context: Context): ArticlesRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance =
                        ArticlesRepositoryImpl(
                            ArticlesDatabase.getInstance(context.applicationContext),
                            OkHttpWebService(),
                        )
                }
                return instance
            }
        }
    }

    private val urls =
        listOf(
            "https://vtsen.hashnode.dev/rss.xml",
        )

    private var newArticlesFound: Boolean = false

    private var _status: ArticlesRepoStatus = ArticlesRepoStatus.Invalid

    override fun getStatus(): Flow<ArticlesRepoStatus> {
        return flow {
            while (true) {
                delay(500)
                emit(_status)
            }
        }
    }

    override fun getAllArticles(): Flow<List<ArticleRepo>> =
        database.selectAllArticles().map { articlesEntity ->
            articlesEntity.toArticleRepoList()
        }

    override suspend fun refresh(): ArticlesRepoStatus =
        withContext(Dispatchers.IO) {
            if (_status != ArticlesRepoStatus.IsLoading) {
                newArticlesFound = false
                _status = ArticlesRepoStatus.IsLoading

                try {
                    val articlesFeed = fetchArticlesFeed()
                    updateDatabase(articlesFeed.toArticleEntities())
                    _status = ArticlesRepoStatus.Success(newArticlesFound)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _status = ArticlesRepoStatus.Fail
                }
            }

            return@withContext _status
        }

    override fun clearStatus() {
        _status = ArticlesRepoStatus.Invalid
    }

    override suspend fun updateArticle(article: ArticleRepo) =
        withContext(Dispatchers.IO) {
            database.updateArticle(article.toArticleEntity())
        }

    override fun selectArticleById(id: String) =
        database.selectArticleById(id).map { articlesEntity ->
            articlesEntity?.toArticleRepo()
        }

    override fun getAllArticlesByTitle(title: String) =
        database.selectAllArticlesByTitle(title).map { articlesEntity ->
            articlesEntity.toArticleRepoList()
        }

    private suspend fun fetchArticlesFeed(): List<ArticleFeed> =
        coroutineScope {
            val results = mutableListOf<ArticleFeed>()
            val jobs = mutableListOf<Job>()

            for (url in urls) {
                val job =
                    launch {
                        val xmlString = webService.getXMlString(url)
                        val articleFeeds = FeedParser().parse(xmlString)
                        results.addAll(articleFeeds)
                    }

                jobs.add(job)
            }

            jobs.joinAll()

            return@coroutineScope results
        }

    private suspend fun updateDatabase(articleEntities: List<ArticleEntity>) =
        coroutineScope {
            for (articleEntity in articleEntities) {
                launch {
                    val articleFound = database.getArticleById(articleEntity.id)

                    if (articleFound == null) {
                        database.insertArticle(articleEntity)
                        newArticlesFound = true
                    } else {
                        database.updateArticle(articleEntity)
                    }
                }
            }
        }
}
