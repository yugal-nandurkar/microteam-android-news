
package microteam.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import microteam.domain.model.ArticleUi
import microteam.domain.model.ArticlesUiState
import microteam.domain.usecase.AddBookmarkArticlesUseCase
import microteam.domain.usecase.AddReadArticlesUseCase
import microteam.domain.usecase.ClearArticlesStatusUseCase
import microteam.domain.usecase.GetArticleStatusUseCase
import microteam.domain.usecase.RefreshArticlesStatusUseCase
import microteam.domain.usecase.RemoveBookmarkArticlesUseCase
import microteam.domain.usecase.RemoveReadArticlesUseCase

abstract class ArticlesViewModel(
    protected val getArticleStatusUseCase: GetArticleStatusUseCase,
    protected val refreshArticlesStatusUseCase: RefreshArticlesStatusUseCase,
    protected val clearArticlesStatusUseCase: ClearArticlesStatusUseCase,
    protected val addBookmarkArticlesUseCase: AddBookmarkArticlesUseCase,
    protected val removeBookmarkArticlesUseCase: RemoveBookmarkArticlesUseCase,
    protected val addReadArticlesUseCase: AddReadArticlesUseCase,
    protected val removeReadArticlesUseCase: RemoveReadArticlesUseCase,
) : ViewModel() {
    val uiState =
        getArticleStatusUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ArticlesUiState.Success,
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    protected fun setIsSearching(value: Boolean) {
        _isSearching.value = value
    }

    fun onSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun refresh() =
        viewModelScope.launch {
            refreshArticlesStatusUseCase()
        }

    fun clearStatus() = clearArticlesStatusUseCase()

    fun onReadClick(articleUi: ArticleUi) =
        viewModelScope.launch {
            if (articleUi.read) {
                removeReadArticlesUseCase(articleUi.id)
            } else {
                addReadArticlesUseCase(articleUi.id)
            }
        }

    fun onBookmarkClick(articleUi: ArticleUi) =
        viewModelScope.launch {
            if (articleUi.bookmarked) {
                removeBookmarkArticlesUseCase(articleUi.id)
            } else {
                addBookmarkArticlesUseCase(articleUi.id)
            }
        }
}
