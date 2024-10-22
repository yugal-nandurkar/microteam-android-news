
package microteam.data.mapper

import microteam.data.local.ArticleEntity
import microteam.data.repository.ArticleRepo

fun ArticleRepo.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = link.toUrlPath(),
        title = title,
        link = link,
        author = author,
        pubDate = pubDate,
        image = image,
        feedTitle = feedTitle,
    )
}
