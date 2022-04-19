package chata.can.chata_ai.compose.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RequiredField() {
	Text(
		text = "* required",
		style = TextStyle(color = Color(0xFFF22735), fontSize = 12.sp),
		modifier = Modifier.padding(top = 4.dp, start = 16.dp)
	)
}