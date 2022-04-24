package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.model.ItemNotification
import chata.can.chata_ai.compose.model.emptyItemNotification
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.compose.ui.theme.Shapes

@Composable
fun CardNotification(
	accentColor: Color = Color.Blue,
	itemNotification: ItemNotification = emptyItemNotification()
) {
	Card(
		modifier = Modifier
			.padding(10.dp)
			.fillMaxWidth(),
		elevation = 10.dp,
		shape = Shapes.medium
	) {
		Row {
			Divider(
				modifier = Modifier
					.background(accentColor)
					.height(80.dp)
					.width(4.dp)
			)
			Column(
				modifier = Modifier
					.padding(8.dp)
			) {
				Text(
					text = itemNotification.title,
					style = TextStyle(fontSize = 18.sp),
					modifier = Modifier.fillMaxWidth()
				)
				if (itemNotification.message.isNotEmpty()) {
					Text(
						text = itemNotification.message,
						style = TextStyle(fontSize = 16.sp),
						modifier = Modifier.fillMaxWidth()
					)
				}
				Text(
					text = itemNotification.createdAtFormatted(),
					style = TextStyle(fontSize = 14.sp),
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	}
}

@Preview()
@Composable
fun CardNotificationPreview() {
	Scaffold {
		LazyColumn {
			item {
				CardNotification()
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun SimpleCard() {
	val paddingModifier = Modifier.padding(10.dp)
	Card(elevation = 10.dp, modifier = paddingModifier.fillMaxWidth()) {
		Text(text = "Simple Card with elevation", modifier = paddingModifier)
	}
}