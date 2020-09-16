package chata.can.chata_ai.activity.dataMessenger.presenter

import chata.can.chata_ai.pojo.chat.SimpleQuery

interface PresenterContract
{
	fun isLoading(isVisible: Boolean)
	fun addNewChat(typeView: Int, queryBase: SimpleQuery)
}