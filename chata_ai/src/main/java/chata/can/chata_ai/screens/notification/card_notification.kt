package chata.can.chata_ai.screens.notification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
	var bottomVisible by remember { mutableStateOf(false) }
	val currentContext = LocalContext.current

	Card(
		modifier = Modifier
			.padding(4.dp)
			.fillMaxWidth()
			.clickable { bottomVisible = !bottomVisible },
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
					.animateContentSize(
						animationSpec = spring(
							dampingRatio = Spring.DampingRatioMediumBouncy,
							stiffness = Spring.StiffnessLow
						)
					)
					.onGloballyPositioned { layoutCoordinates ->
						//16 is padding in column
						height = layoutCoordinates.size.height.toDp(currentContext) + 16
					}
			) {
				//region ivTop
				Column {
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
				//endregion
				AnimatedVisibility(
					visible = bottomVisible,
					enter = fadeIn(animationSpec = tween(1000)),
					exit = fadeOut(animationSpec = tween(1000))
				) {
					//region rlBottom
					Column {
						Spacer(
							modifier = Modifier
								.fillMaxWidth()
								.height(0.2.dp)
								.padding(top = 8.dp, bottom = 8.dp)
						)
						Text(
							text = itemNotification.data_return_query,
							modifier = Modifier.fillMaxWidth(),
							style = TextStyle(
								fontWeight = FontWeight.Bold,
								fontSize = 16.sp,
								textAlign = TextAlign.Center
							)
						)
						Spacer(
							modifier = Modifier
								.fillMaxWidth()
								.height(0.2.dp)
								.padding(start = 24.dp, top = 4.dp, end = 24.dp)
						)
						Text(
							text = "",
							style = TextStyle(fontSize = 22.sp, textAlign = TextAlign.Center),
							modifier = Modifier
								.fillMaxWidth()
								.height(200.dp)
								.padding(start = 16.dp, end = 16.dp)
						)
//					WebView
					}
					//endregion
				}
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