package chata.can.chata_ai.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularText(
	text: String = "999",
	textSize: Int = 20,
	backgroundColor: Color = Color.Black,
	textColor: Color = Color.White
) {
	Box(contentAlignment = Alignment.Center,
		modifier = Modifier
			.background(backgroundColor, shape = CircleShape)
			.layout { measurable, constraints ->
				// Measure the composable
				val placeable = measurable.measure(constraints)

				//get the current max dimension to assign width=height
				val currentHeight = placeable.height
				var heightCircle = currentHeight
				if (placeable.width > heightCircle)
					heightCircle = placeable.width

				//assign the dimension and the center position
				layout(heightCircle, heightCircle) {
					// Where the composable gets placed
					placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
				}
			}) {

		Text(
			text = text,
			color = textColor,
			style = TextStyle(
				textAlign = TextAlign.Center,
				fontSize = textSize.sp,
				fontWeight = FontWeight.Bold
			),
			modifier = Modifier
				.padding(4.dp)
				.defaultMinSize(24.dp) //Use a min size for short text.
		)
	}
}

@Preview
@Composable
fun CircularTextPreview() {
	CircularText()
}