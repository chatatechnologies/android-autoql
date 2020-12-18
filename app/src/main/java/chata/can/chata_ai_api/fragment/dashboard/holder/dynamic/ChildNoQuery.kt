package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

object ChildNoQuery {
	private const val noQueryText = "No query was supplied for this title"

	fun onBind(view: View, dashboard: Dashboard, isPrimary: Boolean)
	{
		view.findViewById<TextView>(R.id.tvNoQuery)?.let { tvNoQuery ->
			tvNoQuery.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)

			dashboard.run {
				if (isPrimary)
				{
					if (query.isEmpty())
					{
						tvNoQuery.text = noQueryText
					}
				}
				else
				{
					if (secondQuery.isEmpty())
					{
						tvNoQuery.text = noQueryText
					}
				}
			}
		}
	}
}