package chata.can.chata_ai.activity.chat.holder

import android.view.View
import android.widget.LinearLayout
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
						//add new view for suggestion

					}
				}
			}
		}
	}
}