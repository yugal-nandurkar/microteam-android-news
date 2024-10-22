
package microteam.ui.screens.common

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import microteam.R
import microteam.domain.model.ArticleUi
import microteam.domain.utils.ArticleUiUtils

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesScreen(
    articleUis: List<ArticleUi>,
    noArticlesDescStrResId: Int,
    isRefreshing: Boolean,
    searchQuery: String,
    isSearching: Boolean,
    navigateToArticle: (ArticleUi) -> Unit,
    onRefresh: () -> Unit,
    onBookmarkClick: (ArticleUi) -> Unit,
    onReadClick: (ArticleUi) -> Unit,
) {
    if (articleUis.isEmpty()) {
        if (searchQuery.isBlank()) {
            NoArticlesScreen(R.string.no_articles, noArticlesDescStrResId)
        } else {
            NoArticlesScreen(R.string.no_articles, R.string.no_search_articles_desc)
        }
        return
    }

    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)
    Box(Modifier.pullRefresh(pullRefreshState)) {
        val context = LocalContext.current
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            items(items = articleUis) { article ->
                ArticleCard(
                    articleUi = article,
                    onArticleCardClick = navigateToArticle,
                    onBookmarkClick = onBookmarkClick,
                    onShareClick = { _article ->
                        shareArticle(context, _article.link)
                    },
                    onReadClick = onReadClick,
                )
            }
        }

        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }

    if (isSearching) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

private fun shareArticle(
    context: Context,
    link: String,
) {
    val sendIntent: Intent =
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
    val shareIntent = Intent.createChooser(sendIntent, null)
    ContextCompat.startActivity(context, shareIntent, null)
}

@Preview(showBackground = true)
@Composable
private fun ArticlesScreenPreview() {
    ArticlesScreen(
        articleUis = ArticleUiUtils.makeFakeArticles(),
        noArticlesDescStrResId = R.string.no_articles_desc,
        searchQuery = "",
        isRefreshing = false,
        isSearching = false,
        navigateToArticle = {},
        onRefresh = {},
        onBookmarkClick = {},
        onReadClick = {},
    )
}
