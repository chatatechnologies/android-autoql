package chata.can.chata_ai.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.color.ThemeColor
import java.util.*

@Composable
fun ScreenAutocomplete(
	placeholder: String = "Placeholder",
	content: @Composable () -> Unit
) {
	val backgroundColor = ThemeColor.currentColor.drawerColorSecondary()
	val textFieldValue = remember { mutableStateOf(TextFieldValue("")) }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(backgroundColor),
		verticalArrangement = Arrangement.Bottom
	) {
		Box(
			contentAlignment = Alignment.BottomCenter,
			modifier = Modifier.weight(1f)
		) {
			content()
			if (textFieldValue.value.text.isNotEmpty()) {
				CountryList(
					modifier = Modifier.align(Alignment.BottomCenter),
					textValue = textFieldValue
				)
			}
		}
		SearchItemList(text = textFieldValue, placeholder = placeholder)
	}
}

@Composable
fun SearchItemList(
	text: MutableState<TextFieldValue>,
	textColor: Color = ThemeColor.currentColor.drawerTextColorPrimary(),
	placeholder: String = "Search item",
	placeholderColor: Color = colorResource(id = R.color.place_holder)
) {
	val focusManager = LocalFocusManager.current
	val blueColor = colorResource(id = R.color.blue_chata_circle)
	val background = ThemeColor.currentColor.drawerBackgroundColor()

	Row(
		horizontalArrangement = Arrangement.End,
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(
			modifier = Modifier
				.padding(8.dp)
				.background(background, RoundedCornerShape(50))
				.weight(1f)
		) {
			TextField(
				value = text.value,
				onValueChange = {
					text.value = it
				},
				placeholder = { Text(text = placeholder) },
				textStyle = TextStyle(fontSize = 18.sp),
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
					backgroundColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent,
					disabledIndicatorColor = Color.Transparent
				)
			)
		}

		Box(
			modifier = Modifier
				.padding(4.dp)
				.background(blueColor, CircleShape)
		) {
			Image(
				contentDescription = "Microphone for speaking",
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.size(48.dp)
					.padding(8.dp),
				painter = painterResource(id = R.drawable.ic_microphone),
			)
		}
	}
}

@Composable
fun CountryList(
	modifier: Modifier = Modifier,
	textValue: MutableState<TextFieldValue>,
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
			if (resultList.size == 1) {
				if (resultList[0] == textValue.value.text) {
					resultList.clear()
				}
			}
			resultList
		}
		items(filteredList) { filteredCountries ->
			CountryListItem(
				countryText = filteredCountries,
				onItemClick = { selectedCountry ->
					focusManager.clearFocus()
					textValue.value = TextFieldValue(selectedCountry)
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