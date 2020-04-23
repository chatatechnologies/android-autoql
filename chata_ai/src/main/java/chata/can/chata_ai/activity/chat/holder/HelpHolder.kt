package chata.can.chata_ai.activity.chat.holder

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class HelpHolder(itemView: View): Holder(itemView)
{
	private var llContent = itemView.findViewById<View>(R.id.llContent) ?: null
	private var tvGreatNewHelp= itemView.findViewById<TextView>(R.id.tvGreatNewHelp) ?: null
	private var tvContent = itemView.findViewById<TextView>(R.id.tvContent) ?: null

	override fun onPaint()
	{
		llContent?.let {
			it.background = backgroundGrayWhite(it)
		}

		tvContent?.let {
			val gray = ContextCompat.getColor(
				it.context,
				ThemeColor.currentColor.drawerColorPrimary)
			it.setTextColor(gray)
			it.background = backgroundGrayWhite(it)
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{

	}

	private fun backgroundGrayWhite(view: View): GradientDrawable
	{
		val white = ContextCompat.getColor(
			view.context,
			ThemeColor.currentColor.drawerBackgroundColor)
		val gray = ContextCompat.getColor(
			view.context,
			ThemeColor.currentColor.drawerColorPrimary)
		return DrawableBuilder.setGradientDrawable(white,18f,1, gray)
	}
}