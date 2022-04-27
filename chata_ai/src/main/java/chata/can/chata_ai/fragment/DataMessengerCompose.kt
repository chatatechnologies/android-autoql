package chata.can.chata_ai.fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.compose.widget.CustomTextField
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
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(Color.Blue)
		) {
			DataMessengerList(modifier = Modifier.weight(1f))
			DataMessengerBottom()
		}
	}
}

//region rvChat
@Composable
fun DataMessengerList(modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Color.LightGray)) {
		Text(text = "Content", modifier = Modifier.fillMaxSize())
	}
}
//endregion

@Composable
fun DataMessengerBottom(modifier: Modifier = Modifier) {
	var query by remember { mutableStateOf("") }
	Box {
		Text(text = "1")
	}
	Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
		Image(
			modifier = Modifier
				.size(48.dp)
				.padding(10.dp)
				.clickable {
					//
				},
			painter = painterResource(id = R.drawable.ic_microphone),
			contentDescription = "Microphone for speaking"
		)
		CustomTextField(
			modifier = Modifier.fillMaxWidth(),
			text = query,
			placeholder = stringResource(id = R.string.ask_me_anything),
			onValueChanged = {
				query = it
			})
	}
}

@Preview(showBackground = true)
@Composable
fun DataMessengerBottomPreview() {
	ApiChataTheme {
		DataMessengerContent()
	}
}