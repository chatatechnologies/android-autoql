package chata.can.chata_ai.activity.chat

import android.graphics.drawable.GradientDrawable
import chata.can.chata_ai.pojo.chat.QueryBase

interface ChatContract
{
	fun addNewChat(queryBase: QueryBase)

	fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
	fun setDataAutocomplete(aMatches: ArrayList<String>)
}