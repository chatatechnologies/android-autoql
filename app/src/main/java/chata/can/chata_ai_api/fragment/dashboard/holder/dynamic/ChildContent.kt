package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ChildContent(
	view: View,
	private val dashboard: Dashboard,
	private val isPrimary: Boolean
)
{
	private val tvContent = view.findViewById<TextView>(R.id.tvContent)?: null
	init {
		onBind()
	}

	private fun onBind()
	{
		dashboard.run {
			if (isPrimary)
			{
				queryBase?.let {
					tvContent?.text = it.message
				}
			}
			else
			{
				queryBase2?.let {
					tvContent?.text = it.message
				}
			}
		}
	}
}