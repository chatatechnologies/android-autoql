package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ChildNoQuery(
	view: View,
	private val dashboard: Dashboard,
	private val isPrimary: Boolean
)
{
	private val noQueryText = "No query was supplied for this title"
	private val tvNoQuery = view.findViewById<TextView>(R.id.tvNoQuery)?: null

	init {
		onPaint()
		onBind()
	}

	private fun onPaint()
	{
		tvNoQuery?.let {
			val drawerColorPrimary =
				ContextCompat.getColor(it.context, ThemeColor.currentColor.drawerColorPrimary)
			it.setTextColor(drawerColorPrimary)
		}
	}

	private fun onBind()
	{
		dashboard.run {
			if (isPrimary)
			{
				if (query.isEmpty())
				{
					tvNoQuery?.text = noQueryText
				}
			}
			else
			{
				if (secondQuery.isEmpty())
				{
					tvNoQuery?.text = noQueryText
				}
			}
		}
	}
}