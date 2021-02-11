package chata.can.chata_ai.extension

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build

object CustomColor {
	fun getBackgroundDrawable(pressedColor: Int, backgroundDrawable: Drawable) =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			RippleDrawable(getPressedState(pressedColor), backgroundDrawable, null)
		else backgroundDrawable

	private fun getPressedState(pressedColor: Int) =
		ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))
}