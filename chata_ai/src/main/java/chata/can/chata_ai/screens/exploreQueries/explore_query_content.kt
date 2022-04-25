package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import chata.can.chata_ai.compose.model.RelatedQueriesData
import chata.can.chata_ai.compose.model.emptyValidateQueryData

@Composable
fun ExploreQueryContent(viewModel: ExploreQueriesViewModel = hiltViewModel()) {
	val value = viewModel.data.value
	val data = value.data?.data ?: emptyValidateQueryData()
	val query = data.query

	val repository2Data = viewModel.relatedQueryData.value
	val relatedQueriesData = repository2Data.data?.data ?: RelatedQueriesData()
	val items = relatedQueriesData.items
	val pagination = relatedQueriesData.pagination

	Scaffold {
		Column {
			if (query.isNotEmpty()) {
				Text(text = "Query => $query")
			}
			if (items.isNotEmpty()) {
				Text(text = "Items count => ${items.size}")
			}
			ExploreQueriesSearch(viewModel = viewModel)
			ExploreQueriesMiddle(modifier = Modifier.weight(1f))
			ExploreQueriesBottom()
		}
	}
}