package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R

abstract class HolderDashboard(itemView: View): Holder(itemView)
{
	val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle) ?: null

	override fun onPaint() {
		ll1?.let {
			val context = it.context
			val white = ContextCompat.getColor(
				context,
				ThemeColor.currentColor.drawerBackgroundColor)
			val gray = ContextCompat.getColor(context, ThemeColor.currentColor.drawerColorPrimary)

			it.background = DrawableBuilder.setGradientDrawable(white,18f,1, gray)
		}
	}
}