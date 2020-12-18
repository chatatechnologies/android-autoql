package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai_api.R

object ChildExecute
{
	fun onBind(view: View)
	{
		view.findViewById<TextView>(R.id.tvExecute)?.let {
			it.setTypeface(it.typeface, Typeface.ITALIC)
			it.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		}
	}
}