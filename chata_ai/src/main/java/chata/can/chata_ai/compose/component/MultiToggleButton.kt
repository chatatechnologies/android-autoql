package chata.can.chata_ai.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun MultiToggleButton(
	currentSelection: String,
	toggleStates: List<String>,
	onToggleChange: (String) -> String
) {
	val selectedTint = MaterialTheme.colors.primary
	val unselectedTint = Color.Unspecified
	var checked by remember { mutableStateOf(currentSelection) }

	Row(
		modifier = Modifier
			.height(IntrinsicSize.Min)
			.border(BorderStroke(1.dp, Color.LightGray))
	) {
		toggleStates.forEachIndexed { index, toggleState ->
			val isSelected = checked.lowercase() == toggleState.lowercase()
			val backgroundTint = if (isSelected) selectedTint else unselectedTint
			val textColor = if (isSelected) Color.White else Color.Unspecified

			if (index != 0) {
				Divider(
					color = Color.LightGray,
					modifier = Modifier
						.fillMaxHeight()
						.width(1.dp)
				)
			}

			Row(
				modifier = Modifier
					.background(backgroundTint)
					.padding(vertical = 6.dp, horizontal = 8.dp)
					.toggleable(value = isSelected, enabled = true) { selected ->
						if (selected) {
							checked = toggleState
							onToggleChange(toggleState)
						}
					}
			) {
				Text(text = toggleState, color = textColor, modifier = Modifier.padding(4.dp))
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MultiToggleButtonPreview() {
	ApiChataTheme {
		MultiToggleButton("Dark", listOf("Light", "Dark")) { value ->
			value
		}
	}
}