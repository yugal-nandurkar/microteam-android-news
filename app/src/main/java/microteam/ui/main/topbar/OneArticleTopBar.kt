
package microteam.ui.main.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import microteam.R
import microteam.data.repository.ArticlesRepositoryImpl
import microteam.data.repository.UserPreferencesRepositoryImpl
import microteam.domain.usecase.AddBookmarkArticlesUseCase
import microteam.domain.usecase.AddReadArticlesUseCase
import microteam.domain.usecase.ClearArticlesStatusUseCase
import microteam.domain.usecase.GetAllArticlesUseCase
import microteam.domain.usecase.GetArticleStatusUseCase
import microteam.domain.usecase.GetOneArticleUseCase
import microteam.domain.usecase.RefreshArticlesStatusUseCase
import microteam.domain.usecase.RemoveBookmarkArticlesUseCase
import microteam.domain.usecase.RemoveReadArticlesUseCase
import microteam.ui.screens.common.ArticleIconButton
import microteam.ui.screens.onearticle.OneArticleViewModel

@Composable
fun OneArticleTopBar(
    navHostController: NavHostController,
    viewModel: OneArticleViewModel,
) {
    val article by viewModel.article.collectAsStateWithLifecycle(null)

    TopAppBar {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }

            Row {
                article?.run {
                    ArticleIconButton(
                        articleUi = article!!,
                        onIconClick = viewModel::onReadClick,
                        iconPainter =
                            if (article!!.read) {
                                painterResource(R.drawable.ic_check_circle)
                            } else {
                                painterResource(R.drawable.ic_radio_button_unchecked)
                            },
                    )

                    ArticleIconButton(
                        articleUi = article!!,
                        onIconClick = viewModel::onBookmarkClick,
                        iconPainter =
                            if (article!!.bookmarked) {
                                painterResource(R.drawable.ic_bookmarked)
                            } else {
                                painterResource(R.drawable.ic_bookmark_border)
                            },
                    )
                }

                TopBarDropDownMenu(navHostController = navHostController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val articlesRepository = ArticlesRepositoryImpl.getInstance(LocalContext.current)
    val userPrefsRepository = UserPreferencesRepositoryImpl.getInstance(LocalContext.current)
    val getAllArticlesUseCase = GetAllArticlesUseCase(articlesRepository, userPrefsRepository)

    val viewModel =
        OneArticleViewModel(
            GetArticleStatusUseCase(articlesRepository),
            RefreshArticlesStatusUseCase(articlesRepository),
            ClearArticlesStatusUseCase(articlesRepository),
            AddBookmarkArticlesUseCase(userPrefsRepository),
            RemoveBookmarkArticlesUseCase(userPrefsRepository),
            AddReadArticlesUseCase(userPrefsRepository),
            RemoveReadArticlesUseCase(userPrefsRepository),
            GetOneArticleUseCase(getAllArticlesUseCase),
            articleId = "",
        )

    val navHostController = rememberNavController()

    OneArticleTopBar(
        navHostController,
        viewModel,
    )
}
