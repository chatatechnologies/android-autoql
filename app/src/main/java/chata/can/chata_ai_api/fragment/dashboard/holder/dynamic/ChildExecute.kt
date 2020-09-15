package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai_api.R

object ChildExecute
{
	fun onBind(view: View)
	{
		view.findViewById<TextView>(R.id.tvExecute)?.let {
			val drawerColorPrimary = it.context.getParsedColor(
				ThemeColor.currentColor.drawerColorPrimary)
			it.setTextColor(drawerColorPrimary)
		}
	}
}