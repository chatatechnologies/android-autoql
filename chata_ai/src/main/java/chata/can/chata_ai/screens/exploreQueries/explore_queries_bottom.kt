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
fun PreviousPage(modifier: Modifier = Modifier, color: Color) {
	//region tvPrevious
	Text(
		modifier = modifier,
		text = stringResource(id = R.string.arrow_left),
		style = TextStyle(
			color = color,//previousTextColor
			fontSize = 32.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center
		)
	)
	//endregion
}

@Composable
fun FirstPage(modifier: Modifier = Modifier, color: Color) {
	//region tvFirstPage
	Text(
		modifier = modifier
			.height(30.dp),
		text = stringResource(id = R.string.one),
		style = TextStyle(
			color = color,
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center
		)
	)
	//endregion
}

@Composable
fun CenterPage(modifier: Modifier = Modifier, text: String, color: Color) {
	//region tvCenterPage
	Text(
		modifier = modifier
			.height(30.dp),
		text = text,
		style = TextStyle(
			color = color,//textColorPrimary
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center
		)
	)
	//endregion
}

@Composable
fun LastPage(modifier: Modifier = Modifier, text: String, color: Color) {
	//region tvLastPage
	Text(
		modifier = modifier
			.height(30.dp),
		text = text,
		style = TextStyle(
			color = color,
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center
		)
	)
	//endregion
}

@Composable
fun NextPage(modifier: Modifier = Modifier, color: Color) {
	//region tvNext
	Text(
		modifier = modifier,
		text = stringResource(id = R.string.arrow_right),
		style = TextStyle(
			color = color,//nextTextColor
			fontSize = 32.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center
		)
	)
	//endregion
}

@Composable
fun ExploreQueriesBottom(
	modifier: Modifier = Modifier,
	relatedQueriesPagination: RelatedQueriesPagination
) {
//	var selected: ComposableFunction = { FirstPage() }

	val currentAccent = currentAccentColor()
	val textColorPrimary = ThemeColor.currentColor.drawerTextColorPrimary()

	val currentPage = relatedQueriesPagination.current_page
	val totalPages = relatedQueriesPagination.total_pages
//	val pageSize = relatedQueriesPagination.page_size

	//region manage currentPage
	val previousTextColor: Color
	val nextTextColor: Color
	val centerPageText: String

	when (currentPage) {
		1, totalPages -> {
			if (currentPage == 1) {
				previousTextColor = currentAccentDisableCompose()
				nextTextColor = currentAccent
			} else {
				previousTextColor = currentAccent
				nextTextColor = currentAccentDisableCompose()
			}
			centerPageText = "..."
		}
		else -> {
			previousTextColor = currentAccent
			nextTextColor = currentAccent
			centerPageText = "$currentPage"
		}
	}
	//endregion

	Row(
		modifier = modifier
			.height(56.dp)
			.fillMaxWidth()

			.background(Color.LightGray),
		verticalAlignment = Alignment.CenterVertically
	) {
		PreviousPage(modifier = Modifier.weight(1f), color = previousTextColor)
		FirstPage(modifier = Modifier.weight(1f), color = textColorPrimary)
		CenterPage(modifier = modifier.weight(1f), text = centerPageText, color = textColorPrimary)
		LastPage(
			modifier = modifier.weight(1f),
			text = if (totalPages >= currentPage) "$totalPages" else "",
			color = textColorPrimary
		)
		NextPage(modifier = modifier.weight(1f), color = nextTextColor)
	}
}