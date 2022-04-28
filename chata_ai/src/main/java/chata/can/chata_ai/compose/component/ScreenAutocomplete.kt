package chata.can.chata_ai.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import java.util.*

@Composable
fun ScreenAutocomplete(content: @Composable (selected: String) -> Unit) {
	val textFieldValue = remember { mutableStateOf(TextFieldValue("")) }
	val textFieldSelected = remember { mutableStateOf(TextFieldValue("")) }
	Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
		Box(
			contentAlignment = Alignment.BottomCenter,
			modifier = Modifier.weight(1f)
		) {
			content(textFieldSelected.value.text)
			if (textFieldValue.value.text.isNotEmpty()) {
				CountryList(
					modifier = Modifier.align(Alignment.BottomCenter),
					textValue = textFieldValue,
					textValueSelected = textFieldSelected
				)
			}
		}
		SearchItemList(textFieldValue)
	}
}

@Composable
fun SearchItemList(
	text: MutableState<TextFieldValue>,
	textColor: Color = Color.Black,
	placeholder: String = "Search item",
	placeholderColor: Color = Color.Black
) {
	val focusManager = LocalFocusManager.current
	TextField(
		value = text.value,
		onValueChange = { text.value = it },
		placeholder = { Text(text = placeholder) },
		modifier = Modifier
			.fillMaxWidth(),
		textStyle = TextStyle(fontSize = 18.sp),
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
			if (text.value != TextFieldValue("")) {
				IconButton(
					onClick = {
						text.value = TextFieldValue("")
					}
				) {
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
		keyboardActions = KeyboardActions(
			onDone = { focusManager.clearFocus() }
		),
		colors = TextFieldDefaults.textFieldColors(
			textColor = textColor,
			placeholderColor = placeholderColor,
			cursorColor = Color.Black,
			leadingIconColor = Color.Black,
			trailingIconColor = Color.Black,
			backgroundColor = Color.White,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			disabledIndicatorColor = Color.Transparent
		)
	)
}

@Composable
fun CountryList(
	modifier: Modifier = Modifier,
	textValue: MutableState<TextFieldValue>,
	textValueSelected: MutableState<TextFieldValue>,
	list: ArrayList<String> = getListOfCountries()
) {
	val focusManager = LocalFocusManager.current
	var filteredList: ArrayList<String>
	LazyColumn(
		verticalArrangement = Arrangement.Bottom,
		modifier = modifier.fillMaxWidth()
	) {
		val searchText = textValue.value.text
		filteredList = if (searchText.isEmpty()) {
			arrayListOf() // before countries
		} else {
			val resultList = ArrayList<String>()
			for (country in list) {
				if (country.lowercase(Locale.getDefault())
						.contains(searchText.lowercase(Locale.getDefault()))
				) {
					resultList.add(country)
				}
			}
			resultList
		}
		items(filteredList) { filteredCountries ->
			CountryListItem(
				countryText = filteredCountries,
				onItemClick = { selectedCountry ->
					focusManager.clearFocus()
					textValue.value = TextFieldValue("")
					textValueSelected.value = TextFieldValue(selectedCountry)
				}
			)
		}
	}
}

@Composable
fun CountryListItem(
	countryText: String,
	onItemClick: (String) -> Unit
) {
	Row(
		modifier = Modifier
			.clickable {
				onItemClick(countryText)
			}
			.background(Color.White)
			.height(60.dp)
			.fillMaxWidth()
			.padding(5.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = countryText,
			fontSize = 16.sp,
			color = Color.Black,
			modifier = Modifier.padding(start = 10.dp)
		)
	}
}

@Composable
fun getListOfCountries(): ArrayList<String> {
	val isoCountryCodes = Locale.getISOCountries()
	val countryListWithEmojis = ArrayList<String>()
	for (countryCode in isoCountryCodes) {
		val locale = Locale("", countryCode)
		val countryName = locale.displayCountry
		val flagOffset = 0x1F1E6
		val asciiOffset = 0x41
		val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
		val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
		val flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
		countryListWithEmojis.add("$countryName (${locale.country}) $flag")
	}
	return countryListWithEmojis
}

@Preview(showBackground = true)
@Composable
fun AutoCompleteTextViewPreview() {
	ApiChataTheme {
		Scaffold {
			Box {
				Column(modifier = Modifier.fillMaxSize()) {
					val list = (0..20).toList()
					LazyColumn(modifier = Modifier.fillMaxWidth()) {
						items(list) { num ->
							Text(text = "$num")
						}
					}
				}
				ScreenAutocomplete {

				}
			}
		}
	}
}