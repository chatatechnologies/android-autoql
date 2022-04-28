package chata.can.chata_ai.fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.screens.dataMessenger.DataMessengerContent
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