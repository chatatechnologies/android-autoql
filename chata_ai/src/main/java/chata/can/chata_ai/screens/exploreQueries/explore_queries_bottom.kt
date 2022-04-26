package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.model.RelatedQueriesPagination
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentColor
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentDisableCompose
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesBottom(
	modifier: Modifier = Modifier,
	relatedQueriesPagination: RelatedQueriesPagination = RelatedQueriesPagination()
) {
	//call set oval
	var selectedFirstPage = true
	var selectedLastPage = false
	var selectedCenterPage = false

	val currentAccent = currentAccentColor()
	val textColorPrimary = ThemeColor.currentColor.drawerTextColorPrimary()

	val currentPage = relatedQueriesPagination.current_page
	val totalPages = relatedQueriesPagination.total_pages
	val pageSize = relatedQueriesPagination.page_size

	//region manage currentPage
	val previousTextColor: Color
	val nextTextColor: Color
	val centerPageText: String

	when (currentPage) {
		1, totalPages -> {
			if (currentPage == 1) {
				previousTextColor = currentAccentDisableCompose()
				nextTextColor = currentAccent

				selectedFirstPage = true
				selectedLastPage = false
				selectedCenterPage = false
			} else {
				previousTextColor = currentAccent
				nextTextColor = currentAccentDisableCompose()

				selectedFirstPage = false
				selectedLastPage = true
				selectedCenterPage = false
			}
			centerPageText = "..."
		}
		else -> {
			previousTextColor = currentAccent
			nextTextColor = currentAccent
			centerPageText = "$currentPage"

			selectedFirstPage = false
			selectedLastPage = false
			selectedCenterPage = true
		}
	}
	//endregion

	Row(
		modifier = modifier
			.height(56.dp)
			.fillMaxWidth()
			.background(Color.LightGray),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		//region tvPrevious
		Text(
			modifier = Modifier
				.weight(1f)
				.clickable {
					if (currentPage > 1) {
//					exploreQueriesViewModel.relatedQuery(
//						query = ExploreQueriesProvider.lastQuery,
//						pageSize = _pageSize,
//						page = _currentPage - 1
//					)
					}
				},
			text = stringResource(id = R.string.arrow_left),
			style = TextStyle(
				color = previousTextColor,
				fontSize = 32.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
		)
		//endregion
		//region tvFirstPage
		Text(
			modifier = Modifier
				.weight(1f)
				.fillMaxWidth(0.5f)
				.height(30.dp)
				.background(
					if (selectedFirstPage) currentAccentColor() else Color.Transparent,
					RoundedCornerShape(100)
				)
				.clickable {
					if (currentPage != 1) {
//					exploreQueriesViewModel.relatedQuery(
//						query = ExploreQueriesProvider.lastQuery,
//						pageSize = _pageSize,
//						page = 1
					}
				},
			text = stringResource(id = R.string.one),
			style = TextStyle(
				color = textColorPrimary,
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
		)
		//endregion
		//region tvCenterPage
		Text(
			modifier = Modifier
				.weight(1f)
				.height(30.dp),
			text = centerPageText,
			style = TextStyle(
				color = textColorPrimary,
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
		)
		//endregion
		//region tvLastPage
		Text(
			modifier = Modifier
				.weight(1f)
				.height(30.dp)
				.clickable {
					if (currentPage != totalPages) {
//					exploreQueriesViewModel.relatedQuery(
//						query = ExploreQueriesProvider.lastQuery,
//						pageSize = _pageSize,
//						page = _numItems
//					)
					}
				},
			text = if (totalPages >= currentPage) "$totalPages" else "",
			style = TextStyle(
				color = textColorPrimary,
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
		)
		//endregion
		//region tvNext
		Text(
			modifier = Modifier
				.weight(1f)
				.clickable {
					if (currentPage < totalPages) {
//					exploreQueriesViewModel.relatedQuery(
//						query = ExploreQueriesProvider.lastQuery,
//						pageSize = _pageSize,
//						page = _currentPage + 1
//					)
					}
				},
			text = stringResource(id = R.string.arrow_right),
			style = TextStyle(
				color = nextTextColor,
				fontSize = 32.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
		)
		//endregion
	}
}

@Preview
@Composable
fun ExploreQueriesBottomPreview() {
	ApiChataTheme {
		Scaffold {
			ExploreQueriesBottom()
		}
	}
}