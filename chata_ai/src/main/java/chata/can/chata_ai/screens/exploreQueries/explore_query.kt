package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.widget.CustomCircularProgress
import chata.can.chata_ai.compose.widget.CustomTextField
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentColor
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesSearch(
	modifier: Modifier = Modifier,
	viewModel: ExploreQueriesViewModel,
	getSearch: (String) -> Unit
) {
	val drawerBackgroundColor = ThemeColor.currentColor.drawerBackgroundColor()
	val drawerTextColorPrimary = ThemeColor.currentColor.drawerTextColorPrimary()
	val placeholderColor = colorResource(id = R.color.place_holder)

	var queryDemo = ""
	if (BuildConfig.DEBUG) {
		queryDemo = "revenue"
	}
	var search by remember { mutableStateOf(queryDemo) }

	Row(
		modifier = modifier
			.padding(horizontal = 8.dp, vertical = 4.dp)
			.background(drawerBackgroundColor, RoundedCornerShape(50)),
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			modifier = Modifier
				.size(48.dp)
				.clickable {
					viewModel.validateQuery(search)
				},
			painter = painterResource(id = R.drawable.ic_search),
			contentDescription = "Image Search Query",
			contentScale = ContentScale.Fit,
			alignment = Alignment.Center
		)
		CustomTextField(
			text = search,
			textColor = drawerTextColorPrimary,
			placeholder = stringResource(id = R.string.explore_queries_hint),
			placeholderColor = placeholderColor,
			backgroundColor = Color.Transparent
		) {
			getSearch(it)
			search = it
		}
	}
}

@Composable
fun ExploreQueriesMiddle(modifier: Modifier = Modifier) {
	val drawerTextColorPrimary = ThemeColor.currentColor.drawerTextColorPrimary()
	Column(modifier = modifier.fillMaxWidth()) {
		//region put Gif
		Text(
			text = stringResource(id = R.string.explore_queries_msg_1),
			style = TextStyle(color = drawerTextColorPrimary, textAlign = TextAlign.Center),
			modifier = Modifier
				.padding(end = 8.dp, top = 8.dp, start = 8.dp)
				.fillMaxWidth()
		)
		Text(
			text = stringResource(id = R.string.explore_queries_msg_2),
			style = TextStyle(color = drawerTextColorPrimary, textAlign = TextAlign.Center),
			modifier = Modifier
				.padding(end = 8.dp, top = 8.dp, start = 8.dp)
				.fillMaxWidth()
		)
	}
}

@Composable
fun ExploreQueriesLoading(modifier: Modifier = Modifier) {
	Column(
		modifier = modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		CustomCircularProgress(color = currentAccentColor())
	}
}

@Preview(showBackground = true)
@Composable
fun ExploreQueriesLoadingPreview() {
	ExploreQueriesLoading()
}