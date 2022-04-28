package chata.can.chata_ai.screens.dataMessenger

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import chata.can.chata_ai.R
import chata.can.chata_ai.compose.component.ScreenAutocomplete

@Composable
fun DataMessengerContent() {
	val placeholder = stringResource(id = R.string.type_queries_here)

	Scaffold {
		ScreenAutocomplete(placeholder = placeholder) {//textReturned ->
			DataMessengerList()
		}
	}
}