package chata.can.chata_ai.compose.widget

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
	modifier: Modifier = Modifier,
	text: String = "Text test",
	textColor: Color = Color.White,
	placeholder: String = "",
	placeholderColor: Color = Color.White,
	backgroundColor: Color = Color.White,
	onValueChanged: (String) -> Unit
) {
	val focusManager = LocalFocusManager.current
	TextField(
		modifier = modifier,
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = backgroundColor,
			cursorColor = backgroundColor,
			unfocusedIndicatorColor = backgroundColor,
			focusedIndicatorColor = backgroundColor
		),
		keyboardActions = KeyboardActions(
			onDone = { focusManager.clearFocus() }
		),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done,
			keyboardType = KeyboardType.Text
		),
		onValueChange = onValueChanged,
		shape = RoundedCornerShape(0.dp),
		textStyle = TextStyle(color = textColor),
		value = text,
		placeholder = { Text(text = placeholder, style = TextStyle(color = placeholderColor)) }
	)
}

@Preview
@Composable
fun CustomTextFieldPreview() {
	CustomTextField(
		textColor = Color.White,
		placeholder = "",
		placeholderColor = Color.Red,
		backgroundColor = Color.DarkGray
	) {

	}
}