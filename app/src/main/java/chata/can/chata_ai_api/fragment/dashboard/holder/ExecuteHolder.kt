package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import chata.can.chata_ai_api.R

class ExecuteHolder(itemView: View): BaseHolder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null

	override fun onPaint()
	{
		super.onPaint()
		ll1?.setBackgroundColor(drawerBackgroundColor)
	}
}