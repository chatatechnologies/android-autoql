package chata.can.chata_ai.extension

import android.animation.ObjectAnimator
import android.graphics.PorterDuff
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
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

fun ImageView.setColorFilter()
{
	setColorFilter(
		ContextCompat.getColor(context, ThemeColor.currentColor.drawerColorPrimary),
		PorterDuff.Mode.SRC_ATOP)
}

fun View.backgroundGrayWhite(iCornerRadius: Float = 18f, iWidthRadius: Int = 1)
{
	val white = ContextCompat.getColor(
		context,
		ThemeColor.currentColor.drawerBackgroundColor)
	val gray = ContextCompat.getColor(
		context,
		ThemeColor.currentColor.drawerColorPrimary)
	background = DrawableBuilder.setGradientDrawable(white,iCornerRadius,iWidthRadius, gray)
}

fun View.setAnimator(yValue: Float, property: String)
{
	ObjectAnimator.ofFloat(this, property, yValue).run {
		duration = 500
		start()
	}
}