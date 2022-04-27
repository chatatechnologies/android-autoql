package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesList(modifier: Modifier = Modifier, queries: List<String>) {
	val drawerColorSecondary = ThemeColor.currentColor.drawerColorSecondary()
	val colorDivider = colorResource(id = android.R.color.darker_gray)
	val state: LazyListState = rememberLazyListState()
	LazyColumn(
		state = state,
		modifier = modifier
			.background(drawerColorSecondary)
			.fillMaxWidth()
	) {
		itemsIndexed(queries) { index, query ->
			CardExploreQueries(text = query)
			if (index < queries.lastIndex) {
				Divider(color = colorDivider, modifier = Modifier.height(0.5f.dp))
			}
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