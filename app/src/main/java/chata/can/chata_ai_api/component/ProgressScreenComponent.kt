package chata.can.chata_ai_api.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun ProgressScreenComponent(isShowProgress: Boolean = false, content: @Composable () -> Unit) {
	val blueColor = colorResource(id = R.color.blue_chata_circle)

	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()
	) {
		content()
		//region progress center
		if (isShowProgress) {
			Column(
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier
					.background(Color(0xCC383838))
					.fillMaxSize()
			) {
				Surface(color = Color.White) {
					CircularProgressIndicator(
						color = blueColor,
						modifier = Modifier
							.padding(8.dp)
							.size(56.dp),
						strokeWidth = 4.dp
					)
				}
			}
		}
		//endregion
	}
}

@Preview(showBackground = true)
@Composable
fun ProgressScreenComponentPreview() {
	ApiChataTheme {
		ProgressScreenComponent {

		}
	}
}