package chata.can.chata_ai.compose.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomTextField(
	placeholder: String,
	enabled: Boolean = true,
	value: String = "",
	onGloballyPositioned: ((LayoutCoordinates) -> Unit)? = null,
	keyboardType: KeyboardType = KeyboardType.Text,
	imeAction: ImeAction = ImeAction.Done,
	onValueChanged: (String) -> Unit
) {
	val focusManager = LocalFocusManager.current
	OutlinedTextField(
		value = value,
		onValueChange = onValueChanged,
		textStyle = TextStyle(color = Color.Black),
		label = {
			Text(text = placeholder, style = MaterialTheme.typography.caption)
		},
		modifier = Modifier
			.fillMaxWidth()
			.onGloballyPositioned { coordinates ->
				onGloballyPositioned?.let {
					it(coordinates)
				}
			},
		enabled = enabled,
		keyboardActions = KeyboardActions(
			onDone = { focusManager.clearFocus() }
		),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = imeAction,
			keyboardType = keyboardType
		)
	)
}