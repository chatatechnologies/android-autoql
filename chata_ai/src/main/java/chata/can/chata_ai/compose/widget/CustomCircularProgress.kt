package chata.can.chata_ai.compose.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun CustomCircularProgress(color: Color = ThemeColor.currentColor.drawerAccentComposeColor()) {
	CircularProgressIndicator(
		color = color,
		modifier = Modifier
			.padding(8.dp)
			.size(56.dp),
		strokeWidth = 4.dp
	)
}