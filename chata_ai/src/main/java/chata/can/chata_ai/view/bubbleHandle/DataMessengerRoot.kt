package chata.can.chata_ai.view.bubbleHandle

import chata.can.chata_ai.data.DataMessenger
import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesData
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer

object DataMessengerRoot
{
	var dataMessenger: DataMessenger? = null
	//set with order part
	var projectId = ""

	var userID = ""
	//init
	var apiKey = dataMessenger?.authentication?.apiKey ?: ""
	//init
	var domainUrl = dataMessenger?.authentication?.domainUrl ?: ""
	//init
	var token = dataMessenger?.authentication?.token ?: ""
	var username = ""
	var password = ""
	var JWT = ""

	fun clearData()
	{
		projectId = ""
		userID = ""
		username = ""
		password = ""
		apiKey = ""
		domainUrl = ""
		token = ""
		JWT = ""

		val model = SinglentonDrawer.mModel
		while (model.countData() > 2)
		{
			model.removeLast()
		}
		SinglentonDashboard.releaseDashboard()
		ExploreQueriesData.lastWord = ""
		ExploreQueriesData.lastExploreQuery = null
	}

	fun notLoginData() = projectId == "" || apiKey == "" || domainUrl == "" || JWT == ""
}