package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.LinearLayout
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getChildSuggestion
import chata.can.chata_ai_api.DashboardView.getChildWebView
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
						TypeChatView.SUGGESTION_VIEW ->
						{
							val viewSuggestion = lls1.searchView(R.id.llMainSuggestion)?: run {
								getChildSuggestion(lls1.context).apply {
									lls1.addView(this)
								}
							}
							ChildSuggestion(viewSuggestion, dashboard, presenter)
						}
						else ->
						{

						}
					}
				}

				dashboard.queryBase2?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.WEB_VIEW ->
						{

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
						val childWebView = lls2.searchView(R.id.rlWebView)?: run {
							getChildWebView(lls2.context).apply {
								lls1.addView(this)
							}
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
//	when(typeView)
//	{
//		TypeChatView.LEFT_VIEW -> 3
//		TypeChatView.WEB_VIEW -> 4
//		TypeChatView.SUGGESTION_VIEW -> 5
//		else -> 2
//	}
}