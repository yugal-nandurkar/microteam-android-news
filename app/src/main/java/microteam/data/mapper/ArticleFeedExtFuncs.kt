
package microteam.data.mapper

import microteam.data.local.ArticleEntity
import microteam.data.remote.ArticleFeed
import microteam.utils.Utils

fun List<ArticleFeed>.toArticleEntities(): List<ArticleEntity> {
    return map { feedItem ->
        feedItem.toArticleEntity()
    }
}

fun ArticleFeed.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = feedItem.link.toUrlPath(),
        title = feedItem.title,
        link = feedItem.link,
        author = feedItem.author,
        pubDate = Utils.parsePubDateStringToLong(feedItem.pubDate),
        image = feedItem.image,
        feedTitle = feedTitle,
    )
}
