package chata.can.chata_ai.pojo.color

import chata.can.chata_ai.R

object ThemeColor
{
	val lightColor = Color(
		R.color.chata_drawer_accent_color,
		R.color.chata_drawer_background_color,
		R.color.chata_drawer_border_color,
		R.color.chata_drawer_hover_color,
		R.color.chata_drawer_color_primary,
		R.color.chata_drawer_text_color_placeholder)
	val darkColor = Color(
		R.color.chata_drawer_accent_color_dark,
		R.color.chata_drawer_background_color_dark,
		R.color.chata_drawer_border_color_dark,
		R.color.chata_drawer_hover_color_dark,
		R.color.chata_drawer_color_primary_dark,
		R.color.chata_drawer_text_color_placeholder_dark)

	var currentColor: Color = lightColor
}