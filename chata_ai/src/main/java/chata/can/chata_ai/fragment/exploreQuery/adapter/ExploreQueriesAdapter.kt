package chata.can.chata_ai.fragment.exploreQuery.adapter

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList

class ExploreQueriesAdapter(model: BaseModelList<*>, listener: OnItemClickListener)
	: BaseAdapter(model, listener)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		return QueryHolder(getRowExploreQueries(parent.context))
	}

	private fun getRowExploreQueries(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -2)
			paddingAll(left = 16f, right = 16f)
			addView(TextView(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2)
				gravity = Gravity.CENTER
				textSize(16f)
				paddingAll(8f)
				id = R.id.tvQuery
			})
			addView(View(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, dpToPx(0.5f)).apply {
					addRule(RelativeLayout.BELOW, R.id.tvQuery)
				}
				setBackgroundColor(context.getParsedColor(android.R.color.darker_gray))
			})
		}
	}
}