package chata.can.chata_ai.activity.chat.holder

import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class RightHolder(view: View): BaseHolder(view)
{
	override fun onPaint()
	{
		val textColor = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_hover_color)
		tvContent.setTextColor(textColor)

		val accentColor = ContextCompat.getColor(
			tvContent.context,
			ThemeColor.currentColor.drawerAccentColor)
		val queryDrawable = DrawableBuilder.setGradientDrawable(accentColor,18f)
		tvContent.background = queryDrawable

		val animation = AnimationUtils.loadAnimation(tvContent.context, R.anim.scale)
		tvContent.startAnimation(animation)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			tvContent.text = item.message
		}
	}
}