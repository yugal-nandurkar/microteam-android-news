
package microteam

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import microteam.data.repository.FakeArticlesRepositoryImpl
import microteam.data.repository.FakeUserPreferencesRepositoryImpl
import microteam.domain.usecase.AddBookmarkArticlesUseCase
import microteam.domain.usecase.AddReadArticlesUseCase
import microteam.domain.usecase.ClearArticlesStatusUseCase
import microteam.domain.usecase.GetAllArticlesUseCase
import microteam.domain.usecase.GetArticleStatusUseCase
import microteam.domain.usecase.RefreshArticlesStatusUseCase
import microteam.domain.usecase.RemoveBookmarkArticlesUseCase
import microteam.domain.usecase.RemoveReadArticlesUseCase
import microteam.ui.screens.home.AllArticlesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    private lateinit var viewModel: AllArticlesViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        val articlesRepository = FakeArticlesRepositoryImpl()
        val userPrefsRepository = FakeUserPreferencesRepositoryImpl()
        val getAllArticlesUseCase = GetAllArticlesUseCase(articlesRepository, userPrefsRepository)

        viewModel =
            AllArticlesViewModel(
                getAllArticlesUseCase,
                GetArticleStatusUseCase(articlesRepository),
                RefreshArticlesStatusUseCase(articlesRepository),
                ClearArticlesStatusUseCase(articlesRepository),
                AddBookmarkArticlesUseCase(userPrefsRepository),
                RemoveBookmarkArticlesUseCase(userPrefsRepository),
                AddReadArticlesUseCase(userPrefsRepository),
                RemoveReadArticlesUseCase(userPrefsRepository),
            )
    }

    @Test
    fun allArticles_areNotNull() =
        runTest {
            Assert.assertNotEquals(null, viewModel.articles.first())

            delay(1000)
            Assert.assertNotEquals(null, viewModel.articles)
        }
}
