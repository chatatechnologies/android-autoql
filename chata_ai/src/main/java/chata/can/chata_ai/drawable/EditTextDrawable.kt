package chata.can.chata_ai.drawable

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

object EditTextDrawable
{
	private fun getAccentColor(context: Context) =
		ContextCompat.getColor(context, ThemeColor.currentColor.drawerAccentColor)

	fun getCircleDrawable(context: Context): GradientDrawable
	{
		return GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(getAccentColor(context))
		}
	}

	private fun getBackgroundColor(context: Context) =
		ContextCompat.getColor(context, ThemeColor.currentColor.drawerBackgroundColor)
	private fun getColorPrimary(context: Context) =
		ContextCompat.getColor(context, ThemeColor.currentColor.drawerColorPrimary)

	fun getQueryDrawable(context: Context): GradientDrawable
	{
		return DrawableBuilder.setGradientDrawable(
			getBackgroundColor(context),64f,1, getColorPrimary(context))
	}
}