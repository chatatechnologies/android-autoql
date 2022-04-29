package chata.can.chata_ai_api.screens.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardList() {
	LazyColumn(
		modifier = Modifier.fillMaxSize()
	) {
		items((0..20).toList()) { index ->
			Text(text = "Item => $index")
		}
	}
}