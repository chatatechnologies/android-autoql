package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

object ChildNoQuery {
	fun onBind(view: View, dashboard: Dashboard, isPrimary: Boolean)
	{
		val noQueryText = view.context.getString(R.string.no_query_title)
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