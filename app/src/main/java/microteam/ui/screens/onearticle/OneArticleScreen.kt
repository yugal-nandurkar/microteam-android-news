
package microteam.ui.screens.onearticle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import microteam.R
import microteam.ui.screens.common.NoArticlesScreen
import microteam.ui.screens.common.UrlWebView

@Composable
fun ArticleScreen(viewModel: OneArticleViewModel) {
    val article by viewModel.article.collectAsStateWithLifecycle()

    if (article != null) {
        UrlWebView(url = article!!.link)
    } else {
        NoArticlesScreen(R.string.invalid_article, R.string.invalid_article_desc)
    }
}
