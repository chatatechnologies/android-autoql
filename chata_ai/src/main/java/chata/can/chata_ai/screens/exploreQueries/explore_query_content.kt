package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import chata.can.chata_ai.compose.model.RelatedQueriesPagination
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueryContent(viewModel: ExploreQueriesViewModel = hiltViewModel()) {
	val drawerBackgroundColor: Color = ThemeColor.currentColor.drawerColorSecondary()

	val relatedQueryData = viewModel.relatedQueryData.value
	val loading = viewModel.loading.value

	val relatedQueriesData = relatedQueryData.data?.data
	val items = relatedQueriesData?.items
	val pagination = relatedQueriesData?.pagination ?: RelatedQueriesPagination()

	var querySearch by remember { mutableStateOf("") }

	Scaffold {
		Column(modifier = Modifier.background(drawerBackgroundColor)) {
			ExploreQueriesSearch(viewModel = viewModel) {
				querySearch = it
			}
			val weight1f = Modifier.weight(1f)
			if (loading) {
				ExploreQueriesLoading(modifier = weight1f)
			} else {
				if (items == null) {
					ExploreQueriesMiddle(modifier = weight1f)
				} else {
					if (items.isEmpty()) {
						EmptyDataExploreQueries(modifier = weight1f)
					} else {
						ExploreQueriesList(modifier = weight1f, items)
						ExploreQueriesBottom(
							modifier = Modifier.background(drawerBackgroundColor),
							querySearch = querySearch,
							relatedQueriesPagination = pagination,
							viewModel = viewModel
						)
					}
				}
			}
		}
	}
}