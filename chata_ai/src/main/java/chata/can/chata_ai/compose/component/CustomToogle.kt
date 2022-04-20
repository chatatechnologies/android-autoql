package chata.can.chata_ai.compose.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconToggleButton(
	imageVector: ImageVector = Icons.Filled.Favorite,
	checkedColor: Color = Color(0xFFEC407A),
	color: Color = Color(0xFFB0BEC5),
	contentDescription: String = "Icon Toggle Example"
) {
	var checked by remember { mutableStateOf(false) }

	IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
		val tint by animateColorAsState(
			if (checked) checkedColor else color
		)
		Icon(imageVector, contentDescription = contentDescription, tint = tint)
	}
}