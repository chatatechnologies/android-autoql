package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.popup.PopupMenu.buildPopup
import chata.can.chata_ai_api.DashboardView.getChildContent
import chata.can.chata_ai_api.DashboardView.getChildLoading
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
	private val tData1 = Triple(R.id.rlWebView, R.id.webView, R.id.rlLoad)
	private val tData2 = Triple(R.id.rvSplitView, R.id.webView2, R.id.rlLoad2)

	private val ll1 = itemView.findViewById<View>(R.id.ll1)
	private val lls1 = itemView.findViewById<RelativeLayout>(R.id.lls1)
	private val lls2 = itemView.findViewById<RelativeLayout>(R.id.lls2)

	override fun onPaint()
	{
		ll1?.backgroundWhiteGray()
	}

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
							val view = lls1.searchView(R.id.tvContent)?: run {
								val view = getChildContent(lls1.context)
								addView(lls1, view)
								view
							}
							setOption(lls1, R.id.ivOption, queryBase.sql)
							ChildContent.onBind(view, dashboard, true)
						}
						TypeChatView.SUPPORT ->
						{
							val view = lls1.searchView(R.id.tvContent)?: run {
								val view = getChildContent(lls1.context)
								addView(lls1, view)
								view
							}
							ChildSupport.onBind(view, dashboard, true)
						}
						TypeChatView.SUGGESTION_VIEW ->
						{
							var vSuggestion = lls1.searchView(R.id.llMainSuggestion)
							if (vSuggestion == null)
							{
								vSuggestion = getChildSuggestion(lls1.context)
								addView(lls1, vSuggestion)
							}
							ChildSuggestion.onBind(vSuggestion, dashboard, presenter, true)
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
							ChildNoQuery.onBind(vExecute, dashboard, true)
						}
						else
						{
							addView(lls1, getChildLoading(lls1.context))
						}
					}
					else
					{
						val vExecute = lls1.searchView(R.id.tvExecute)?:run {
							val vExecute = getExecute(lls1.context, R.id.tvExecute)
							addView(lls1, vExecute)
							vExecute
						}
						ChildExecute.onBind(vExecute)
					}
				}

				dashboard.queryBase2?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.LEFT_VIEW ->
						{
							val view = lls2.searchView(R.id.tvContent)?: run {
								val view = getChildContent(lls2.context)
								addView(lls2, view)
								view
							}
							setOption(lls2, R.id.ivOption2, queryBase.sql)
							ChildContent.onBind(view, dashboard, false)
						}
						TypeChatView.SUPPORT ->
						{
							val view = lls2.searchView(R.id.tvContent)?: run {
								val view = getChildContent(lls2.context)
								addView(lls2, view)
								view
							}
							ChildSupport.onBind(view, dashboard, false)
						}
						TypeChatView.SUGGESTION_VIEW ->
						{
							var vSuggestion2 = lls2.searchView(R.id.llMainSuggestion)
							if (vSuggestion2 == null)
							{
								vSuggestion2 = getChildSuggestion(lls2.context)
								addView(lls2, vSuggestion2)
							}
							ChildSuggestion.onBind(vSuggestion2, dashboard, presenter, false)
						}
						else -> {}
					}
				} ?: run {
					if (dashboard.isWaitingData2)
					{
						if (dashboard.query.isEmpty())
						{
							var vExecute2 = lls2.searchView(R.id.tvNoQuery)
							if (vExecute2 == null)
							{
								vExecute2 = getExecute(lls2.context, R.id.tvNoQuery)
								addView(lls2, vExecute2)
							}
							ChildNoQuery.onBind(vExecute2, dashboard, false)
						}
						else
						{
							addView(lls2, getChildLoading(lls2.context))
						}
					}
					else
					{
						val vExecute2 = lls2.searchView(R.id.tvExecute)?:run {
							val vExecute2 = getExecute(lls2.context, R.id.tvExecute)
							addView(lls2, vExecute2)
							vExecute2
						}
						ChildExecute.onBind(vExecute2)
					}
				}
			}

			if (item is QueryBase)
			{
				when(item.typeView)
				{
					TypeChatView.WEB_VIEW ->
					{
						val tData = if (item.isSecondaryQuery)
						{
							Triple(lls2, R.id.rvSplitView, tData2)
						}
						else
						{
							Triple(lls1, R.id.rlWebView, tData1)
						}
						tData.run {
							var childWebView = first.searchView(second)
							if (childWebView == null)
							{
								childWebView = getChildWebView(first.context, second).apply {
									findViewById<View>(R.id.ivOption)?.setOnClickListener {
										buildPopup(it, listOf(4), item.sql)
									}
									findViewById<View>(R.id.ivOption2)?.setOnClickListener {
										buildPopup(it, listOf(4), item.sql)
									}
									findViewById<View>(R.id.ivAlert)?.let {
										configAlert(it, item)
									}
									findViewById<View>(R.id.ivAlert2)?.let {
										configAlert(it, item)
									}
								}
								addView(first, childWebView)
							}
							ChildWebView.onBind(childWebView, item, tData.third)
						}
					}
				}
			}
		}
	}

	private fun View.searchView(id: Int): View?
	{
		return findViewById(id)
	}

	private fun addView(llRoot: RelativeLayout, newView: View)
	{
		llRoot.run {
			removeAllViews()
			addView(newView)
		}
	}

	private fun setOption(view: ViewGroup, idRes: Int, sql: String)
	{
		val iv = ImageView(view.context).apply {
			layoutParams = getRelativeLayoutParams(dpToPx(42f), dpToPx(42f)).apply {
				addRule(RelativeLayout.ALIGN_PARENT_END)
				addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
			}
			id = idRes
			paddingAll(8f)
			setImageResource(R.drawable.ic_points)
		}
		ChildContent.onBindOption(iv, sql)
		view.addView(iv)
	}

	private fun configAlert(view: View, item: QueryBase)
	{
		view.visibility = if (item.limitRowNum <= item.aRows.size)
		{
			view.setOnClickListener {
				showToast()
			}
			View.VISIBLE
		}
		else View.GONE
	}

	private fun showToast()
	{
		val context = itemView.context
		Toast.makeText(context, R.string.limit_row_num, Toast.LENGTH_LONG).show()
	}
}