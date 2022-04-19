package chata.can.chata_ai.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.compose.util.LambdaAction

@Composable
fun CustomButton(text: String, action: LambdaAction = {}) {
	Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
		Button(
			onClick = { action() },
			shape = MaterialTheme.shapes.large,
			colors = ButtonDefaults.buttonColors(
				backgroundColor = colorResource(id = R.color.blue_chata_circle),
				contentColor = Color.White
			),
			elevation = ButtonDefaults.elevation(
				defaultElevation = 6.dp,
				pressedElevation = 8.dp,
				disabledElevation = 0.dp
			)
		) {
			Text(text = text)
		}
	}
}

@Preview
@Composable
fun CustomButtonPreview() {
	ApiChataTheme {
		Box(
			modifier = Modifier.padding(16.dp)
		) {
			CustomButton("Authenticate")
		}
	}
}