package chata.can.chata_ai.view.chatDrawer

import chata.can.chata_ai.view.chatDrawer.model.DrawableChatDrawer

interface ChatDrawerContract
{
	fun setDataAutocomplete(aMatches: ArrayList<String>)
	fun setDrawables(drawableChatDrawer: DrawableChatDrawer)
}