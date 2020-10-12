package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

object ChildContent {
	fun onBind(view: View, dashboard: Dashboard, isPrimary: Boolean)
	{
		view.findViewById<TextView>(R.id.tvContent)?.let { tvContent ->
			val drawerColorPrimary = tvContent.context.getParsedColor(
				ThemeColor.currentColor.drawerTextColorPrimary)
			tvContent.setTextColor(drawerColorPrimary)

			dashboard.run {
				if (isPrimary)
				{
					queryBase?.let { queryBase ->
						setDataContent(tvContent, queryBase)
					}
				}
				else
				{
					queryBase2?.let { queryBase ->
						setDataContent(tvContent, queryBase)
					}
				}
			}
		}
	}

	private fun setDataContent(tvContent: TextView, queryBase: QueryBase)
	{
		tvContent.text = queryBase.contentHTML
		if (SinglentonDrawer.mIsEnableDrillDown)
		{
			tvContent.setOnClickListener {
				DrillDownDialog(tvContent.context, queryBase).show()
			}
		}
	}
}