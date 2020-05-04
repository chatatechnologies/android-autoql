package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class HolderExecute(itemView: View): HolderDashboard(itemView)
{
	private val tvExecute = itemView.findViewById<TextView>(R.id.tvExecute)

	override fun onPaint()
	{
		super.onPaint()

		ll1?.let {
			val context = it.context
			val white = ContextCompat.getColor(
				context,
				ThemeColor.currentColor.drawerBackgroundColor)
			tvExecute?.setBackgroundColor(white)
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is Dashboard)
		{
			val titleToShow = if (item.title.isNotEmpty()) item.title else item.query
			tvTitle?.text = titleToShow
		}
	}
}