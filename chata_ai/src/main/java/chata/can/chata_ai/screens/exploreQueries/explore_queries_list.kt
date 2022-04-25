package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ExploreQueriesList() {
	val items = listOf(1, 2, 3, 4)
	val state: LazyListState = rememberLazyListState()
	Scaffold { paddingValues ->
		LazyColumn(
			state = state,
			modifier = Modifier
				.background(Color.White)
				.fillMaxSize(),
			contentPadding = paddingValues
		) {
			items(items) { query ->
				//card query information
			}
		}
	}
}