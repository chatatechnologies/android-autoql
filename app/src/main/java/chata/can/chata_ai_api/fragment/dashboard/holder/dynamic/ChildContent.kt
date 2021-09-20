package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

object ChildContent {
	fun onBind(view: View, dashboard: Dashboard, isPrimary: Boolean)
	{
		view.findViewById<TextView>(R.id.tvContent)?.let { tvContent ->
			tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)

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

	fun onBindOption(iv: ImageView)
	{
		iv.run {
			backgroundWhiteGray()
			setColorFilter(SinglentonDrawer.currentAccent)
			setOnClickListener {

			}
		}
	}

	private fun setDataContent(tvContent: TextView, queryBase: QueryBase)
	{
		tvContent.text = queryBase.contentHTML
		tvContent.setOnClickListener {
			if (SinglentonDrawer.mIsEnableDrillDown)
			{
				DrillDownDialog(tvContent.context, queryBase).show()
			}
		}
	}
}