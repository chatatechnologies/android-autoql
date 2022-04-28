package chata.can.chata_ai.screens.dataMessenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import chata.can.chata_ai.pojo.color.ThemeColor

@Composable
fun DataMessengerList(modifier: Modifier = Modifier) {
	val backgroundColor = ThemeColor.currentColor.drawerColorSecondary()

	Column(
		modifier = modifier
			.background(backgroundColor)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Text(text = "Content for DataMessenger", modifier = Modifier)
	}
}