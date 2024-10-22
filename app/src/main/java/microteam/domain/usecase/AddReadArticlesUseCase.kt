
package microteam.domain.usecase

import microteam.data.repository.UserPreferencesRepository

class AddReadArticlesUseCase(
    private val userPrefsRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(articleId: String) {
        userPrefsRepository.addReadArticle(articleId)
    }
}
