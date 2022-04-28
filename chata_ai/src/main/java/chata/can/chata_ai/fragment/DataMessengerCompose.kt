package chata.can.chata_ai.fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import chata.can.chata_ai.compose.component.ScreenAutocomplete
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ApiChataTheme {
				DataMessengerContent()
			}
		}
	}
}

@Composable
fun DataMessengerContent() {
	Scaffold {
		ScreenAutocomplete {//textReturned ->
			DataMessengerList()
		}
	}
}

//region rvChat
@Composable
fun DataMessengerList(modifier: Modifier = Modifier) {
	Column(
		modifier = modifier.background(Color.Gray).fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Text(text = "Content for DataMessenger", modifier = Modifier)
	}
}
//endregion

@Preview(showBackground = true)
@Composable
fun DataMessengerBottomPreview() {
	ApiChataTheme {
		DataMessengerContent()
	}
}