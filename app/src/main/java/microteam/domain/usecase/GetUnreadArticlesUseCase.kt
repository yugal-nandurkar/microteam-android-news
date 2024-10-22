
package microteam.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import microteam.domain.model.ArticleUi

class GetUnreadArticlesUseCase(
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
) {
    operator fun invoke(title: String? = null): Flow<List<ArticleUi>> {
        val allArticlesFlow = getAllArticlesUseCase()

        val unreadArticlesFlow =
            allArticlesFlow.map { articleUiList ->
                articleUiList.filter { articleUi ->

                    if (title.isNullOrEmpty()) {
                        !articleUi.read
                    } else {
                        !articleUi.read && articleUi.title.contains(title)
                    }
                }
            }

        return unreadArticlesFlow
    }
}
