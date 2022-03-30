package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.*
import chata.can.chata_ai_api.fragment.dashboard.holder.dynamic.DynamicHolder

class GridAdapter(
	private val model: List<Dashboard>,
	private val presenter: DashboardPresenter
): RecyclerView.Adapter<DashboardHolder>()
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
		model[position].run {
			if (splitView) {
				viewType = 10
			}
			//region once QueryBase
			else
			{
				if (contentFromViewModel.isNotEmpty()) {
					viewType = 3
				} else {
					if (isWaitingData)
					{
						viewType = if (query.isEmpty()) 8
						else 1
					}
				}

//				queryBase?.run {
//					viewType = when(typeView)
//					{
//						TypeChatView.LEFT_VIEW -> 3
//						TypeChatView.WEB_VIEW -> 4
//						TypeChatView.SUGGESTION_VIEW -> 5
//						else -> 2
//					}
//				} ?: run {
//					if (isWaitingData)
//					{
//						viewType = if (query.isEmpty())
//							8
//						else 1
//					}
//				}
			}
			//endregion
		}
		return viewType
	}

	override fun onBindViewHolder(holder: DashboardHolder, position: Int) {
		val dashboard = model[position]
		holder.onRender(dashboard)
//		if (item is Dashboard) {
//			item.queryBase?.checkData(holder)
//			item.queryBase2?.checkData(holder)
//		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder
	{
		return when(viewType)
		{
			0 -> {
//				ExecuteHolder(getRowExecute(context))
				val view = parent.inflateView(R.layout.card_execute)
				ExecuteHolder(view)
			}
			1 -> {
//				LoadingHolder(getRowLoading(context))
				val view = parent.inflateView(R.layout.card_loading)
				LoadingHolder(view)
			}
			/**
			2 -> {
//				SupportHolder(getRowContent(context))
				val view = parent.inflateView(R.layout.card_support)
				SupportHolder(view)
			}//dynamic
			**/
			3 -> {
//				ContentHolder(getRowContent(context, true))
				val view = parent.inflateView(R.layout.card_content)
				ContentHolder(view)
			}//dynamic; show option
			/**
			4 -> {
//				WebViewHolder(getRowWebView(context))
				val view = parent.inflateView(R.layout.card_web_view)
				WebViewHolder(view)
			}//; show option
			5 -> {
//				SuggestionHolder(getRowSuggestion(context), presenter)
				val view = parent.inflateView(R.layout.card_suggestion)
				SuggestionHolder(view, presenter)
			}//dynamic
			 **/
			8 -> {
//				NoQueryHolder(getRowExecute(context))
				val view = parent.inflateView(R.layout.card_no_query)
				NoQueryHolder(view)
			}//dynamic
			10 -> {
//				DynamicHolder(getRowTwin(context), presenter)
				val view = parent.inflateView(R.layout.card_dynamic)
				DynamicHolder(view, presenter)
			}
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