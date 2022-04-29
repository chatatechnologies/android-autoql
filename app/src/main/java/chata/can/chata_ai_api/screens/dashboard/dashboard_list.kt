package chata.can.chata_ai_api.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chata.can.chata_ai_api.R


@Composable
fun DashboardList() {
	LazyColumn(
		modifier = Modifier.fillMaxSize()
	) {
		items((0..20).toList()) { index ->
			CardDashboard(titleDashboard = "Item => $index")
		}
	}
}

@Composable
fun CardDashboard(titleDashboard: String = "") {
	Card(
		modifier = Modifier
			.padding(4.dp)
			.fillMaxWidth(),
		elevation = 4.dp,
		shape = MaterialTheme.shapes.medium
	) {
		Column {
			Text(text = "Title dashboard: $titleDashboard")
			Divider(
				modifier = Modifier
					.background(Color.LightGray)
					.height(1.dp)
					.width(100.dp)
					.padding(bottom = 5.dp, top = 5.dp)
			)
			Text(
				text = stringResource(id = R.string.execute_run_dashboard),
				style = TextStyle(textAlign = TextAlign.Center),
				modifier = Modifier
					.height(180.dp)
					.fillMaxWidth()
			)

			Divider(
				modifier = Modifier
					.fillMaxWidth()
					.height(1.dp)
					.padding(end = 4.dp, start = 4.dp)
			)

			Text(
				text = stringResource(id = R.string.execute_run_dashboard),
				style = TextStyle(textAlign = TextAlign.Center),
				modifier = Modifier
					.height(180.dp)
					.fillMaxWidth()
			)
		}
	}
}