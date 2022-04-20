package chata.can.chata_ai.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.compose.widget.CustomSwitch

@Composable
fun SwitchContent(text: String) {
	Column(modifier = Modifier.fillMaxWidth()) {
		Text(
			text = text,
			style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
			modifier = Modifier.fillMaxWidth()
		)
		CustomSwitch(modifier = Modifier.fillMaxWidth()) { value -> value }
	}
}

@Preview(showBackground = true)
@Composable
fun SwitchContentPreview() {
	ApiChataTheme {
		SwitchContent("Enable Autocomplete")
	}
}