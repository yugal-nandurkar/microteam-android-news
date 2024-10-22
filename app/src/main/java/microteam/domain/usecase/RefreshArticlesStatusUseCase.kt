
package microteam.domain.usecase

import microteam.data.repository.ArticlesRepository

class RefreshArticlesStatusUseCase(private val repository: ArticlesRepository) {
    suspend operator fun invoke() = repository.refresh()
}
