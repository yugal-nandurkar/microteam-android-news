
package microteam.domain.usecase

import microteam.data.repository.UserPreferencesRepository

class RemoveReadArticlesUseCase(
    private val userPrefsRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(articleId: String) {
        userPrefsRepository.removeReadArticle(articleId)
    }
}
