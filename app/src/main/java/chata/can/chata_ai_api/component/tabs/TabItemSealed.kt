package chata.can.chata_ai_api.component.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import chata.can.chata_ai_api.TabApp

sealed class TabItemSealed(
	val index: Int,
	val icon: ImageVector,
	val title: String,
	val screenShowForTab: @Composable () -> Unit
) {
	object DataMessengerTab: TabItemSealed(0, Icons.Default.Lock, "Data Messenger", {
		TabApp()
	})

	object DashboardTab: TabItemSealed(1, Icons.Default.Notifications, "Dashboard", {
		TabApp()
	})
}