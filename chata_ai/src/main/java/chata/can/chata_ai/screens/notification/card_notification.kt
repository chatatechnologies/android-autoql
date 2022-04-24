package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.model.ItemNotification
import chata.can.chata_ai.compose.model.emptyItemNotification
import chata.can.chata_ai.extension.toDp

@Composable
fun CardNotification(
	accentColor: Color = Color.Blue,
	itemNotification: ItemNotification = emptyItemNotification()
) {
	var height by remember { mutableStateOf(0) }
	val currentContext = LocalContext.current

	Card(
		modifier = Modifier
			.padding(4.dp)
			.fillMaxWidth(),
		elevation = 4.dp,
		shape = MaterialTheme.shapes.medium
	) {
		Row {
			Divider(
				modifier = Modifier
					.background(accentColor)
					.height(height.dp)
					.width(4.dp)
			)
			Column(
				modifier = Modifier
					.padding(8.dp)
					.onGloballyPositioned { layoutCoordinates ->
						//16 is padding in column
						height = layoutCoordinates.size.height.toDp(currentContext) + 16
					}
			) {
				Text(
					text = itemNotification.title,
					style = TextStyle(fontSize = 18.sp, color = itemNotification.getColorTint()),
					modifier = Modifier.fillMaxWidth()
				)
				if (itemNotification.message.isNotEmpty()) {
					Text(
						text = itemNotification.message,
						style = TextStyle(fontSize = 16.sp, color = itemNotification.getTextColorPrimary()),
						modifier = Modifier.fillMaxWidth()
					)
				}
				Text(
					text = itemNotification.createdAtFormatted(),
					style = TextStyle(fontSize = 14.sp, color = itemNotification.getTextColorPrimary()),
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	}
}

@Preview
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