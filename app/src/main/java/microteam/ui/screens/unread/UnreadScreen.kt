
package microteam.ui.screens.unread

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import microteam.R
import microteam.domain.model.ArticleUi
import microteam.domain.model.ArticlesUiState
import microteam.ui.screens.common.ArticlesScreen

@Composable
fun UnreadScreen(
    viewModel: UnreadArticlesViewModel,
    navigateToArticle: (ArticleUi) -> Unit,
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

    val articles by viewModel.articles.collectAsStateWithLifecycle()
    if (articles != null) {
        val uiState: ArticlesUiState by viewModel.uiState.collectAsStateWithLifecycle()

        ArticlesScreen(
            articleUis = articles!!,
            noArticlesDescStrResId = R.string.no_unread_articles_desc,
            isRefreshing = (uiState is ArticlesUiState.Loading),
            searchQuery = searchQuery,
            isSearching = isSearching,
            navigateToArticle = navigateToArticle,
            onRefresh = viewModel::refresh,
            onBookmarkClick = viewModel::onBookmarkClick,
            onReadClick = viewModel::onReadClick,
        )
    }
}
