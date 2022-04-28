package chata.can.chata_ai.fragment.dataMessenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme
import chata.can.chata_ai.screens.dataMessenger.DataMessengerContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataMessengerCompose : Fragment() {
	companion object {
		const val nameFragment = "Data Messenger"
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return ComposeView(requireContext()).apply {
			setContent {
				ApiChataTheme {
					DataMessengerContent()
				}
			}
		}
	}
}