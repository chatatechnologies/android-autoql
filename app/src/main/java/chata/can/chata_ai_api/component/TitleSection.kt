package chata.can.chata_ai_api.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleSection(text: String) {
	Text(
		text = text,
		style = TextStyle(
			fontWeight = FontWeight.Bold,
			fontSize = 16.sp,
			textAlign = TextAlign.Center
		),
		modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
	)
}