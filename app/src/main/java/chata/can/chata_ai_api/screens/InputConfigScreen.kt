package chata.can.chata_ai_api.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.component.CustomButton
import chata.can.chata_ai.compose.component.CustomTextField
import chata.can.chata_ai.compose.component.RequiredField
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun InputConfigScreen() {
	Surface(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 12.dp, vertical = 4.dp),
		color = Color.White
	) {
		var projectId by remember { mutableStateOf("") }
		var userEmail by remember { mutableStateOf("") }
		var apiKey by remember { mutableStateOf("") }
		var domainUrl by remember { mutableStateOf("") }
		var username by remember { mutableStateOf("") }
		var password by remember { mutableStateOf("") }

		Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
			Text(
				text = "Authentication",
				style = TextStyle(
					fontWeight = FontWeight.Bold,
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			CustomTextField(placeholder = "Project ID", value = projectId) { projectId = it }
			RequiredField()
			Spacer(modifier = Modifier.height(4.dp))
			CustomTextField(placeholder = "User Email", value = userEmail) { userEmail = it }
			RequiredField()
			Spacer(modifier = Modifier.height(4.dp))
			CustomTextField(placeholder = "API key", value = apiKey) { apiKey = it }
			RequiredField()
			Spacer(modifier = Modifier.height(4.dp))
			CustomTextField(placeholder = "Domain URL", value = domainUrl) { domainUrl = it }
			RequiredField()
			Spacer(modifier = Modifier.height(4.dp))
			CustomTextField(placeholder = "Username", value = username) { username = it }
			RequiredField()
			Spacer(modifier = Modifier.height(4.dp))
			CustomTextField(
				placeholder = "Password",
				value = password,
				keyboardType = KeyboardType.Password
			) {
				password = it
			}
			RequiredField()
			Spacer(modifier = Modifier.height(4.dp))
			CustomButton("Authenticate")
		}
	}
}

@Preview(showBackground = true, name = "InputExample", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun InputConfigScreenPreview() {
	ApiChataTheme {
		InputConfigScreen()
	}
}