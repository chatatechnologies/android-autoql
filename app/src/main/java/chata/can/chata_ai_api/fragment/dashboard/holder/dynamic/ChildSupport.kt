package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

object ChildSupport {
	fun onBind(view: View, dashboard: Dashboard, isPrimary: Boolean)
	{
		view.findViewById<TextView>(R.id.tvContent)?.let { tvContent ->
			val drawerColorPrimary = ContextCompat.getColor(
				tvContent.context, ThemeColor.currentColor.drawerColorPrimary)
			tvContent.setTextColor(drawerColorPrimary)

			dashboard.run {
				if (isPrimary)
					queryBase?.let {
						tvContent.text = it.message
					}
				else
					queryBase2?.let {
						tvContent.text = it.message
					}
			}
		}
	}
}