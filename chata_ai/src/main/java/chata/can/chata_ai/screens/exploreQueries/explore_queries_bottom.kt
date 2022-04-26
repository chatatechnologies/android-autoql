package chata.can.chata_ai.screens.exploreQueries

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import chata.can.chata_ai.compose.widget.CircularText
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentColor
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentDisableCompose
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesBottom(
	modifier: Modifier = Modifier,
	relatedQueriesPagination: RelatedQueriesPagination = RelatedQueriesPagination()
) {
	//call set oval
	var selectedFirstPage: Boolean
	var selectedLastPage: Boolean
	var selectedCenterPage: Boolean

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
				selectedCenterPage = false
				selectedLastPage = false
			} else {
				previousTextColor = currentAccent
				nextTextColor = currentAccentDisableCompose()

				selectedFirstPage = false
				selectedCenterPage = false
				selectedLastPage = true
			}
			centerPageText = "..."
		}
		else -> {
			previousTextColor = currentAccent
			nextTextColor = currentAccent
			centerPageText = "$currentPage"

			selectedFirstPage = false
			selectedCenterPage = true
			selectedLastPage = false
		}
	}
	//endregion

	selectedFirstPage = false
	selectedCenterPage = false
	selectedLastPage = true

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
		Column(
			modifier = Modifier.weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CircularText(
				text = stringResource(id = R.string.one),
				textSize = 20,
				backgroundColor =
				if (selectedFirstPage) currentAccentColor() else Color.Transparent,
				textColor = textColorPrimary
			) {
				//					exploreQueriesViewModel.relatedQuery(
//						query = ExploreQueriesProvider.lastQuery,
//						pageSize = _pageSize,
//						page = 1
				Log.e("tvFirstPage", "tvFirstPage was clicked")
			}
		}
		//endregion
		//region tvCenterPage
		Column(
			modifier = Modifier.weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CircularText(
				text = centerPageText,
				textSize = 20,
				backgroundColor =
				if (selectedCenterPage) currentAccentColor() else Color.Transparent,
				textColor = textColorPrimary
			) {
				Log.e("tvCenterPage", "tvCenterPage was clicked")
			}
		}
		//endregion
		//endregion
		//region tvLastPage
		Column(
			modifier = Modifier.weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CircularText(
				text = if (totalPages >= currentPage) "$totalPages" else "",
				textSize = 20,
				backgroundColor =
				if (selectedLastPage) currentAccentColor() else Color.Transparent,
				textColor = textColorPrimary
			) {
				//					exploreQueriesViewModel.relatedQuery(
//						query = ExploreQueriesProvider.lastQuery,
//						pageSize = _pageSize,
//						page = _numItems
//					)
				Log.e("tvLastPage", "tvLastPage was clicked")
			}
		}
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