
package microteam.ui.main.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import microteam.ui.screens.unread.UnreadArticlesViewModel

@Composable
fun UnreadArticlesTopBar(
    navHostController: NavHostController,
    unreadArticlesViewModel: UnreadArticlesViewModel,
) {
    val searchQuery by unreadArticlesViewModel.searchQuery.collectAsStateWithLifecycle()

    ArticlesTopBar(
        navHostController,
        searchQuery,
        unreadArticlesViewModel::onSearchQuery,
    )
}
