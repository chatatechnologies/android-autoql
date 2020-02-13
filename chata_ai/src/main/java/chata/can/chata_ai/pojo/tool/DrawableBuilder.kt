package chata.can.chata_ai.pojo.tool

import android.graphics.drawable.GradientDrawable

object DrawableBuilder
{
	fun setGradientDrawable(
		colorBase: Int,
		iCornerRadius: Float = 0f,
		iWidthRadius: Int = 0,
		strokeColor: Int = 0): GradientDrawable
	{
		return GradientDrawable().apply {
			shape = GradientDrawable.RECTANGLE
			setColor(colorBase)
			if (iCornerRadius != 0f)
				cornerRadius = iCornerRadius
			if (iWidthRadius != 0 && strokeColor != 0)
				setStroke(iWidthRadius, strokeColor)
		}
	}

	fun setOvalDrawable(
		colorBase: Int,
		iWidthRadius: Int = 0,
		strokeColor: Int = 0): GradientDrawable
	{
		return GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(colorBase)
			if (iWidthRadius != 0 && strokeColor != 0)
				setStroke(iWidthRadius, strokeColor)
		}
	}
}