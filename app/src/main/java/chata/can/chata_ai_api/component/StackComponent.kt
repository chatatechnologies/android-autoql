package chata.can.chata_ai_api.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun StackComponent() {
	Scaffold(content = {
		Box(
			modifier = Modifier
				.fillMaxSize(),
			contentAlignment = Alignment.Center,
		) {
			Column(
				modifier = Modifier
					.background(Color.LightGray)
					.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = "Content under Circular Progress Indicator",
					style = TextStyle(
						fontFamily = FontFamily.Monospace,
						fontWeight = FontWeight.W900,
						fontSize = 14.sp,
						textAlign = TextAlign.Center,
						background = Color.Gray
					),
					modifier = Modifier.padding(16.dp)
				)
			}

			Surface(color = Color.White) {
				CircularProgressIndicator(
					color = Color.Blue,
					modifier = Modifier
						.size(112.dp)
						.padding(16.dp),
					strokeWidth = 4.dp
				)
			}
		}
	})
}

@Preview(showBackground = true)
@Composable
fun StackComponentPreview() {
	ApiChataTheme {
		StackComponent()
	}
}