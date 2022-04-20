package chata.can.chata_ai.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.util.LambdaAction

@Composable
fun CustomButton(text: String, action: LambdaAction = {}) {
	val accentColor = colorResource(id = R.color.blue_chata_circle)
	val alphaAccent = Color(accentColor.red, accentColor.green, accentColor.blue, 0.3f)
	Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
		Button(
			border = BorderStroke(1.dp, accentColor),
			elevation = null,
			onClick = { action() },
			shape = MaterialTheme.shapes.small,
			colors = ButtonDefaults.buttonColors(
				backgroundColor = alphaAccent,
				contentColor = accentColor
			),
			modifier = Modifier.fillMaxWidth()
		) {
			Text(text = text)
		}
	}
}