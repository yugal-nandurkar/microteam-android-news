
package microteam.ui.main.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import microteam.ui.screens.home.AllArticlesViewModel

@Composable
fun AllArticlesTopBar(
    navHostController: NavHostController,
    allArticlesViewModel: AllArticlesViewModel,
) {
    val searchQuery by allArticlesViewModel.searchQuery.collectAsStateWithLifecycle()

    ArticlesTopBar(
        navHostController,
        searchQuery,
        allArticlesViewModel::onSearchQuery,
    )
}
