
package microteam.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import microteam.data.repository.ArticlesRepository
import microteam.data.repository.UserPreferencesRepository
import microteam.domain.usecase.AddBookmarkArticlesUseCase
import microteam.domain.usecase.AddReadArticlesUseCase
import microteam.domain.usecase.ClearArticlesStatusUseCase
import microteam.domain.usecase.GetAllArticlesUseCase
import microteam.domain.usecase.GetArticleStatusUseCase
import microteam.domain.usecase.GetBookmarkArticlesUseCase
import microteam.domain.usecase.GetUnreadArticlesUseCase
import microteam.domain.usecase.RefreshArticlesStatusUseCase
import microteam.domain.usecase.RemoveBookmarkArticlesUseCase
import microteam.domain.usecase.RemoveReadArticlesUseCase
import microteam.ui.screens.bookmarks.BookmarkArticlesViewModel
import microteam.ui.screens.home.AllArticlesViewModel
import microteam.ui.screens.unread.UnreadArticlesViewModel

@Suppress("UNCHECKED_CAST")
class ArticlesViewModelFactory(
    private val articlesRepository: ArticlesRepository,
    private val userPrefsRepository: UserPreferencesRepository,
) : ViewModelProvider.Factory {
    private val getAllArticlesUseCase = GetAllArticlesUseCase(articlesRepository, userPrefsRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllArticlesViewModel::class.java)) {
            return AllArticlesViewModel(
                getAllArticlesUseCase,
                GetArticleStatusUseCase(articlesRepository),
                RefreshArticlesStatusUseCase(articlesRepository),
                ClearArticlesStatusUseCase(articlesRepository),
                AddBookmarkArticlesUseCase(userPrefsRepository),
                RemoveBookmarkArticlesUseCase(userPrefsRepository),
                AddReadArticlesUseCase(userPrefsRepository),
                RemoveReadArticlesUseCase(userPrefsRepository),
            ) as T
        } else if (modelClass.isAssignableFrom(UnreadArticlesViewModel::class.java)) {
            return UnreadArticlesViewModel(
                GetUnreadArticlesUseCase(getAllArticlesUseCase),
                GetArticleStatusUseCase(articlesRepository),
                RefreshArticlesStatusUseCase(articlesRepository),
                ClearArticlesStatusUseCase(articlesRepository),
                AddBookmarkArticlesUseCase(userPrefsRepository),
                RemoveBookmarkArticlesUseCase(userPrefsRepository),
                AddReadArticlesUseCase(userPrefsRepository),
                RemoveReadArticlesUseCase(userPrefsRepository),
            ) as T
        } else if (modelClass.isAssignableFrom(BookmarkArticlesViewModel::class.java)) {
            return BookmarkArticlesViewModel(
                GetBookmarkArticlesUseCase(getAllArticlesUseCase),
                GetArticleStatusUseCase(articlesRepository),
                RefreshArticlesStatusUseCase(articlesRepository),
                ClearArticlesStatusUseCase(articlesRepository),
                AddBookmarkArticlesUseCase(userPrefsRepository),
                RemoveBookmarkArticlesUseCase(userPrefsRepository),
                AddReadArticlesUseCase(userPrefsRepository),
                RemoveReadArticlesUseCase(userPrefsRepository),
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
