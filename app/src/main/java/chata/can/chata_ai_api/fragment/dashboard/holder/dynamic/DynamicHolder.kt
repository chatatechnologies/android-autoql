package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.LinearLayout
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getChildSuggestion
import chata.can.chata_ai_api.DashboardView.getChildWebView
import chata.can.chata_ai_api.DashboardView.getExecute
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.BaseHolder

class DynamicHolder(
	itemView: View,
	private val presenter: DashboardPresenter
): BaseHolder(itemView)
{
	private val lls1 = itemView.findViewById<LinearLayout>(R.id.lls1)
	private val lls2 = itemView.findViewById<LinearLayout>(R.id.lls2)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		item?.let { dashboard ->
			if (dashboard is Dashboard)
			{
				dashboard.queryBase?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.LEFT_VIEW ->
						{

						}
						TypeChatView.SUGGESTION_VIEW ->
						{
							var vSuggestion = lls1.searchView(R.id.llMainSuggestion)
							if (vSuggestion == null)
							{
								vSuggestion = getChildSuggestion(lls1.context)
								addView(lls1, vSuggestion)
							}
							ChildSuggestion(vSuggestion, dashboard, presenter)
						}
						else -> { }
					}
				} ?: run {
					if (dashboard.isWaitingData)
					{
						if (dashboard.query.isEmpty())
						{
							var vExecute = lls1.searchView(R.id.tvNoQuery)
							if (vExecute == null)
							{
								vExecute = getExecute(lls1.context, R.id.tvNoQuery)
								addView(lls1, vExecute)
							}
							ChildNoQuery(vExecute, dashboard, true)
						}
					}
					else
					{
						var vExecute = lls1.searchView(R.id.tvExecute)
						if (vExecute == null)
						{
							vExecute = getExecute(lls1.context, R.id.tvExecute)
							addView(lls1, vExecute)
						}
					}
				}

				dashboard.queryBase2?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.LEFT_VIEW ->
						{

						}
						TypeChatView.WEB_VIEW ->
						{

						}
						else ->
						{

						}
					}
				} ?: run {
					if (dashboard.isWaitingData)
					{
						if (dashboard.query.isEmpty())
						{
							var vExecute2 = lls2.searchView(R.id.tvNoQuery)
							if (vExecute2 == null)
							{
								vExecute2 = getExecute(lls2.context, R.id.tvNoQuery)
								addView(lls2, vExecute2)
							}
							ChildNoQuery(vExecute2, dashboard, true)
						}
					}
					else
					{
						var vExecute2 = lls2.searchView(R.id.tvExecute)
						if (vExecute2 == null)
						{
							vExecute2 = getExecute(lls2.context, R.id.tvExecute)
							addView(lls2, vExecute2)
						}
					}
				}
			}

			if (item is QueryBase)
			{
				when(item.typeView)
				{
					TypeChatView.WEB_VIEW ->
					{
						var childWebView = lls2.searchView(R.id.rlWebView)
						if (childWebView == null)
						{
							childWebView = getChildWebView(lls2.context)
							addView(lls2, childWebView)
						}
						ChildWebView(childWebView, item)
					}
				}
			}
		}
	}

	private fun View.searchView(id: Int): View?
	{
		return findViewById(id)
	}

	private fun addView(llRoot: LinearLayout, newView: View)
	{
		llRoot.run {
			removeAllViews()
			addView(newView)
		}
	}
//	when(typeView)
//	{
//		TypeChatView.LEFT_VIEW -> 3
//		TypeChatView.WEB_VIEW -> 4
//		TypeChatView.SUGGESTION_VIEW -> 5
//		else -> 2
//	}
}