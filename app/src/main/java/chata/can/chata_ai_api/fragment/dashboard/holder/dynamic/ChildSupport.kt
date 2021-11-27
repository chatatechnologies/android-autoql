package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

object ChildSupport {
	fun onBind(view: View, dashboard: Dashboard, isPrimary: Boolean)
	{
		view.findViewById<TextView>(R.id.tvContent)?.let { tvContent ->
			tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)

			dashboard.run {
				if (isPrimary)
					queryBase?.let {
						tvContent.text = getErrorMessage(it)
					}
				else
					queryBase2?.let {
						tvContent.text = getErrorMessage(it)
					}
			}
		}
	}

	private fun getErrorMessage(queryBase: QueryBase): String
	{
		return queryBase.message + "\n\n Error ID: ${queryBase.referenceId}"
	}
}