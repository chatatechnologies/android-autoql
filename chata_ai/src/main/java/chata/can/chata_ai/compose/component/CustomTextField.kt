package chata.can.chata_ai.compose.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.R

/** https://material.io/components/text-fields#specs **/
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
	val blueColor = colorResource(id = R.color.blue_chata_circle)
	val blackColor = Color.Black
	val grayColor = Color.DarkGray
	val focusManager = LocalFocusManager.current
	OutlinedTextField(
		value = value,
		onValueChange = onValueChanged,
		textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
		label = {
			Text(text = placeholder, style = TextStyle(fontSize = 16.sp))
		},
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 8.dp)
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
		),
		visualTransformation = if (keyboardType == KeyboardType.Password)
			PasswordVisualTransformation()
		else
			VisualTransformation.None,
		//TODO complete colors for input config screen
		colors = TextFieldDefaults.outlinedTextFieldColors(
			cursorColor = blueColor,
			textColor = blackColor,
			focusedBorderColor = blueColor,
			focusedLabelColor = blueColor,
			unfocusedBorderColor = grayColor,
			unfocusedLabelColor = grayColor,
			placeholderColor = blackColor
		)
	)
}