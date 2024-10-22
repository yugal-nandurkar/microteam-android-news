
package microteam.data.mapper

import microteam.data.local.ArticleEntity
import microteam.data.repository.ArticleRepo

fun List<ArticleEntity>.toArticleRepoList(): List<ArticleRepo> {
    return map { articleEntity ->
        articleEntity.toArticleRepo()
    }
}

fun ArticleEntity.toArticleRepo(): ArticleRepo {
    return ArticleRepo(
        id = link.toUrlPath(),
        title = title,
        link = link,
        author = author,
        pubDate = pubDate,
        image = image,
        feedTitle = feedTitle,
    )
}
