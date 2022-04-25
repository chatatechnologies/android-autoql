package chata.can.chata_ai.compose.widget

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
	modifier: Modifier = Modifier,
	text: String,
	textColor: Color,
	placeholder: String,
	placeholderColor: Color,
	backgroundColor: Color = Color.Transparent,
	onValueChanged: (String) -> Unit
) {
	TextField(
		modifier = modifier,
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = backgroundColor,
			placeholderColor = placeholderColor,
			textColor = textColor
		),
		placeholder = {
			Text(text = placeholder, style = TextStyle(color = placeholderColor))
		},
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done,
			keyboardType = KeyboardType.Text
		),
		onValueChange = onValueChanged,
		shape = RoundedCornerShape(0.dp),
		value = text
	)
}