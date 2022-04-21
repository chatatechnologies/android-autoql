package chata.can.chata_ai.compose.widget

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import chata.can.chata_ai.R

@Composable
fun CustomSwitch(
	modifier: Modifier = Modifier,
	isChecked: Boolean = true,
	checkedColor: Color = colorResource(id = R.color.blue_chata_circle),
	onValueChanged: (Boolean) -> Unit
) {
	val uncheckedColor = Color.LightGray
	var checked by remember { mutableStateOf(isChecked) }

	Switch(
		checked = checked,
		colors = SwitchDefaults.colors(
			checkedThumbColor = checkedColor,
			uncheckedThumbColor = uncheckedColor,
			uncheckedTrackColor = uncheckedColor
		),
		modifier = modifier,
		onCheckedChange = {
			onValueChanged(it)
			checked = it
		})
}