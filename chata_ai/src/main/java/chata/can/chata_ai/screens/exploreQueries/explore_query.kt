package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.component.CustomTextField
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesSearch() {
	val colorTextField = Color(ThemeColor.currentColor.pDrawerTextColorPrimary)
	val placeholderColor = colorResource(id = R.color.place_holder)
	var search by remember { mutableStateOf("") }
	Row(modifier = Modifier.padding(8.dp)) {
		CustomTextField(
			modifier = Modifier
				.height(48.dp)
				.padding(horizontal = 16.dp, vertical = 8.dp)
				.weight(1f),
			placeholder = stringResource(id = R.string.explore_queries_hint),
			value = search,
			textColor = colorTextField,
			placeholderColor = placeholderColor
		) {
			search = it
		}

		Image(
			modifier = Modifier.size(48.dp),
			painter = painterResource(id = R.drawable.ic_search),
			contentDescription = "Image Search Query",
			contentScale = ContentScale.Fit,
			alignment = Alignment.Center
		)
	}
}

@Preview
@Composable
fun SearchPreview() {
	ExploreQueriesSearch()
}