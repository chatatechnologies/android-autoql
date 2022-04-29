package chata.can.chata_ai_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import chata.can.chata_ai_api.screens.InputConfigScreen
import chata.can.chata_ai_api.screens.InputConfigViewModel
import chata.can.chata_ai_api.screens.MainManagerPager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagerComposableActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MainManagerPager()
		}
	}
}

@Composable
fun TabApp(viewModel: InputConfigViewModel = hiltViewModel()) {
	val blueAccentColor = colorResource(id = R.color.colorButton)
	val blackColor = Color.Black
	var selectedIndex by remember { mutableStateOf(0) }
	val titles = listOf("Data Messenger", "Dashboard")

	val getColor: (Int) -> Color = remember(selectedIndex) {
		{ index ->
			if (selectedIndex == index) blueAccentColor else blackColor
		}
	}

	Column(Modifier.fillMaxSize()) {

//		TabRow(
//			selectedTabIndex = selectedIndex,
//			backgroundColor = Color.White,
//			contentColor = blueAccentColor
//		) {
//			titles.forEachIndexed { index, title ->
//				Tab(selected = selectedIndex == index, onClick = { selectedIndex = index }) {
//					Row(verticalAlignment = Alignment.CenterVertically) {
//						Image(
//							painter = painterResource(
//								id = if (index == 0) R.drawable.ic_tab_dashboard else R.drawable.ic_tab_data
//							),
//							contentDescription = "Data Messenger Image",
//							colorFilter = ColorFilter.tint(
//								color = getColor(index)
//							)
//						)
//						Text(text = title, Modifier.padding(12.dp), style = TextStyle(color = getColor(index)))
//					}
//				}
//			}
//		}
		InputConfigScreen(viewModel = viewModel)
	}
}