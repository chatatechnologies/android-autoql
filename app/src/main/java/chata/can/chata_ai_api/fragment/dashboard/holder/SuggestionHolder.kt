package chata.can.chata_ai_api.fragment.dashboard.holder

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.request.query.QueryRequest
import chata.can.chata_ai_api.R

class SuggestionHolder(itemView: View): BaseHolder(itemView)
{
	private val llContent: View = itemView.findViewById(R.id.llContent)
	private val tvContent: TextView = itemView.findViewById(R.id.tvContent)
	private val llSuggestion: LinearLayout = itemView.findViewById(R.id.llSuggestion)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.let {
				tvContent.context?.let { context ->
					val introMessageRes = context.getStringResources(R.string.msg_suggestion)
					val message = String.format(introMessageRes, it.message)
					tvContent.text = message
				}

				val rows = it.aRows
				llSuggestion.removeAllViews()
				for (index in 0 until rows.size)
				{
					val singleRow = rows[index]
					singleRow.firstOrNull()?.let {
							suggestion ->
						//add new view for suggestion
						val tv = buildSuggestionView(llSuggestion.context, suggestion)
						llSuggestion.addView(tv)
					}
				}
			}
		}
	}

	private fun buildSuggestionView(context: Context, content: String): TextView
	{
		return TextView(context).apply {
			backgroundGrayWhite()
			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
			setPadding(15,15,15,15)
			text = content
			setOnClickListener {
				//view.addChatMessage(2, content)
				val mInfoHolder = hashMapOf<String, Any>("query" to content)
				//QueryRequest.callQuery(content, servicePresenter, "data_messenger", mInfoHolder)
			}
		}
	}
}