package chata.can.chata_ai_api.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import chata.can.chata_ai.compose.component.CustomTextField
import chata.can.chata_ai.compose.component.RequiredField

@Composable
fun ColumnList() {
	LazyColumn(
		modifier = Modifier
			.background(Color(0xFFEDEAE0))
			.fillMaxSize()
	) {
		items(20) { index ->
			CustomTextField(placeholder = "counter $index", value = "") {}
		}
		items(5) {
			RequiredField()
		}
	}
}

@Preview
@Composable
fun ComposablePreview() {
	ColumnList()
}