package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class NoQueryHolder(itemView: View): BaseHolder(itemView)
{
	private val iView = itemView.findViewById<View>(R.id.iView) ?: null
	private val tvExecute = itemView.findViewById<TextView>(R.id.tvExecute) ?: null
	private val tvExecute2: TextView = itemView.findViewById(R.id.tvExecute2)

	override fun onPaint()
	{
		super.onPaint()
		iView?.let {
			(it.layoutParams as? LinearLayout.LayoutParams)?.let { layout ->
				layout.height = 1
				iView.layoutParams = layout
			}
		}
		tvExecute?.setTextColor(drawerColorPrimary)
		tvExecute2.setTextColor(drawerColorPrimary)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			if (item.splitView)
			{
				iView?.setBackgroundColor(drawerColorPrimary)
			}
			else
			{
				tvExecute2.visibility = View.GONE
				iView?.setBackgroundColor(drawerBackgroundColor)
			}

			if (item.query.isEmpty())
			{
				val text = "No query was supplied for this title"
				tvExecute?.text = text
			}
		}
	}
}