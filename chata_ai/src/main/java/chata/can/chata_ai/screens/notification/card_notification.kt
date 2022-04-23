package chata.can.chata_ai.screens.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardNotification() {
	Column(
		modifier = Modifier
			.padding(10.dp)
			.fillMaxWidth()
	) {
		Text(text = "Title", style = TextStyle(fontSize = 18.sp), modifier = Modifier.fillMaxWidth())
		Text(text = "Body", style = TextStyle(fontSize = 16.sp), modifier = Modifier.fillMaxWidth())
		Text(text = "Date", style = TextStyle(fontSize = 14.sp), modifier = Modifier.fillMaxWidth())
	}
}