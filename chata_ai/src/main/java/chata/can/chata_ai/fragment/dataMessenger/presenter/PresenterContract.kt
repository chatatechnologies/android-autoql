package chata.can.chata_ai.fragment.dataMessenger.presenter

import chata.can.chata_ai.pojo.chat.SimpleQuery

interface PresenterContract
{
	fun isLoading(isVisible: Boolean)
	fun addNewChat(typeView: Int, queryBase: SimpleQuery)
}