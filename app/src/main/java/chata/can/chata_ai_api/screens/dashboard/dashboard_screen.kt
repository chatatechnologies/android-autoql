package chata.can.chata_ai_api.screens.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

@Composable
fun DashboardContent() {
	Column {
		CurrentDashboard()
		ExecuteDashboard()
		DashboardList()
	}
}

@Composable
fun CurrentDashboard() {
	Text(
		text = "Title Dashboard",
		style = TextStyle(textAlign = TextAlign.Center),
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 8.dp, vertical = 4.dp)
			.border(width = 1.dp, Color.LightGray, shape = RoundedCornerShape(15))
			.padding(8.dp)
	)
}

@Composable
fun ExecuteDashboard() {
	Text(
		text = "Execute",
		style = TextStyle(textAlign = TextAlign.Center),
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 8.dp, vertical = 4.dp)
			.border(width = 1.dp, Color.LightGray, shape = RoundedCornerShape(15))
			.padding(8.dp)
	)
}

@Preview(showBackground = true)
@Composable
fun DashboardContentPreview() {
	ApiChataTheme {
		Scaffold {
			DashboardContent()
		}
	}
}