package chata.can.chata_ai_api.test

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeText() {
	Text(
		text = "Welcome to chat with Jetpack Compose",
		style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
	)
}