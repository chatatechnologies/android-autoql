package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getRowContent
import chata.can.chata_ai_api.DashboardView.getRowExecute
import chata.can.chata_ai_api.DashboardView.getRowLoading
import chata.can.chata_ai_api.DashboardView.getRowSuggestion
import chata.can.chata_ai_api.DashboardView.getRowTwin
import chata.can.chata_ai_api.DashboardView.getRowWebView
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.*
import chata.can.chata_ai_api.fragment.dashboard.holder.dynamic.DynamicHolder
import chata.can.chata_ai_api.fragment.dashboard.holder.suggestion.SuggestionHolder

class GridAdapter(
	private val model: List<*>,
	private val presenter: DashboardPresenter
): RecyclerView.Adapter<BaseHolder>()
{
	override fun getItemCount(): Int = model.size

	override fun getItemViewType(position: Int): Int
	{
		/**
		 * 0 for start view (execute message)
		 * 1 for loading data (gifView)
		 * 2 for support message
		 * 3 for simple text data
		 * 4 for webView data
		 * 8 for no query data
		 */
		var viewType = 0
		model[position]?.run {
			if (this is Dashboard)
			{
				if (splitView)
				{
					viewType = 10
				}
				//region once QueryBase
				else
				{
					queryBase?.run {
						viewType = when(typeView)
						{
							TypeChatView.LEFT_VIEW -> 3
							TypeChatView.WEB_VIEW -> 4
							TypeChatView.SUGGESTION_VIEW -> 5
							else -> 2
						}
					} ?: run {
						if (isWaitingData)
						{
							viewType = if (query.isEmpty())
								8
							else 1
						}
					}
				}
				//endregion
			}
		}
		return viewType
	}

	override fun onBindViewHolder(holder: BaseHolder, position: Int) {
		val item = model[position]
		if (item is Dashboard) {
			item.queryBase?.checkData(holder)
			item.queryBase2?.checkData(holder)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder
	{
		val context = parent.context
		return when(viewType)
		{
			0 -> {
//				ExecuteHolder(getRowExecute(context))
				val view = parent.inflateView(R.layout.card_execute)
				ExecuteHolder(view)
			}
			1 -> LoadingHolder(getRowLoading(context))
			2 -> SupportHolder(getRowContent(context))//dynamic
			3 -> ContentHolder(getRowContent(context, true))//dynamic; show option
			4 -> WebViewHolder(getRowWebView(context))//; show option
			5 -> SuggestionHolder(getRowSuggestion(context), presenter)//dynamic
			8 -> NoQueryHolder(getRowExecute(context))//dynamic
			10 -> DynamicHolder(getRowTwin(context), presenter)
			else -> {
				val view = parent.inflateView(R.layout.card_execute)
				ExecuteHolder(view)
			}
		}
	}

	private fun ViewGroup.inflateView(resource: Int): View {
		val inflater = LayoutInflater.from(context)
		return inflater.inflate(resource, this, false)
	}
}