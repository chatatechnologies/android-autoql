package chata.can.chata_ai_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PagerComposableActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			TabApp()
		}
	}
}

@Preview
@Composable
fun TabApp() {
	var selectedIndex by remember { mutableStateOf(0) }
	val titles = listOf("Data Messenger", "Dashboard")

	Column(Modifier.fillMaxSize()) {
		TabRow(
			selectedTabIndex = selectedIndex,
			backgroundColor = Color(0xFF2E5894),
			contentColor = Color(0xFFBCD4E6)
		) {
			titles.forEachIndexed { index, title ->
				Tab(selected = selectedIndex == index, onClick = { selectedIndex = index }) {
					Text(text = title, Modifier.padding(12.dp))
				}
			}
		}
		Box(
			Modifier
				.fillMaxSize()
				.wrapContentSize(Alignment.Center)) {
			Text(text = titles[selectedIndex], style = TextStyle(fontSize = 30.sp))
		}
	}
}