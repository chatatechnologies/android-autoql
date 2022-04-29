package chata.can.chata_ai_api.component.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import chata.can.chata_ai_api.TabApp

sealed class TabItemSealed(
	val index: Int,
	val icon: ImageVector,
	val title: String,
	val screenShowForTab: @Composable () -> Unit
) {
	//R.drawable.ic_dashboard
	object DataMessengerTab : TabItemSealed(0, Icons.Default.Lock, "Data Messenger", {
		TabApp()
	})

	object DashboardTab : TabItemSealed(1, Icons.Default.Notifications, "Dashboard", {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Content for dashboards")
		}
	})
}