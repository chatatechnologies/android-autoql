package chata.can.chata_ai.pojo.color

import chata.can.chata_ai.R

object ThemeColor
{
	val lightColor = Color(
		R.color.accent_color_1,
		R.color.background_color_primary_1,
		R.color.border_color_1,
		R.color.hover_color_1,
		R.color.text_color_primary_1,
		R.color.text_color_placeholder_1)
	val darkColor = Color(
		R.color.accent_color_2,
		R.color.background_color_primary_2,
		R.color.border_color_2,
		R.color.hover_color_2,
		R.color.text_color_primary_2,
		R.color.text_color_placeholder_2)

	var currentColor: Color = darkColor
	set(value) {
		field = value
		for ((_, method) in aMethod)
		{
			method()
		}
	}

	val aMethod = LinkedHashMap<String, () -> Unit>()
}