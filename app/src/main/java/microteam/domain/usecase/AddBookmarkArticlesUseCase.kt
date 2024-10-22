
package microteam.domain.usecase

import microteam.data.repository.UserPreferencesRepository

class AddBookmarkArticlesUseCase(
    private val userPrefsRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(articleId: String) {
        userPrefsRepository.addBookmarkArticle(articleId)
    }
}
