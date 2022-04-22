package chata.can.chata_ai_api.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun StackComponent() {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(200.dp),
		contentAlignment = Alignment.Center,
	) {
		Text(
			text = "Title",
			style = TextStyle(
				fontFamily = FontFamily.Monospace,
				fontWeight = FontWeight.W900,
				fontSize = 14.sp
			),
			modifier = Modifier
				.padding(16.dp)
		)

		CircularProgressIndicator()
	}
}

@Preview(showBackground = true)
@Composable
fun StackComponentPreview() {
	ApiChataTheme {
		StackComponent()
	}
}