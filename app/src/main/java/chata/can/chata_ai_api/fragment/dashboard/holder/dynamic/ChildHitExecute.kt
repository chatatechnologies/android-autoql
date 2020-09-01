package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ChildHitExecute(
	view: View,
	private val dashboard: Dashboard
)
{
	private val tvExecute: TextView = view.findViewById(R.id.tvExecute)

	fun onBind()
	{

	}
}