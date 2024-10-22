
package microteam.ui.main.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import microteam.data.repository.ArticlesRepositoryImpl
import microteam.data.repository.UserPreferencesRepositoryImpl
import microteam.ui.main.navigation.NavRoute
import microteam.ui.screens.bookmarks.BookmarkArticlesViewModel
import microteam.ui.screens.home.AllArticlesViewModel
import microteam.ui.screens.onearticle.OneArticleViewModel
import microteam.ui.screens.onearticle.OneArticleViewModelFactory
import microteam.ui.screens.unread.UnreadArticlesViewModel

@Composable
fun TopBar(
    navHostController: NavHostController,
    allArticlesViewModel: AllArticlesViewModel,
    unreadArticlesViewModel: UnreadArticlesViewModel,
    bookmarkArticlesViewModel: BookmarkArticlesViewModel,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentNavRoutePath = navBackStackEntry?.destination?.route ?: return

    val articlesRepository = ArticlesRepositoryImpl.getInstance(LocalContext.current.applicationContext)
    val userPrefsRepository =
        UserPreferencesRepositoryImpl.getInstance(LocalContext.current)

    // All articles
    if (currentNavRoutePath.contains(NavRoute.Home.path)) {
        AllArticlesTopBar(navHostController, allArticlesViewModel)

        // Unread articles
    } else if (currentNavRoutePath.contains(NavRoute.Unread.path)) {
        UnreadArticlesTopBar(navHostController, unreadArticlesViewModel)

        // Bookmarked articles
    } else if (currentNavRoutePath.contains(NavRoute.Bookmarks.path)) {
        BookmarkedArticlesTopBar(navHostController, bookmarkArticlesViewModel)

        // One article
    } else if (currentNavRoutePath.contains(NavRoute.Article.path)) {
        val args = navBackStackEntry?.arguments
        val articleId = args?.getString(NavRoute.Article.id)

        // articleId is null when deep link is https://vtsen.hashnode.dev
        // we navigate back to the home screen. See NavGraph.kt
        if (articleId != null) {
            val viewModel: OneArticleViewModel =
                viewModel(
                    factory =
                        OneArticleViewModelFactory(
                            articlesRepository,
                            userPrefsRepository,
                            articleId,
                        ),
                )
            OneArticleTopBar(navHostController, viewModel)
        }
    } else if (currentNavRoutePath.contains(NavRoute.About.path)) {
        AboutTopBar(navHostController)
    } else {
        throw Exception("Invalid navigation path!")
    }
}
