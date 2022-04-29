@file:OptIn(ExperimentalPagerApi::class)

package chata.can.chata_ai_api.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.component.tabs.TabItemSealed
import chata.can.chata_ai_api.component.tabs.tabInitialList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MainManagerPager() {
	ApiChataTheme {
		val pagerState = rememberPagerState()
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			content = {
				TabManagePage(tabItems = tabInitialList, pagerState = pagerState)
			},
			topBar = {
				val coroutineScope = rememberCoroutineScope()

				IconWithTextManageTabLayout(
					tabInitialList,
					selectedIndex = pagerState.currentPage,
					onPageSelected = { tabItem: TabItemSealed ->
						coroutineScope.launch {
							pagerState.animateScrollToPage(tabItem.index)
						}
					}
				)
			}
		)
	}
}

@Composable
fun TabManagePage(tabItems: List<TabItemSealed>, pagerState: PagerState) {
	HorizontalPager(count = tabItems.size, state = pagerState) { index ->
		tabItems[index].screenShowForTab()
	}
}

@Composable
fun IconWithTextManageTabLayout(
	tabs: List<TabItemSealed>,
	selectedIndex: Int,
	onPageSelected: ((tabItem: TabItemSealed) -> Unit)
) {
	val blueAccentColor = colorResource(id = R.color.colorButton)
	TabRow(
		selectedTabIndex = selectedIndex,
		backgroundColor = Color.White,
		contentColor = blueAccentColor
	) {
		tabs.forEachIndexed { index, tabItemSealed ->
			Tab(selected = index == selectedIndex, onClick = {
				onPageSelected(tabItemSealed)
			}, text = {
				Text(text = tabItemSealed.title)
			}, icon = {
				Image(
					painter = painterResource(id = tabItemSealed.imageResource),
					contentDescription = "",
					colorFilter = ColorFilter.tint(
						colorResource(
							id = tabItemSealed.colorImageResource
						)
					)
				)
//				Icon(tabItemSealed.icon, contentDescription = "")
			})
		}
	}
}