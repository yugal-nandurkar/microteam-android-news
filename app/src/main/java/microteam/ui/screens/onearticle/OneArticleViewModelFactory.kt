
package microteam.ui.screens.onearticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import microteam.data.repository.ArticlesRepository
import microteam.data.repository.UserPreferencesRepository
import microteam.domain.usecase.AddBookmarkArticlesUseCase
import microteam.domain.usecase.AddReadArticlesUseCase
import microteam.domain.usecase.ClearArticlesStatusUseCase
import microteam.domain.usecase.GetAllArticlesUseCase
import microteam.domain.usecase.GetArticleStatusUseCase
import microteam.domain.usecase.GetOneArticleUseCase
import microteam.domain.usecase.RefreshArticlesStatusUseCase
import microteam.domain.usecase.RemoveBookmarkArticlesUseCase
import microteam.domain.usecase.RemoveReadArticlesUseCase

@Suppress("UNCHECKED_CAST")
class OneArticleViewModelFactory(
    private val articlesRepository: ArticlesRepository,
    private val userPrefsRepository: UserPreferencesRepository,
    private val articleId: String,
) : ViewModelProvider.Factory {
    private val getAllArticlesUseCase = GetAllArticlesUseCase(articlesRepository, userPrefsRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneArticleViewModel::class.java)) {
            return OneArticleViewModel(
                GetArticleStatusUseCase(articlesRepository),
                RefreshArticlesStatusUseCase(articlesRepository),
                ClearArticlesStatusUseCase(articlesRepository),
                AddBookmarkArticlesUseCase(userPrefsRepository),
                RemoveBookmarkArticlesUseCase(userPrefsRepository),
                AddReadArticlesUseCase(userPrefsRepository),
                RemoveReadArticlesUseCase(userPrefsRepository),
                GetOneArticleUseCase(getAllArticlesUseCase),
                articleId,
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
