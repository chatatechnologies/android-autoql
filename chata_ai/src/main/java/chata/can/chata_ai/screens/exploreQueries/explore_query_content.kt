package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.model.RelatedQueriesPagination
import chata.can.chata_ai.compose.widget.GifImage

@Composable
fun ExploreQueryContent(viewModel: ExploreQueriesViewModel = hiltViewModel()) {
	val repository2Data = viewModel.relatedQueryData.value
//	val loading2 = repository2Data.loading
	val relatedQueriesData = repository2Data.data?.data
	val items = relatedQueriesData?.items ?: listOf()
	val pagination = relatedQueriesData?.pagination ?: RelatedQueriesPagination()

	Scaffold {
		Column {
			GifImage(imageID = R.drawable.gif_balls)

			ExploreQueriesSearch(viewModel = viewModel)
			if (items.isEmpty())
				ExploreQueriesMiddle(modifier = Modifier.weight(1f))
			else {
				ExploreQueriesList(modifier = Modifier.weight(1f), items)
				ExploreQueriesBottom(relatedQueriesPagination = pagination)
			}
		}
	}
}