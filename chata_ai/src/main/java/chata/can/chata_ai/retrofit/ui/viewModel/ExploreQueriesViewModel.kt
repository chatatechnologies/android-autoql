package chata.can.chata_ai.retrofit.ui.viewModel

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import kotlin.math.abs
import kotlin.math.log10

class ExploreQueriesViewModel: ViewModel() {

	var colorSecondary = 0
	var backgroundColor = 0
	var borderColor = 0
	var textColorPrimary = 0

	init {
		ThemeColor.currentColor.run {
			colorSecondary = pDrawerColorSecondary
			backgroundColor = pDrawerBackgroundColor
			borderColor = drawerBorderColor
			textColorPrimary = pDrawerTextColorPrimary
		}
	}

	fun getDrawableQuery(): GradientDrawable {
		return DrawableBuilder.setGradientDrawable(backgroundColor, 64f, 1, borderColor)
	}

	fun getDrawableSearch(): GradientDrawable {
		return DrawableBuilder.setOvalDrawable(getAccentColor())
	}

	fun getAccentColor() = SinglentonDrawer.currentAccent

	companion object {
		fun Int.length() = when(this) {
			0 -> 1
			else -> log10(abs(toDouble())).toInt() + 1
		}

		@JvmStatic
		@BindingAdapter("ovalBackground")
		fun setOvalBackground(view: View, drawable: Drawable?) {
			val count = 1

			val gradientDrawable = DrawableBuilder.setOvalDrawable(SinglentonDrawer.currentAccent)
			val height = view.dpToPx(30f)
			val width = view.dpToPx(25f + (count.length() * 5))//count = 1
			gradientDrawable.setSize(width, height)
			view.background = gradientDrawable
		}
	}
}