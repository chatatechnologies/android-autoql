package chata.can.chata_ai.pojo.color

import android.content.Context
import chata.can.chata_ai.R

object ThemeColor
{
	val lightColor = Color(
		R.color.accent_color_1,
		R.color.background_color_primary_1,
		R.color.background_color_secondary_1,
		R.color.background_color_tertiary_1,
		R.color.border_color_1,
		R.color.hover_color_1,
		R.color.text_color_primary_1,
		R.color.text_color_placeholder_1,
		R.color.highlight_color_1,
		R.color.danger_color_1)
	val darkColor = Color(
		R.color.accent_color_2,
		R.color.background_color_primary_2,
		R.color.background_color_secondary_2,
		R.color.background_color_tertiary_2,
		R.color.border_color_2,
		R.color.hover_color_2,
		R.color.text_color_primary_2,
		R.color.text_color_placeholder_2,
		R.color.highlight_color_2,
		R.color.danger_color_2)

	var currentColor: Color = lightColor//darkColor
	set(value) {
		field = value
		for ((_, method) in aColorMethods)
		{
			method()
		}
	}

	fun parseColor(context: Context)
	{
		lightColor.initConfig(context)
		darkColor.initConfig(context)
	}

	val aColorMethods = LinkedHashMap<String, () -> Unit>()
}