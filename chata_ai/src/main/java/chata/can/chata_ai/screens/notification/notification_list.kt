package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chata.can.chata_ai.compose.model.ItemNotification

@Composable
fun NotificationList(items: List<ItemNotification>) {
	LazyColumn(modifier = Modifier.fillMaxHeight()) {
		items(items) { notification ->
			CardNotification(itemNotification = notification)
		}
	}
}