package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExploreQueryContent() {
	Scaffold {
		Column {
			ExploreQueriesSearch()
			ExploreQueriesMiddle(modifier = Modifier.weight(1f))
			ExploreQueriesBottom()
		}
	}
}