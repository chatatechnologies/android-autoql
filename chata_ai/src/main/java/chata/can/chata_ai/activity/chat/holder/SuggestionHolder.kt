package chata.can.chata_ai.activity.chat.holder

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData

class SuggestionHolder(view: View): BaseHolder(view)
{
	val llSuggestion = view.findViewById<LinearLayout>(R.id.llSuggestion)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			item.queryBase?.let {
				val rows = it.aRows
				for (index in 0 until rows.size)
				{
					val singleRow = rows[index]
					singleRow.firstOrNull()?.let {
						suggestion ->
						//add new view for suggestion
						val tv = buildSuggestion(llSuggestion.context, suggestion)
						llSuggestion.addView(tv)
					}
				}
			}
		}
	}

	private fun buildSuggestion(context: Context, content: String): TextView
	{
		return TextView(llSuggestion.context).apply {
			text = content
		}
	}
}