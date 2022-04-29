@file:OptIn(ExperimentalPagerApi::class)

package chata.can.chata_ai_api.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai_api.component.tabs.TabItemSealed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun MainManagerPager() {
	ApiChataTheme(darkTheme = true) {
		val pagerState = rememberPagerState()
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			content = {

			},
			topBar = {

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