package chata.can.chata_ai_api.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.component.*
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai_api.BuildConfig
import chata.can.chata_ai_api.component.ColorText
import chata.can.chata_ai_api.component.ProgressScreenComponent
import chata.can.chata_ai_api.component.TitleSection
import chata.can.chata_ai_api.util.Constant

@Composable
fun InputConfigScreen(viewModel: InputConfigViewModel) {
//	val isShowState = remember { mutableStateOf(false) }
	val isShowProgress = viewModel.isShowProgress
	val mIsAuthenticate = viewModel.isAuthenticate
	val mIsEnable = viewModel.isEnableLogin

	var projectId by remember { mutableStateOf(Constant.projectID) }
	var userEmail by remember { mutableStateOf(Constant.userEmail) }
	var apiKey by remember { mutableStateOf(Constant.apiKey) }
	var domainUrl by remember { mutableStateOf(Constant.domainUrl) }
	var username by remember { mutableStateOf(Constant.username) }
	var password by remember { mutableStateOf(Constant.password) }

	if (BuildConfig.DEBUG) {
		AutoQLData.projectId = projectId
		AutoQLData.userID = userEmail
		AutoQLData.apiKey = apiKey
		AutoQLData.domainUrl = domainUrl
		AutoQLData.username = username
		AutoQLData.password = password
	}

	ProgressScreenComponent(isShowProgress = isShowProgress.value) {
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 12.dp, vertical = 4.dp),
			color = Color.White
		) {
			Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
				//region Authentication
				TitleSection("Authentication")
				CustomOutlinedTextField(placeholder = "Project ID", value = projectId) {
					AutoQLData.projectId = it
					projectId = it
				}
				RequiredField()
				Spacer(modifier = Modifier.height(4.dp))
				CustomOutlinedTextField(placeholder = "User Email", value = userEmail) {
					AutoQLData.userID = it
					userEmail = it
				}
				RequiredField()
				Spacer(modifier = Modifier.height(4.dp))
				CustomOutlinedTextField(placeholder = "API key", value = apiKey) {
					AutoQLData.apiKey = it
					apiKey = it
				}
				RequiredField()
				Spacer(modifier = Modifier.height(4.dp))
				CustomOutlinedTextField(placeholder = "Domain URL", value = domainUrl) {
					AutoQLData.domainUrl = it
					domainUrl = it
				}
				RequiredField()
				Spacer(modifier = Modifier.height(4.dp))
				CustomOutlinedTextField(placeholder = "Username", value = username) {
					AutoQLData.username = it
					username = it
				}
				RequiredField()
				Spacer(modifier = Modifier.height(4.dp))
				CustomOutlinedTextField(
					placeholder = "Password",
					value = password,
					keyboardType = KeyboardType.Password
				) {
					AutoQLData.password = it
					password = it
				}
				RequiredField()
				Spacer(modifier = Modifier.height(4.dp))
				CustomButton(if (mIsAuthenticate.value) "Log Out" else "Authenticate", mIsEnable.value) {
					viewModel.login()
				}
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
				SwitchContent(text = "Enable Autocomplete") { isChecked ->
					AutoQLData.autoQLConfig.enableAutocomplete = isChecked
				}
				SwitchContent(text = "Enable Query Validation") { isChecked ->
					AutoQLData.autoQLConfig.enableQueryValidation = isChecked
				}
				SwitchContent(text = "Enable Query Suggestion") { isChecked ->
					AutoQLData.autoQLConfig.enableQuerySuggestions = isChecked
				}
				SwitchContent(text = "Enable Drilldowns") { isChecked ->
					AutoQLData.autoQLConfig.enableDrilldowns = isChecked
				}
				SwitchContent(text = "Enable Column Visibility Editor") { isChecked ->
					AutoQLData.isColumnVisibility = isChecked
				}
				SwitchContent(text = "Enable Notifications") { isChecked ->
					AutoQLData.visibleNotification = isChecked
					AutoQLData.activeNotifications = isChecked
				}
				//endregion
				//region UI Configuration Options
				TitleSection("UI Configuration Options")
				SwitchContent(text = "Show Data Messenger Button") {}
				SwitchContent(
					text = "Darken Background Behind Data Messenger",
					isChecked = false
				) { isChecked ->
					AutoQLData.isDarkenBackgroundBehind = isChecked
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

				CustomOutlinedTextField(placeholder = "Currency Code", value = Constant.currencyCode) { }
				CustomOutlinedTextField(placeholder = "Language Code", value = Constant.languageCode) { }
				CustomOutlinedTextField(placeholder = "Format for Day, Year", value = Constant.formatMonthYear) { }
				CustomOutlinedTextField(
					placeholder = "Format for Day, Month, Year",
					value = Constant.formatDayMonthYear
				) { }
				CustomOutlinedTextField(
					placeholder = "Number of Decimals for Currency Values",
					value = "${Constant.numberDecimalCurrencyValues}"
				) { }
				CustomOutlinedTextField(
					placeholder = "Number of Decimals for Quantity Values",
					value = "${Constant.numberDecimalQuantityValues}"
				) { }
				CustomOutlinedTextField(placeholder = "User Display Name", value = Constant.userDisplay) { }
				CustomOutlinedTextField(placeholder = "Intro Message", value = Constant.introMessage) { }
				CustomOutlinedTextField(
					placeholder = "Query Input Placeholder",
					value = Constant.queryInputPlaceholder
				) { }

				SwitchContent(text = "Clear All Message on Close", isChecked = false) { isChecked ->
					AutoQLData.clearOnClose = isChecked
				}

				CustomOutlinedTextField(placeholder = "Title", value = Constant.title) { }

				ColorText("#26A7E9")
				ColorText("#A5CD39")
				ColorText("#DD6A6A")
				ColorText("#FFA700")
				ColorText("#00C1B2")

				Text(
					text = "Dashboard Background Color",
					style = TextStyle(
						fontSize = 16.sp,
						textAlign = TextAlign.Center
					),
					modifier = Modifier.fillMaxWidth()
				)
				ColorText("#FAFAFA")

				Text(
					text = "Light Theme Accent Color",
					style = TextStyle(
						fontSize = 16.sp,
						textAlign = TextAlign.Center
					),
					modifier = Modifier.fillMaxWidth()
				)
				ColorText("#26A7DF")

				Text(
					text = "Dark Theme Accent Color",
					style = TextStyle(
						fontSize = 16.sp,
						textAlign = TextAlign.Center
					),
					modifier = Modifier.fillMaxWidth()
				)
				ColorText("#26A7DF")

				CustomOutlinedTextField(
					placeholder = "Maximum Number of Message",
					value = "${Constant.maximumNumberMessage}"
				) { }

				SwitchContent(text = "Enable Explore Queries Tab") { isChecked ->
					AutoQLData.visibleExploreQueries = isChecked
				}
				SwitchContent(text = "Enable Notification Tab") { isChecked ->
					AutoQLData.visibleNotification = isChecked
				}
				SwitchContent(text = "Enable Speech to Text") { isChecked ->
					AutoQLData.enableVoiceRecord = isChecked
				}
				//endregion
			}
		}
	}
}