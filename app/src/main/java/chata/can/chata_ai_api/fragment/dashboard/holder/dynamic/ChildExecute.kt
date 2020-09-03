package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai_api.R

object ChildExecute
{
	fun onBind(view: View)
	{
		view.findViewById<TextView>(R.id.tvExecute)?.let {
			val drawerColorPrimary = ContextCompat.getColor(
				it.context, ThemeColor.currentColor.drawerColorPrimary)
			it.setTextColor(drawerColorPrimary)
		}
	}
}