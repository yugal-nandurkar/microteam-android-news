
package microteam.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import microteam.data.repository.ArticlesRepository
import microteam.data.repository.UserPreferencesRepository
import microteam.domain.mapper.toArticleUi
import microteam.domain.model.ArticleUi

class GetAllArticlesUseCase(
    private val articlesRepository: ArticlesRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    operator fun invoke(title: String? = null): Flow<List<ArticleUi>> {
        val allArticlesFlow =
            if (title.isNullOrEmpty()) {
                articlesRepository.getAllArticles()
            } else {
                articlesRepository.getAllArticlesByTitle(title)
            }

        val readArticlesFlow = userPreferencesRepository.getReadArticles()
        val bookmarkArticlesFlow = userPreferencesRepository.getBookmarkArticles()

        val combineFlow =
            combine(
                allArticlesFlow,
                readArticlesFlow,
                bookmarkArticlesFlow,
            ) { allArticles, readArticleIds, bookmarkArticleIds ->

                val allArticleUis = mutableListOf<ArticleUi>()

                for (article in allArticles) {
                    val isArticleRead = readArticleIds.contains(article.id)
                    val isArticleBookmarked = bookmarkArticleIds.contains(article.id)
                    val articleUi = article.toArticleUi(isArticleBookmarked, isArticleRead)
                    allArticleUis.add(articleUi)
                }

                allArticleUis.toList()
            }

        return combineFlow
    }
}
