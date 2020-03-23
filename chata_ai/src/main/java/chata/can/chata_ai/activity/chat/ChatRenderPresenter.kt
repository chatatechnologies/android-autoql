package chata.can.chata_ai.activity.chat

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class ChatRenderPresenter(
	private val context: Context, private val contract: ChatContract)
{
	fun setData()
	{
		contract.setData(backgroundBorder())
	}

	private fun backgroundBorder(): Pair<GradientDrawable,GradientDrawable>
	{
		val blue = ContextCompat.getColor(context, R.color.chata_drawer_accent_color)
		val circleDrawable = GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(blue)
		}

		val white = ContextCompat.getColor(context, R.color.chata_drawer_background_color)
		val gray = ContextCompat.getColor(context, R.color.chata_drawer_color_primary)

		val queryDrawable = DrawableBuilder.setGradientDrawable(white,64f,1, gray)
		return Pair(circleDrawable, queryDrawable)
	}
}