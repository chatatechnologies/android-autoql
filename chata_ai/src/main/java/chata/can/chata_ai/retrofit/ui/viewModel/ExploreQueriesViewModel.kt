package chata.can.chata_ai.retrofit.ui.viewModel

import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModel
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

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
}