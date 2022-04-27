package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.model.RelatedQueriesPagination
import chata.can.chata_ai.compose.widget.CircularText
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentColor
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentDisableCompose
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesBottom(
	modifier: Modifier = Modifier,
	querySearch: String,
	relatedQueriesPagination: RelatedQueriesPagination = RelatedQueriesPagination(),
	viewModel: ExploreQueriesViewModel
) {
	//call set oval
	val selectedFirstPage: Boolean
	val selectedLastPage: Boolean
	val selectedCenterPage: Boolean

	val currentAccent = currentAccentColor()
	val textColorPrimary = ThemeColor.currentColor.drawerTextColorPrimary()

	val currentPage = relatedQueriesPagination.current_page
	val totalPages = relatedQueriesPagination.total_pages
	val pageSize = relatedQueriesPagination.page_size

	//region manage currentPage
	val previousTextColor: Color
	val nextTextColor: Color
	val centerPageText: String

	val selectViews = when (currentPage) {
		1, totalPages -> {
			centerPageText = "..."
			if (currentPage == 1) {
				previousTextColor = currentAccentDisableCompose()
				nextTextColor = currentAccent
				Triple(first = true, second = false, third = false)
			} else {
				previousTextColor = currentAccent
				nextTextColor = currentAccentDisableCompose()
				Triple(first = false, second = false, third = true)
			}
		}
		else -> {
			previousTextColor = currentAccent
			nextTextColor = currentAccent
			centerPageText = "$currentPage"
			Triple(first = false, second = true, third = false)
		}
	}

	selectedFirstPage = selectViews.first
	selectedCenterPage = selectViews.second
	selectedLastPage = selectViews.third
	//endregion

	Row(
		modifier = modifier
			.height(56.dp)
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		//region tvPrevious
		Text(
			modifier = Modifier
				.weight(1f)
				.clickable {
					if (currentPage > 1) {
						viewModel.relatedQuery(query = querySearch, pageSize = pageSize, page = currentPage - 1)
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
				if (currentPage != 1) {
					viewModel.relatedQuery(query = querySearch, pageSize = pageSize, page = 1)
				}
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
			)
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
				if (currentPage != totalPages) {
					viewModel.relatedQuery(query = querySearch, pageSize = pageSize, page = totalPages)
				}
			}
		}
		//endregion
		//region tvNext
		Text(
			modifier = Modifier
				.weight(1f)
				.clickable {
					if (currentPage < totalPages) {
						viewModel.relatedQuery(query = querySearch, pageSize = pageSize, page = currentPage + 1)
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