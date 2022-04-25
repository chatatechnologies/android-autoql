package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.model.ItemNotification

@Composable
fun NotificationList(items: List<ItemNotification>) {
	val accentColor = colorResource(id = R.color.blue_chata_circle)
	Scaffold { paddingValues ->
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.White),
			contentPadding = paddingValues,
		) {
			items(items) { notification ->
				CardNotification(accentColor = accentColor, itemNotification = notification)
			}
		}
	}
}