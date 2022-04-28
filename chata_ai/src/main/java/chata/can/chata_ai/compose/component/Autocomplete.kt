package chata.can.chata_ai.compose.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun AutoCompleteTextView() {
	val textVal = remember { mutableStateOf(TextFieldValue("")) }
	Column {
		CountryList(textVal, modifier = Modifier.weight(1f))
		SearchItemList(textVal)
	}
}

@Composable
fun SearchItemList(
	text: MutableState<TextFieldValue>,
	textColor: Color = Color.Black,
	placeholder: String = "Search item",
	placeholderColor: Color = Color.Black
) {
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
fun CountryList(textVal: MutableState<TextFieldValue>, modifier: Modifier = Modifier) {
	val context = LocalContext.current
	val countries = getListOfCountries()
	var filteredCountries: ArrayList<String>
	LazyColumn(
		verticalArrangement = Arrangement.Bottom,
		modifier = modifier.fillMaxWidth()
	) {
		val searchText = textVal.value.text
		filteredCountries = if (searchText.isEmpty()) {
			countries
		} else {
			val resultList = ArrayList<String>()
			for (country in countries) {
				if (country.lowercase(Locale.getDefault())
						.contains(searchText.lowercase(Locale.getDefault()))
				) {
					resultList.add(country)
				}
			}
			resultList
		}
		items(filteredCountries) { filteredCountries ->
			CountryListItem(
				countryText = filteredCountries,
				onItemClick = { selectedCountry ->
					Toast.makeText(context, selectedCountry, Toast.LENGTH_SHORT).show()
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