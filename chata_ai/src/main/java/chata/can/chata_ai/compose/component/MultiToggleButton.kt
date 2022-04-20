package chata.can.chata_ai.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R

@Composable
fun MultiToggleButton(
	currentSelection: String,
	toggleStates: List<String>,
	onToggleChange: (String) -> String
) {
	val selectedTint = colorResource(id = R.color.blue_chata_circle)
	val unselectedTint = Color.White
	var checked by remember { mutableStateOf(currentSelection) }
	val interactionSource = remember { MutableInteractionSource() }

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.border(
				BorderStroke(1.dp, Color.LightGray),
				RoundedCornerShape(6.dp)
			)
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

			val shape = when (index) {
				0 -> RoundedCornerShape(bottomStart = 6.dp, topStart = 6.dp)
				toggleStates.size - 1 -> RoundedCornerShape(bottomEnd = 6.dp, topEnd = 6.dp)
				else -> RoundedCornerShape(0.dp)
			}

			Row(
				modifier = Modifier
					.background(backgroundTint, shape)
					.padding(vertical = 6.dp, horizontal = 8.dp)
					.weight(1f)
					.toggleable(
						value = isSelected,
						enabled = true,
						interactionSource = interactionSource,
						indication = null
					) { selected ->
						if (selected) {
							checked = toggleState
							onToggleChange(toggleState)
						}
					}
			) {
				Text(
					text = toggleState,
					color = textColor,
					style = TextStyle(textAlign = TextAlign.Center),
					modifier = Modifier
						.fillMaxWidth()
						.padding(4.dp)
				)
			}
		}
	}
}