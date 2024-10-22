
package microteam.domain.mapper

import microteam.R
import microteam.data.repository.ArticlesRepoStatus
import microteam.domain.model.ArticlesUiState

fun ArticlesRepoStatus.toArticlesUiState(): ArticlesUiState {
    return when (this) {
        is ArticlesRepoStatus.Invalid -> ArticlesUiState.Invalid
        is ArticlesRepoStatus.IsLoading -> ArticlesUiState.Loading
        is ArticlesRepoStatus.Success -> ArticlesUiState.Success
        is ArticlesRepoStatus.Fail -> ArticlesUiState.Error(R.string.no_internet)
    }
}
