package chata.can.chata_ai_api.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun ColorText() {
	TextField(
		value = "#008577",
		onValueChange = { },
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = Color.Red,
		),
		textStyle = TextStyle(fontSize = 20.sp),
		modifier = Modifier.fillMaxWidth(),
	)
}