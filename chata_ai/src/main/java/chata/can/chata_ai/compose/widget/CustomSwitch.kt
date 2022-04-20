package chata.can.chata_ai.compose.widget

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun CustomSwitch(
	modifier: Modifier = Modifier,
	isChecked: Boolean = true,
	checkedColor: Color = colorResource(id = R.color.blue_chata_circle),
	onValueChanged: (Boolean) -> Boolean
) {
	var checked by remember { mutableStateOf(isChecked) }

	Switch(
		checked = checked,
		colors = SwitchDefaults.colors(checkedThumbColor = checkedColor),
		modifier = modifier,
		onCheckedChange = {
			onValueChanged(it)
			checked = it
		})
}

@Preview
@Composable
fun CustomSwitchPreview() {
	ApiChataTheme {
		CustomSwitch { value -> value }
	}
}