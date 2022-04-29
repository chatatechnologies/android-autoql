package chata.can.chata_ai_api.component.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.TabApp

sealed class TabItemSealed(
	val index: Int,
	val imageResource: Int,
	val colorImageResource: Int,
	val title: String,
	val screenShowForTab: @Composable () -> Unit
) {
	object DataMessengerTab : TabItemSealed(
		index = 0,
		imageResource = R.drawable.ic_tab_data,
		colorImageResource = R.color.colorButton,
		title = "Data Messenger",
		screenShowForTab = {
			TabApp()
		})

	object DashboardTab : TabItemSealed(
		index = 1,
		imageResource = R.drawable.ic_tab_dashboard,
		colorImageResource = R.color.colorButton,
		title = "Dashboard",
		screenShowForTab = {
			Column(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(text = "Content for dashboards")
			}
		})
}