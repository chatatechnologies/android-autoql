package chata.can.chata_ai.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.widget.CustomSwitch

@Composable
fun SwitchContent(text: String, isChecked: Boolean = true, onValueChanged: (Boolean) -> Unit) {
	Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
		Text(
			text = text,
			style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
			modifier = Modifier.fillMaxWidth()
		)
		CustomSwitch(
			isChecked = isChecked,
			onValueChanged = onValueChanged
		)
	}
}