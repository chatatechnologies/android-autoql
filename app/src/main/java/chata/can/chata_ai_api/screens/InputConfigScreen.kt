package chata.can.chata_ai_api.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.component.CustomButton
import chata.can.chata_ai.compose.component.CustomTextField
import chata.can.chata_ai.compose.component.MultiToggleButton
import chata.can.chata_ai.compose.component.RequiredField
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai_api.component.TitleSection

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
			//region Authentication
			TitleSection("Authentication")
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
			//endregion
			//region Customize Widgets
			TitleSection("Customize Widgets")

			TitleSection("AutoQL Api Configuration Options")

			Text(
				text = "Enable Autocomplete",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Query Validation",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Query Suggestion",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Drilldowns",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Column Visibility Editor",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Notifications",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			//endregion
			//region UI Configuration Options
			TitleSection("UI Configuration Options")

			Text(
				text = "Show Data Messenger Button",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Darken Background Behind Data Messenger",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Theme",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			MultiToggleButton("One", listOf("One", "Two")) { value ->
				Log.d("Toggle Button", "MultiToggleButtonPreview: $value")
				value
			}

			Text(
				text = "Data Messenger Placement",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Default Tab",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			CustomTextField(placeholder = "Currency Code", value = "") {  }
			CustomTextField(placeholder = "Language Code", value = "") {  }
			CustomTextField(placeholder = "Format for Day, Year", value = "") {  }
			CustomTextField(placeholder = "Format for Day, Month, Year", value = "") {  }
			CustomTextField(placeholder = "Number of Decimals for Currency Values", value = "") {  }
			CustomTextField(placeholder = "Number of Decimals for Quantity Values", value = "") {  }
			CustomTextField(placeholder = "User Display Name", value = "") {  }
			CustomTextField(placeholder = "Intro Message", value = "") {  }
			CustomTextField(placeholder = "Query Input Placeholder", value = "") {  }

			Text(
				text = "Clear All Message on Close",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			CustomTextField(placeholder = "Title", value = "") {  }

			Text(
				text = "Dashboard Background Color",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Light Theme Accent Color",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Dark Theme Accent Color",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			CustomTextField(placeholder = "Maximum Number of Message", value = "") {  }

			Text(
				text = "Enable Explore Queries Tab",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Notification Tab",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)

			Text(
				text = "Enable Speech to Text",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			//endregion
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