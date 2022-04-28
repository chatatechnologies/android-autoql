package chata.can.chata_ai.compose.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import chata.can.chata_ai.compose.component.ScreenAutocomplete
import chata.can.chata_ai.compose.ui.theme.ApiChataTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ApiChataTheme {
				Scaffold {
					ScreenAutocomplete {
						//region content for interaction with autocomplete
						val list = (0..40).toList()
						LazyColumn(modifier = Modifier.fillMaxWidth()) {
							items(list) { num ->
								Text(text = "$num, searched =>")
							}
						}
						//endregion
					}
				}
			}
		}
	}
}