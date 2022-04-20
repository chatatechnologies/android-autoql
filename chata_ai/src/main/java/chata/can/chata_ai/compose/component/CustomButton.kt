package chata.can.chata_ai.compose.component

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
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.util.LambdaAction

@Composable
fun CustomButton(text: String, action: LambdaAction = {}) {
	Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
		Button(
			onClick = { action() },
			shape = MaterialTheme.shapes.small,
			colors = ButtonDefaults.buttonColors(
				backgroundColor = colorResource(id = R.color.blue_chata_circle),
				contentColor = Color.White
			),
			modifier = Modifier.fillMaxWidth()
		) {
			Text(text = text)
		}
	}
}