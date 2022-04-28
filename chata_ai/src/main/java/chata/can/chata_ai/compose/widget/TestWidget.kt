package chata.can.chata_ai.compose.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
				Scaffold {
					Box {
						Column(modifier = Modifier.fillMaxSize()) {
							val list = (0..40).toList()
							LazyColumn(modifier = Modifier.fillMaxWidth()) {
								items(list) { num ->
									Text(text = "$num")
								}
							}
						}
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