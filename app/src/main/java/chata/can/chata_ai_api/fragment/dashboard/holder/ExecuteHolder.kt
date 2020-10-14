package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R

class ExecuteHolder(itemView: View): BaseHolder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1)

	private val iView = itemView.findViewById<View>(R.id.iView) ?: null
	private val tvExecute: TextView = itemView.findViewById(R.id.tvExecute)
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
		with(ThemeColor.currentColor)
		{
			ll1.context?.let {
				val backgroundColor = it.getParsedColor(drawerBackgroundColor)
				ll1.background =  DrawableBuilder.setGradientDrawable(backgroundColor, 18f)
			}
		}
//		tvExecute.setTextColor(drawerColorPrimary)
//		tvExecute2.setTextColor(drawerColorPrimary)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			if (item.splitView)
			{
				val layoutParams = tvExecute.layoutParams
				layoutParams.height = tvExecute.dpToPx(150f)

				val layoutParams2 = tvExecute2.layoutParams
				layoutParams2.height = tvExecute2.dpToPx(150f)
				tvExecute2.visibility = View.VISIBLE
				iView?.visibility = View.VISIBLE
			}
			else
			{
				tvExecute2.visibility = View.GONE
				iView?.visibility = View.GONE
			}
		}
	}
}