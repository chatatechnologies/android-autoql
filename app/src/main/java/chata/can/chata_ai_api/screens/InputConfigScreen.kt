package chata.can.chata_ai_api.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.component.CustomTextField

@Composable
fun InputConfigScreen() {
	Surface(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 12.dp, vertical = 4.dp)
	) {
		Column {
			Text(
				text = "Authentication",
				style = TextStyle(
					fontWeight = FontWeight.Bold,
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			CustomTextField(placeholder = "Project ID", value = "") {

			}
			CustomTextField(placeholder = "User Email") {

			}
			CustomTextField(placeholder = "API key") {

			}
			CustomTextField(placeholder = "Domain URL") {

			}
			CustomTextField(placeholder = "Username") {

			}
			CustomTextField(placeholder = "Password") {

			}
		}
	}
}

@Preview(showBackground = true, name = "InputExample")
@Composable
fun InputConfigScreenPreview() {
	InputConfigScreen()
}