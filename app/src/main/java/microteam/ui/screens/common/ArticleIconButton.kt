
package microteam.ui.screens.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import microteam.domain.model.ArticleUi

@Composable
fun ArticleIconButton(
    articleUi: ArticleUi,
    onIconClick: (ArticleUi) -> Unit,
    iconPainter: Painter,
    contentDescription: String? = null,
) {
    IconButton(onClick = { onIconClick(articleUi) }) {
        Icon(
            painter = iconPainter,
            contentDescription = contentDescription,
        )
    }
}
