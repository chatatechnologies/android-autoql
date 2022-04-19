package chata.can.chata_ai_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai_api.ui.theme.ApiChataTheme

class PagerComposableActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ApiChataTheme {
				TabApp()
			}
		}
	}
}

@Preview
@Composable
fun TabApp() {
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
		TabRow(
			selectedTabIndex = selectedIndex,
			backgroundColor = Color.White,
			contentColor = blueAccentColor
		) {
			titles.forEachIndexed { index, title ->
				Tab(selected = selectedIndex == index, onClick = { selectedIndex = index }) {
					Row(verticalAlignment = Alignment.CenterVertically) {
						Image(
							painter = painterResource(
								id = if (index == 0) R.drawable.ic_tab_dashboard else R.drawable.ic_tab_data
							),
							contentDescription = "Data Messenger Image",
							colorFilter = ColorFilter.tint(
								color = getColor(index)
							)
						)
						Text(text = title, Modifier.padding(12.dp), style = TextStyle(color = getColor(index)))
					}
				}
			}
		}
		Box(
			Modifier
				.fillMaxSize()
				.wrapContentSize(Alignment.Center)
		) {
			Text(text = titles[selectedIndex], style = TextStyle(fontSize = 30.sp))
		}
	}
}