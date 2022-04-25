package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.widget.CustomTextField

@Composable
fun ExploreQueriesSearch(modifier: Modifier = Modifier) {
//	val colorTextField = Color(ThemeColor.currentColor.pDrawerTextColorPrimary)
	val colorTextField = Color.Black
//	val placeholderColor = colorResource(id = R.color.place_holder)
	val placeholderColor = Color.Black
	var search by remember { mutableStateOf("") }

	Row(
		modifier = modifier
			.padding(horizontal = 8.dp, vertical = 4.dp)
			.background(Color.Gray, RoundedCornerShape(50)),
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			modifier = Modifier.size(48.dp),
			painter = painterResource(id = R.drawable.ic_search),
			contentDescription = "Image Search Query",
			contentScale = ContentScale.Fit,
			alignment = Alignment.Center
		)
		CustomTextField(
			text = search,
			textColor = colorTextField,
			placeholder = stringResource(id = R.string.explore_queries_hint),
			placeholderColor = placeholderColor,
			backgroundColor = Color.Transparent
		) {
			search = it
		}
	}
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
	ExploreQueriesSearch()
}

@Composable
fun ExploreQueriesMiddle(modifier: Modifier = Modifier) {
	val color = colorResource(id = R.color.text_explore_queries)
	Column(modifier = modifier.fillMaxWidth()) {
		//region put Gif
		Text(
			text = stringResource(id = R.string.explore_queries_msg_1),
			style = TextStyle(color = color, textAlign = TextAlign.Center),
			modifier = Modifier
				.padding(end = 8.dp, top = 8.dp, start = 8.dp)
				.fillMaxWidth()
		)
		Text(
			text = stringResource(id = R.string.explore_queries_msg_2),
			style = TextStyle(color = color, textAlign = TextAlign.Center),
			modifier = Modifier
				.padding(end = 8.dp, top = 8.dp, start = 8.dp)
				.fillMaxWidth()
		)
	}
}

//ExploreQueriesList()