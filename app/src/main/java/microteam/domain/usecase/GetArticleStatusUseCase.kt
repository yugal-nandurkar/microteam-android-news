
package microteam.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import microteam.data.repository.ArticlesRepository
import microteam.domain.mapper.toArticlesUiState
import microteam.domain.model.ArticlesUiState

class GetArticleStatusUseCase(private val articlesRepository: ArticlesRepository) {
    operator fun invoke(): Flow<ArticlesUiState> {
        return articlesRepository.getStatus().map { articlesRepoStatus ->
            articlesRepoStatus.toArticlesUiState()
        }
    }
}
