
package microteam.ui.main.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import microteam.ui.main.navigation.NavRoute

@Composable
fun TopBarDropDownMenu(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    var expandedDropDownMenu by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
    ) {
        IconButton(onClick = {
            expandedDropDownMenu = true
        }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
            )
        }

        DropdownMenu(
            expanded = expandedDropDownMenu,
            onDismissRequest = {
                expandedDropDownMenu = false
            },
        ) {
            DropdownMenuItem(
                onClick = {
                    expandedDropDownMenu = false
                    navHostController.navigate(NavRoute.About.path)
                },
            ) {
                Text(text = "About")
            }
        }
    }
}
