package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.component.ColumnCenterComponent
import chata.can.chata_ai.compose.util.isTrue

@Composable
fun ContentNotification(viewModel: NotificationViewModel = hiltViewModel()) {
	val valueViewModel = viewModel.data.value
	val loading = valueViewModel.loading
	val dataNotification = valueViewModel.data?.data
	val itemNotifications = dataNotification?.items
	val isEmpty = itemNotifications?.isEmpty()

	if (loading.isTrue()) {
		ColumnCenterComponent {
			Text(
				text = stringResource(id = R.string.loading),
				style = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
				modifier = Modifier.fillMaxWidth()
			)
		}
	} else {
		if (isEmpty == true) {
			ColumnCenterComponent {
				Image(
					painter = painterResource(id = R.drawable.ic_notification),
					contentDescription = "Waiting notification",
					modifier = Modifier.size(180.dp)
				)
				Text(
					text = stringResource(id = R.string.empty_notification),
					style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
				)
				Text(
					text = stringResource(id = R.string.stay_tuned),
					style = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
					modifier = Modifier
						.padding(top = 4.dp)
						.fillMaxWidth()
				)
			}
		} else {
			NotificationList(itemNotifications ?: listOf(), viewModel)
		}
	}
}