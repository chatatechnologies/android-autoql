package chata.can.chata_ai.view.chatDrawer

import android.content.Context
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.chatDrawer.model.DrawableChatDrawer

class ChatDrawerRenderPresenter(
	private val cContext: Context,
	private val view: ChatDrawerContract)
{
	private fun createBackground() = DrawableBuilder.setGradientDrawable(
			ContextCompat.getColor(cContext, R.color.white),
			90f,
			3,
			ContextCompat.getColor(cContext, R.color.border_color))

	fun setDrawables()
	{
		val shapeBackground = createBackground()
		val shapeDropDown = createBackground()
		val shapeMicrophone = DrawableBuilder.setOvalDrawable(
			ContextCompat.getColor(cContext, R.color.accent_color))
		view.setDrawables(DrawableChatDrawer(shapeBackground, shapeDropDown, shapeMicrophone))
	}
}