package chata.can.chata_ai.compose.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun ReverseDirection(content: @Composable () -> Unit) {
	CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
		content()
	}
}