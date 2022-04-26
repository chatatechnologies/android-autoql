@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package chata.can.chata_ai.compose.widget

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R

@Composable
fun GifImage(
	modifier: Modifier = Modifier,
	imageID: Int
) {
	val image = animatedVectorResource(id = imageID)
	var atEnd by remember { mutableStateOf(false) }
	Image(
		painter = image.painterFor(atEnd = atEnd),
		contentDescription = "",
		modifier = Modifier
			.size(64.dp)
			.clickable { atEnd = !atEnd })
}

@Preview(showBackground = true)
@Composable
fun GifImagePreview() {
	GifImage(imageID = R.drawable.gif_balls)
}