package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.screens.NotificationViewModel
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun ContentNotification(viewModel: NotificationViewModel = hiltViewModel()) {
	val notificationResponse = viewModel.data.value

	Column(
		Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		if (notificationResponse.message.isNotEmpty()) {
			Text(text = "Notification count => ${notificationResponse.data.items.size}")
		}
		Image(
			painter = painterResource(id = R.drawable.ic_notification),
			contentDescription = "Waiting notification",
			modifier = Modifier.size(180.dp)
		)
		Text(
			text = stringResource(id = R.string.loading),
			style = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
			modifier = Modifier
				.fillMaxWidth()
				.padding(end = 16.dp, start = 16.dp)
		)
		Text(text = stringResource(id = R.string.try_again), modifier = Modifier.padding(36.dp))
		Text(
			text = stringResource(id = R.string.stay_tuned),
			style = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
			modifier = Modifier
				.padding(top = 4.dp)
				.fillMaxWidth()
		)
	}
}

@Composable
fun NotificationList() {
	val notificationList = listOf(1, 2, 3, 4)
	LazyColumn {
		items(notificationList) {
			CardNotification()
		}
	}
}

@Preview(showBackground = true)
@Composable
fun ContentNotificationPreview() {
	ApiChataTheme {
		ContentNotification()
	}
}

//@Preview(showBackground = true)
@Composable
fun NotificationListPreview() {
	ApiChataTheme {
		NotificationList()
	}
}