package chata.can.chata_ai.compose.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.compose.ui.theme.Purple500
import java.util.*

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ApiChataTheme {
				Surface(color = MaterialTheme.colors.background) {
					Scaffold(
						topBar = { TopBar() },
						backgroundColor = Purple500
					) {
						CountryNavigation()
					}
				}
			}
		}
	}
}

@Composable
fun TopBar() {
	TopAppBar(
		title = { Text(text = "Country List", fontSize = 20.sp) },
		backgroundColor = Purple500,
		contentColor = Color.White
	)
}

@Composable
fun CountryNavigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = "countryList") {
		composable("countryList") {
			CountryListScreen(navController)
		}

	}
}

@Composable
fun CountryListScreen(navController: NavHostController) {
	val textVal = remember { mutableStateOf(TextFieldValue("")) }
	Column {
		SearchCountryList(textVal)
		CountryList(textVal)
	}
}

@Composable
fun SearchCountryList(textVal: MutableState<TextFieldValue>) {
	TextField(
		value = textVal.value,
		onValueChange = { textVal.value = it },
		modifier = Modifier.fillMaxWidth(),
		textStyle = TextStyle(Color.White, fontSize = 18.sp),
		leadingIcon = {
			Icon(
				imageVector = Icons.Filled.Search,
				contentDescription = "Search",
				modifier = Modifier
					.padding(15.dp)
					.size(24.dp)
			)
		},
		trailingIcon = {
			if (textVal.value != TextFieldValue("")) {
				IconButton(onClick = {
					textVal.value = TextFieldValue("")
				}) {
					Icon(
						imageVector = Icons.Filled.Close,
						contentDescription = "Close",
						modifier = Modifier
							.padding(15.dp)
							.size(24.dp)
					)
				}
			}
		},
		singleLine = true,
		shape = RectangleShape,
		colors = TextFieldDefaults.textFieldColors(
			textColor = Color.White,
			cursorColor = Color.White,
			leadingIconColor = Color.White,
			trailingIconColor = Color.White,
			backgroundColor = Color.LightGray,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			disabledIndicatorColor = Color.Transparent
		)
	)
}

@Composable
fun CountryList(textVal: MutableState<TextFieldValue>) {
	val countries = getListOfCountries()
}

@Composable
fun getListOfCountries(): ArrayList<String> {
	val isoCountryCodes = Locale.getISOCountries()
	val countryListWithEmojis = ArrayList<String>()
	for (countryCode in isoCountryCodes) {
		val locale = Locale("", countryCode)
		val countryName = locale.displayCountry
		val flagOffset = 0x1FF1E6
		val asciiOffset = 0x41
		val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
		val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
		val flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
		countryListWithEmojis.add("$countryName (${locale.country} $flag")
	}
	return countryListWithEmojis
}