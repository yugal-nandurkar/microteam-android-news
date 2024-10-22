
package microteam.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette =
    darkColors(
        primary = md_theme_dark_primary,
        primaryVariant = md_theme_dark_primaryVariant,
    )

private val LightColorPalette =
    lightColors(
        primary = md_theme_light_primary,
        primaryVariant = md_theme_light_primaryVariant,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
     */
    )

@Composable
fun AndroidNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useSystemUIController: Boolean,
    content: @Composable () -> Unit,
) {
    val colors =
        if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }

    if (useSystemUIController) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = colors.primaryVariant,
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
