package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getChildSuggestion
import chata.can.chata_ai_api.R

class DynamicHolder(itemView: View): BaseHolder(itemView)
{
	private val lls1 = itemView.findViewById<LinearLayout>(R.id.lls1)
	private val lls2 = itemView.findViewById<LinearLayout>(R.id.lls2)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		item?.let {
			if (item is Dashboard)
			{
				item.queryBase?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.SUGGESTION_VIEW ->
						{
							val viewSuggestion = getChildSuggestion(lls1.context)
							lls1.addView(viewSuggestion)
						}
						else ->
						{

						}
					}
				}
			}
		}
	}

	//region presenter suggestion
	class SubSuggestion(
		private val view: View,
		private val queryBase: QueryBase
	): Holder(view)
	{
		private val tvContent = view.findViewById<TextView>(R.id.tvContent)?: null
		private val llSuggestion = view.findViewById<LinearLayout>(R.id.tvContent)?: null

		override fun onPaint()
		{
			tvContent?.let {
				val drawerColorPrimary =
					ContextCompat.getColor(it.context, ThemeColor.currentColor.drawerColorPrimary)
				it.setTextColor(drawerColorPrimary)
			}
		}

		override fun onBind(item: Any?, listener: OnItemClickListener?)
		{
			queryBase.let {
				tvContent?.context?.let { context ->
					val introMessageRes = context.getStringResources(R.string.msg_suggestion)
					val message = String.format(introMessageRes, it.message)
					tvContent.text = message
				}

				val rows = it.aRows
				llSuggestion?.removeAllViews()
				for (index in 0 until rows.size)
				{
					val singleRow = rows[index]
					singleRow.firstOrNull()?.let { suggestion ->
						//add new view for suggestion
//						val tv = buildSuggestionView(llSuggestion.context, suggestion, item)
//						llSuggestion.addView(tv)
					}
				}
			}
		}
	}
	//endregion
//	when(typeView)
//	{
//		TypeChatView.LEFT_VIEW -> 3
//		TypeChatView.WEB_VIEW -> 4
//		TypeChatView.SUGGESTION_VIEW -> 5
//		else -> 2
//	}
}