@file:OptIn(ExperimentalPagerApi::class)

package chata.can.chata_ai_api.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.component.tabs.TabItemSealed
import chata.can.chata_ai_api.component.tabs.tabInitialList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

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
				//region tabRow
				val blueAccentColor = colorResource(id = R.color.colorButton)
				val blackColor = Color.Black
				var selectedIndex by remember { mutableStateOf(0) }
				val getTabColor: (Int) -> Color = remember(selectedIndex) {
					{ index ->
						if (selectedIndex == index) blueAccentColor else blackColor
					}
				}

				TabRow(
					selectedTabIndex = selectedIndex,
					backgroundColor = Color.White,
					contentColor = blueAccentColor
				) {
					tabInitialList.forEachIndexed { index, tabItemSealed ->
						Tab(selected = selectedIndex == index, onClick = { selectedIndex = index }) {
							Row(verticalAlignment = Alignment.CenterVertically) {
								Image(
									painter = painterResource(id = tabItemSealed.imageResource),
									colorFilter = ColorFilter.tint(getTabColor(index)),
									contentDescription = ""
								)
								Text(
									text = tabItemSealed.title,
									modifier = Modifier.padding(12.dp),
									style = TextStyle(color = getTabColor(index))
								)
							}
						}
					}
				}
				//endregion
			}
//			topBar = {
//				val coroutineScope = rememberCoroutineScope()
//
//				IconWithTextManageTabLayout(
//					tabInitialList,
//					selectedIndex = pagerState.currentPage,
//					onPageSelected = { tabItem: TabItemSealed ->
//						coroutineScope.launch {
//							pagerState.animateScrollToPage(tabItem.index)
//						}
//					}
//				)
//			}
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