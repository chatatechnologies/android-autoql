package chata.can.chata_ai.pojo.color

import android.content.Context
import chata.can.chata_ai.extension.getParsedColor

class Color(
	private val drawerAccentColor: Int,
	val drawerBackgroundColor: Int,
	val drawerColorSecondary: Int,
	val backgroundColorTertiary: Int,
	val drawerBorderColor: Int,
	private val drawerHoverColor: Int,
	val drawerTextColorPrimary: Int,
	val drawerTextColorPlaceholder: Int,
	private val highlightColor: Int,
	private val dangerColor: Int
)
{
	var pDrawerAccentColor = 0
	var pDrawerBackgroundColor = 0
	var pDrawerColorSecondary = 0
	var pDrawerBorderColor = 0
	private var pDrawerHoverColor = 0
	var pDrawerTextColorPrimary = 0
	var pHighlightColor = 0
	var pDangerColor = 0

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