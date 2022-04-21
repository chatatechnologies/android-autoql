package chata.can.chata_ai_api.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.extension.getContrast

@Composable
fun ColorText(textColor: String = "#00857") {
	var text by remember { mutableStateOf(textColor) }
	val contrastColor by remember(text) { mutableStateOf(text.getContrast()) }

	TextField(
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = Color(contrastColor.first),
		),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done,
			keyboardType = KeyboardType.Number
		),
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 8.dp),
		onValueChange = {
			text = it
		},
		shape = RoundedCornerShape(0.dp),
		textStyle = TextStyle(
			color = Color(contrastColor.second),
			fontSize = 20.sp,
			textAlign = TextAlign.Center
		),
		value = text
	)
}

@Preview
@Composable
fun ColorTextPreview() {
	ApiChataTheme {
		ColorText()
	}
}