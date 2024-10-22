
package microteam.domain.usecase

import microteam.data.repository.ArticlesRepository

class ClearArticlesStatusUseCase(private val articlesRepository: ArticlesRepository) {
    operator fun invoke() = articlesRepository.clearStatus()
}
