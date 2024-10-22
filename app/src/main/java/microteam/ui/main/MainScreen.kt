
package microteam.ui.main

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.github.vinchamp77.buildutils.BuildExt
import microteam.data.repository.ArticlesRepositoryImpl
import microteam.data.repository.UserPreferencesRepositoryImpl
import microteam.domain.model.ArticlesUiState
import microteam.ui.main.navigation.BottomBarNav
import microteam.ui.main.navigation.NavGraph
import microteam.ui.main.topbar.TopBar
import microteam.ui.main.viewmodel.ArticlesViewModelFactory
import microteam.ui.screens.bookmarks.BookmarkArticlesViewModel
import microteam.ui.screens.common.PermissionDialog
import microteam.ui.screens.home.AllArticlesViewModel
import microteam.ui.screens.unread.UnreadArticlesViewModel
import microteam.ui.theme.AndroidNewsTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    val navHostController = rememberNavController()

    val articlesRepository = ArticlesRepositoryImpl.getInstance(LocalContext.current)
    val userPrefsRepository = UserPreferencesRepositoryImpl.getInstance(LocalContext.current)
    val allArticlesViewModel: AllArticlesViewModel =
        viewModel(
            factory = ArticlesViewModelFactory(articlesRepository, userPrefsRepository),
        )
    val unreadArticlesViewModel: UnreadArticlesViewModel =
        viewModel(
            factory = ArticlesViewModelFactory(articlesRepository, userPrefsRepository),
        )
    val bookmarkArticlesViewModel: BookmarkArticlesViewModel =
        viewModel(
            factory = ArticlesViewModelFactory(articlesRepository, userPrefsRepository),
        )

    val uiState: ArticlesUiState by allArticlesViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                navHostController,
                allArticlesViewModel,
                unreadArticlesViewModel,
                bookmarkArticlesViewModel,
            )
        },
        bottomBar = { BottomBarNav(navHostController) },
    ) { paddingValues ->

        NavGraph(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navHostController = navHostController,
            allArticlesViewModel,
            unreadArticlesViewModel,
            bookmarkArticlesViewModel,
        )
    }

    if (uiState is ArticlesUiState.Error) {
        SnackBar(
            scaffoldState,
            (uiState as ArticlesUiState.Error).msgResId,
            onDone = {
                allArticlesViewModel.clearStatus()
            },
        )
    }

    if (BuildExt.VERSION.isNotificationRuntimePermissionNeeded()) {
        PermissionDialog(Manifest.permission.POST_NOTIFICATIONS)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AndroidNewsTheme(useSystemUIController = false) {
        MainScreen()
    }
}
