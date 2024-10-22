
package microteam.ui.screens.bookmarks

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import microteam.domain.usecase.AddBookmarkArticlesUseCase
import microteam.domain.usecase.AddReadArticlesUseCase
import microteam.domain.usecase.ClearArticlesStatusUseCase
import microteam.domain.usecase.GetArticleStatusUseCase
import microteam.domain.usecase.GetBookmarkArticlesUseCase
import microteam.domain.usecase.RefreshArticlesStatusUseCase
import microteam.domain.usecase.RemoveBookmarkArticlesUseCase
import microteam.domain.usecase.RemoveReadArticlesUseCase
import microteam.ui.main.viewmodel.ArticlesViewModel

class BookmarkArticlesViewModel(
    getBookmarkArticlesUseCase: GetBookmarkArticlesUseCase,
    getArticleStatusUseCase: GetArticleStatusUseCase,
    refreshArticlesStatusUseCase: RefreshArticlesStatusUseCase,
    clearArticlesStatusUseCase: ClearArticlesStatusUseCase,
    addBookmarkArticlesUseCase: AddBookmarkArticlesUseCase,
    removeBookmarkArticlesUseCase: RemoveBookmarkArticlesUseCase,
    addReadArticlesUseCase: AddReadArticlesUseCase,
    removeReadArticlesUseCase: RemoveReadArticlesUseCase,
) : ArticlesViewModel(
        getArticleStatusUseCase,
        refreshArticlesStatusUseCase,
        clearArticlesStatusUseCase,
        addBookmarkArticlesUseCase,
        removeBookmarkArticlesUseCase,
        addReadArticlesUseCase,
        removeReadArticlesUseCase,
    ) {
    val articles =
        searchQuery
            // .debounce(1000) // required if it is network call
            .onEach { setIsSearching(true) }
            .combine(getBookmarkArticlesUseCase()) { searchQuery, articles ->
                if (searchQuery.isBlank()) {
                    articles
                } else {
                    // delay(2000) // simulate network delay
                    articles.filter { articleUi ->
                        articleUi.title.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
            .onEach { setIsSearching(false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null,
            )
}
