package chata.can.chata_ai.extension

import android.animation.ObjectAnimator
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import chata.can.chata_ai.pojo.base.ItemSelectedListener
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

fun Spinner.setOnItemSelected(listener: (AdapterView<*>?, View?, Int, Long) -> Unit)
{
	onItemSelectedListener = object : ItemSelectedListener {
		override fun onSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
		{
			listener(parent, view, position, id)
		}
	}
}

fun View.backgroundGrayWhite(iCornerRadius: Float = 18f)
{
	context.run {
		background = DrawableBuilder.setGradientDrawable(
			ThemeColor.currentColor.pDrawerBackgroundColor,iCornerRadius)
	}
}

fun View.backgroundWhiteGray()
{
	context.run {
		ThemeColor.currentColor.run {
			background = DrawableBuilder.setGradientDrawable(
				pDrawerBackgroundColor,18f,1, pDrawerTextColorPrimary)
		}
	}
}

fun View.setAnimator(yValue: Float, property: String)
{
	ObjectAnimator.ofFloat(this, property, yValue).run {
		duration = 500
		start()
	}
}