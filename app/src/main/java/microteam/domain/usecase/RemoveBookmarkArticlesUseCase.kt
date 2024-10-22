
package microteam.domain.usecase

import microteam.data.repository.UserPreferencesRepository

class RemoveBookmarkArticlesUseCase(
    private val userPrefsRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(articleId: String) {
        userPrefsRepository.removeBookmarkArticle(articleId)
    }
}
