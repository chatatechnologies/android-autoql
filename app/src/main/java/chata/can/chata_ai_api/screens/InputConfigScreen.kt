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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.component.*
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai_api.component.TitleSection
import chata.can.chata_ai_api.util.Constant

@Composable
fun InputConfigScreen() {
	Surface(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 12.dp, vertical = 4.dp),
		color = Color.White
	) {
		var projectId by remember { mutableStateOf(Constant.projectID) }
		var userEmail by remember { mutableStateOf(Constant.userEmail) }
		var apiKey by remember { mutableStateOf(Constant.apiKey) }
		var domainUrl by remember { mutableStateOf(Constant.domainUrl) }
		var username by remember { mutableStateOf(Constant.username) }
		var password by remember { mutableStateOf(Constant.password) }
		var authenticate by remember { mutableStateOf("Authentication") }
		/*  */

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
			CustomButton(authenticate)
			//endregion
			//region Customize Widgets
			TitleSection("Customize Widgets")
			MultiToggleButton(
				currentSelection = "Open Data Messenger",
				toggleStates = listOf("Reload Data Messenger", "Open Data Messenger")
			) { value ->
				value
			}

			TitleSection("AutoQL Api Configuration Options")
			SwitchContent(text = "Enable Autocomplete") { isAutocomplete ->

			}
			SwitchContent(text = "Enable Query Validation") { isQueryValidation ->

			}
			SwitchContent(text = "Enable Query Suggestion") { isQuerySuggestion ->

			}
			SwitchContent(text = "Enable Drilldowns") { isDrillDown ->

			}
			SwitchContent(text = "Enable Column Visibility Editor") { isColumVisibility ->

			}
			SwitchContent(text = "Enable Notifications") { isNotification ->

			}
			//endregion
			//region UI Configuration Options
			TitleSection("UI Configuration Options")
			SwitchContent(text = "Show Data Messenger Button") { isShowData ->

			}
			SwitchContent(
				text = "Darken Background Behind Data Messenger",
				isChecked = false
			) { isShowData ->

			}

			Text(
				text = "Theme",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			MultiToggleButton(
				currentSelection = "Dark",
				toggleStates = listOf("Light", "Dark")
			) { value ->
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
			MultiToggleButton(
				currentSelection = "Right",
				toggleStates = listOf("Top", "Bottom", "Left", "Right")
			) { value ->
				value
			}

			Text(
				text = "Default Tab",
				style = TextStyle(
					fontSize = 16.sp,
					textAlign = TextAlign.Center
				),
				modifier = Modifier.fillMaxWidth()
			)
			MultiToggleButton(
				currentSelection = "data-messenger",
				toggleStates = listOf("data-messenger", "explore-queries")
			) { value ->
				value
			}

			CustomTextField(placeholder = "Currency Code", value = Constant.currencyCode) { }
			CustomTextField(placeholder = "Language Code", value = Constant.languageCode) { }
			CustomTextField(placeholder = "Format for Day, Year", value = Constant.formatMonthYear) { }
			CustomTextField(
				placeholder = "Format for Day, Month, Year",
				value = Constant.formatDayMonthYear
			) { }
			CustomTextField(
				placeholder = "Number of Decimals for Currency Values",
				value = "${Constant.numberDecimalCurrencyValues}"
			) { }
			CustomTextField(
				placeholder = "Number of Decimals for Quantity Values",
				value = "${Constant.numberDecimalQuantityValues}"
			) { }
			CustomTextField(placeholder = "User Display Name", value = Constant.userDisplay) { }
			CustomTextField(placeholder = "Intro Message", value = Constant.introMessage) { }
			CustomTextField(
				placeholder = "Query Input Placeholder",
				value = Constant.queryInputPlaceholder
			) { }

			SwitchContent(text = "Clear All Message on Close", isChecked = false) { isClearAll ->

			}

			CustomTextField(placeholder = "Title", value = Constant.title) { }

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

			CustomTextField(
				placeholder = "Maximum Number of Message",
				value = "${Constant.maximumNumberMessage}"
			) { }

			SwitchContent(text = "Enable Explore Queries Tab") { isExploreQueries ->

			}
			SwitchContent(text = "Enable Notification Tab") { isNotification ->

			}
			SwitchContent(text = "Enable Speech to Text") { isSpeech ->

			}
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