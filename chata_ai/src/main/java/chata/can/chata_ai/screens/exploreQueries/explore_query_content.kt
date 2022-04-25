package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExploreQueryContent(viewModel: ExploreQueriesViewModel = hiltViewModel()) {
	val value = viewModel.data.value
//	val data = value.data?.data ?: emptyValidateQueryData()

	val repository2Data = viewModel.relatedQueryData.value
//	val loading2 = repository2Data.loading
	val relatedQueriesData = repository2Data.data?.data
	val items = relatedQueriesData?.items ?: listOf()
	val pagination = relatedQueriesData?.pagination

	Scaffold {
		Column {
//			if (query.isNotEmpty()) {
//				Text(text = "Query requested => $query")
//			}
//			if (loading2 == false) {
//				Text(text = "Repository has been finished", style = TextStyle(color = Color.Green))
//			} else {
//				Text(text = "Repository is loading", style = TextStyle(color = Color.Red))
//			}
//
//			if (items.isNotEmpty()) {
//				Text(text = "Items is ready!", style = TextStyle(color = Color.Green))
//			}
//			if (pagination != null) {
//				Text(text = "Pagination is ready!", style = TextStyle(color = Color.Green))
//			}

			ExploreQueriesSearch(viewModel = viewModel)
			if (items.isEmpty())
				ExploreQueriesMiddle(modifier = Modifier.weight(1f))
			else
				ExploreQueriesList(items)
			ExploreQueriesBottom()
		}
	}
}