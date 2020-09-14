package chata.can.chata_ai.extension

import android.animation.ObjectAnimator
import android.view.View
import android.widget.EditText
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

fun EditText.setOnTextChanged(listener: (String) -> Unit)
{
	addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				listener(string)
			}
		}
	)
}

fun View.backgroundGrayWhite(iCornerRadius: Float = 18f, iWidthRadius: Int = 1)
{
	context.run {
		val white = context.getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
		val gray = context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
		background = DrawableBuilder.setGradientDrawable(white,iCornerRadius,iWidthRadius, gray)
	}
}

fun View.setAnimator(yValue: Float, property: String)
{
	ObjectAnimator.ofFloat(this, property, yValue).run {
		duration = 500
		start()
	}
}