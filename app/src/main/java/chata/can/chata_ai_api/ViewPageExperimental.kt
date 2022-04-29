@file:OptIn(ExperimentalPagerApi::class)

package chata.can.chata_ai_api

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class ViewPagerActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
		setContent {
			TabLayoutDemo()
		}
	}
}

private val tabs = listOf(
	TabItem.Home,
	TabItem.Settings,
	TabItem.Contacts
)

sealed class TabItem(
	val index: Int,
	val icon: ImageVector,
	val title: String,
	val screenToLoad: @Composable () -> Unit
) {
	object Home : TabItem(0, Icons.Default.Home, "Home", {
		HomeScreenForTab()
	})

	object Contacts : TabItem(2, Icons.Default.Person, "Contacts", {
		ContactScreenForTab()
	})

	object Settings : TabItem(1, Icons.Default.Settings, "Settings", {
		SettingsScreenForTab()
	})
}

@Composable
fun HomeScreenForTab() {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "You are in Home Screen")
	}
}

@Composable
fun ContactScreenForTab() {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "You are in Contact Screen")
	}
}

@Composable
fun SettingsScreenForTab() {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "You are in Settings Screen")
	}
}

@Composable
fun TabLayoutDemo() {
	ApiChataTheme(darkTheme = true) {
		val pagerState = rememberPagerState()
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			content = {
				TabPage(pagerState = pagerState, tabItems = tabs)
			},
			topBar = {
				val coroutineScope = rememberCoroutineScope()
				Column(content = {
					TopAppBar(title = { Text(text = "Tab Layout Demo") })
				})
				//Replace here with TextTabLayout or ScrollableTabLayout or IconTabLayout
				//Tab only Text
//				TextTabLayout(
//					tabs = tabs,
//					selectedIndex = pagerState.currentPage,
//					onPageSelected = { tabItem: TabItem ->
//						coroutineScope.launch {
//							pagerState.animateScrollToPage(tabItem.index)
//						}
//					})
				//endregion
//				IconWithTextTabLayout(
//					tabs,
//					selectedIndex = pagerState.currentPage,
//					onPageSelected = { tabItem: TabItem ->
//						coroutineScope.launch {
//							pagerState.animateScrollToPage(tabItem.index)
//						}
//					}
//				)

				//region scrollable tab
//				ScrollableTabLayout(tab = tabs, selectedIndex = pagerState.currentPage, onPageSelected = { tabItem: TabItem ->
//					coroutineScope.launch {
//						pagerState.animateScrollToPage(tabItem.index)
//					}
//				})
				//endregion
				//region Only icons
				IconTabLayout(
					tab = tabs,
					selectedIndex = pagerState.currentPage, onPageSelected = { tabItem: TabItem ->
						coroutineScope.launch {
							pagerState.animateScrollToPage(tabItem.index)
						}
					})
				//endregion
			}
		)
	}
}

@Composable
fun TabPage(pagerState: PagerState, tabItems: List<TabItem>) {
	HorizontalPager(count = tabs.size, state = pagerState) { index ->
		tabItems[index].screenToLoad()
	}
}

@Composable
fun IconWithTextTabLayout(
	tabs: List<TabItem>,
	selectedIndex: Int,
	onPageSelected: ((tabItem: TabItem) -> Unit)
) {
	TabRow(selectedTabIndex = selectedIndex) {
		tabs.forEachIndexed { index, tabItem ->
			Tab(selected = index == selectedIndex, onClick = {
				onPageSelected(tabItem)
			}, text = {
				Text(text = tabItem.title)
			}, icon = {
				Icon(tabItem.icon, "")
			})
		}
	}
}

@Composable
fun TextTabLayout(
	tabs: List<TabItem>,
	selectedIndex: Int,
	onPageSelected: ((tabItem: TabItem) -> Unit)
) {
	TabRow(selectedTabIndex = selectedIndex) {
		tabs.forEachIndexed { index, tabItem ->
			Tab(selected = index == selectedIndex, onClick = {
				onPageSelected(tabItem)
			},
				text = {
					Text(text = tabItem.title)
				})
		}
	}
}

@Composable
fun ScrollableTabLayout(
	tab: List<TabItem>,
	selectedIndex: Int,
	onPageSelected: ((tabItem: TabItem) -> Unit)
) {
	ScrollableTabRow(selectedTabIndex = selectedIndex) {
		tabs.forEachIndexed { index, tabItem ->
			Tab(selected = index == selectedIndex, onClick = {
				onPageSelected(tabItem)
			}, text = {
				Text(text = tabItem.title)
			}, icon = {
				Icon(tabItem.icon, "")
			})
		}
	}
}

@Composable
fun IconTabLayout(
	tab: List<TabItem>,
	selectedIndex: Int,
	onPageSelected: ((tabItem: TabItem) -> Unit)
) {
	TabRow(selectedTabIndex = selectedIndex) {
		tabs.forEachIndexed { index, tabItem ->
			Tab(selected = index == selectedIndex, onClick = {
				onPageSelected(tabItem)
			}, icon = {
				Icon(tabItem.icon, "")
			})
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	TabLayoutDemo()
}