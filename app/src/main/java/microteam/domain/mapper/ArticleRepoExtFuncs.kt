
package microteam.domain.mapper

import microteam.data.mapper.toUrlPath
import microteam.data.repository.ArticleRepo
import microteam.domain.model.ArticleUi

fun List<ArticleRepo>.toArticleUiList(): List<ArticleUi> {
    return map { articleRepo ->
        articleRepo.toArticleUi()
    }
}

fun ArticleRepo.toArticleUi(
    bookmarked: Boolean = false,
    read: Boolean = false,
): ArticleUi {
    return ArticleUi(
        id = link.toUrlPath(),
        title = title,
        link = link,
        author = author,
        pubDate = pubDate,
        image = image,
        feedTitle = feedTitle,
        bookmarked = bookmarked,
        read = read,
    )
}
