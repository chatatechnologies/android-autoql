package chata.can.chata_ai.compose.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import chata.can.chata_ai.compose.component.AutoCompleteTextView
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.compose.ui.theme.Purple500

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ApiChataTheme {
				Surface(color = MaterialTheme.colors.background) {
					Scaffold(
						topBar = { TopBar() },
						backgroundColor = Purple500
					) {
						AutoCompleteTextView()
					}
				}
			}
		}
	}
}

@Composable
fun TopBar() {
	TopAppBar(
		title = {
			Text(
				text = "Country List",
				fontSize = 20.sp,
				modifier = Modifier.fillMaxWidth(),
				textAlign = TextAlign.Center
			)
		},
		backgroundColor = Purple500,
		contentColor = Color.White
	)
}