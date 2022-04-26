package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentColor
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentDisableCompose
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun ExploreQueriesBottom(
	modifier: Modifier = Modifier,
	relatedQueriesPagination: RelatedQueriesPagination
) {
	val currentAccent = currentAccentColor()
	val textColorPrimary = ThemeColor.currentColor.drawerTextColorPrimary()

	val currentPage = relatedQueriesPagination.current_page
	val totalPages = relatedQueriesPagination.total_pages
	val pageSize = relatedQueriesPagination.page_size

	Row(
		modifier = modifier
			.height(56.dp)
			.fillMaxWidth()

			.background(Color.LightGray),
		verticalAlignment = Alignment.CenterVertically
	) {
		//region tvPrevious
		Text(
			modifier = Modifier.weight(1f),
			text = stringResource(id = R.string.arrow_left),
			style = TextStyle(
				color = currentAccentDisableCompose(),
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
				.height(30.dp),
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
			text = "",
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
				.height(30.dp),
			text = if (totalPages >= currentPage)"$totalPages" else "",
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
			modifier = Modifier.weight(1f),
			text = stringResource(id = R.string.arrow_right),
			style = TextStyle(
				color = currentAccent,
				fontSize = 32.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
		)
		//endregion
	}
}