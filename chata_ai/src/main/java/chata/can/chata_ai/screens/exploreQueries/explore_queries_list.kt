package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesList(modifier: Modifier = Modifier, queries: List<String>) {
	val state: LazyListState = rememberLazyListState()
	LazyColumn(
		state = state,
		modifier = modifier
			.background(Color.Black)
			.fillMaxWidth()
	) {
		items(queries) { query ->
			CardExploreQueries(text = query)
		}
	}
}

@Composable
fun CardExploreQueries(text: String) {
	val textColor = ThemeColor.currentColor.drawerTextColorPrimary()
	Text(
		text = text,
		style = TextStyle(color = textColor, fontSize = 16.sp, textAlign = TextAlign.Center),
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp)
	)
}