package chata.can.chata_ai

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object Constant
{
	val nullParent = null
}

@Composable
fun Int.dp() = with(LocalDensity.current) { Dp(this@dp.toFloat()) }