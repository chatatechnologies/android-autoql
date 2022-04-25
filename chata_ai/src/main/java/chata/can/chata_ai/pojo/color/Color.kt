package chata.can.chata_ai.pojo.color

import android.content.Context
import androidx.compose.ui.graphics.Color
import chata.can.chata_ai.extension.getParsedColor

class Color(
	private val drawerAccentColor: Int,
	val drawerBackgroundColor: Int,
	val drawerColorSecondary: Int,
	val drawerBorderColor: Int,
	private val drawerHoverColor: Int,
	val drawerTextColorPrimary: Int,
	private val highlightColor: Int,
	private val dangerColor: Int
)
{
	var pDrawerAccentColor = 0
	fun drawerAccentComposeColor() = Color(pDrawerAccentColor)
	var pDrawerBackgroundColor = 0
	fun drawerBackgroundColor() = Color(pDrawerBackgroundColor)
	var pDrawerColorSecondary = 0
	fun drawerColorSecondary() = Color(pDrawerColorSecondary)
	var pDrawerBorderColor = 0
	fun drawerBorderColor() = Color(pDrawerBorderColor)
	private var pDrawerHoverColor = 0
	var pDrawerTextColorPrimary = 0
	fun drawerTextColorPrimary() = Color(pDrawerTextColorPrimary)
	var pHighlightColor = 0
	fun highlightColor() = Color(pHighlightColor)
	var pDangerColor = 0
	fun dangerColor() = Color(pDangerColor)

	fun initConfig(context: Context)
	{
		context.run {
			pDrawerAccentColor = getParsedColor(drawerAccentColor)
			pDrawerBackgroundColor = getParsedColor(drawerBackgroundColor)
			pDrawerColorSecondary = getParsedColor(drawerColorSecondary)
			pDrawerBorderColor = getParsedColor(drawerBorderColor)
			pDrawerHoverColor = getParsedColor(drawerHoverColor)
			pDrawerTextColorPrimary = getParsedColor(drawerTextColorPrimary)
			pHighlightColor = getParsedColor(highlightColor)
			pDangerColor = getParsedColor(dangerColor)
		}
	}
}