package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ExecuteHolder(itemView: View): BaseHolder(itemView)
{
	private val tvExecute = itemView.findViewById<TextView>(R.id.rlWebView) ?: null

	override fun onPaint()
	{
		super.onPaint()
		tvExecute?.setBackgroundColor(drawerBackgroundColor)
	}
}