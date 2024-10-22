
package microteam.ui.main.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import microteam.ui.screens.bookmarks.BookmarkArticlesViewModel

@Composable
fun BookmarkedArticlesTopBar(
    navHostController: NavHostController,
    bookmarkArticlesViewModel: BookmarkArticlesViewModel,
) {
    val searchQuery by bookmarkArticlesViewModel.searchQuery.collectAsStateWithLifecycle()

    ArticlesTopBar(
        navHostController,
        searchQuery,
        bookmarkArticlesViewModel::onSearchQuery,
    )
}
