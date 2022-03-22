package chata.can.chata_ai.retrofit.ui.view.notification

import android.graphics.drawable.GradientDrawable
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.domain.GetRuleQueryUseCase

class NotificationUi {
	private var blue = 0
	private var gray = 0
	private var white = 0

	val ruleQueryUseCase = GetRuleQueryUseCase()

	init {
		ThemeColor.currentColor.run {
			blue = SinglentonDrawer.currentAccent
			gray = pDrawerTextColorPrimary
			white = pDrawerBackgroundColor
		}
	}

	fun getDrawableParent(): GradientDrawable {
		return DrawableBuilder.setGradientDrawable(white, 18f,0, gray)
	}

	fun getTextColorPrimary() = gray
}