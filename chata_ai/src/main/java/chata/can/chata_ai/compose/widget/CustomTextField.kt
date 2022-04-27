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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
	modifier: Modifier = Modifier,
	text: String = "Text test",
	textColor: Color = Color.White,
	placeholder: String = "",
	placeholderColor: Color = Color.White,
	backgroundColor: Color = Color.White,
	onValueChanged: (String) -> Unit,
	methodOnDone: () -> Unit = {}
) {
	val focusManager = LocalFocusManager.current
	TextField(
		modifier = modifier,
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = backgroundColor,
			cursorColor = textColor,
			unfocusedIndicatorColor = backgroundColor,
			focusedIndicatorColor = backgroundColor
		),
		keyboardActions = KeyboardActions(
			onDone = {
				focusManager.clearFocus()
				methodOnDone()
			}
		),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done,
			keyboardType = KeyboardType.Text
		),
		onValueChange = onValueChanged,
		shape = RoundedCornerShape(0.dp),
		textStyle = TextStyle(color = textColor, fontSize = 16.sp),
		value = text,
		placeholder = { Text(text = placeholder, style = TextStyle(color = placeholderColor)) }
	)
}