package chata.can.chata_ai.view.bubbleHandle

import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesData
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer

object DataMessengerRoot
{
	//var dataMessenger: DataMessenger? = null
	//set with order part
	var projectId = ""

	var userID = ""
	//init
	var apiKey = ""
	//init
	var domainUrl = ""
	//init
	var token = ""
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

		SinglentonDrawer.mModel.clear()
		SinglentonDashboard.releaseDashboard()
		ExploreQueriesData.lastWord = ""
		ExploreQueriesData.lastExploreQuery = null
	}

	fun notLoginData() = projectId == "" || apiKey == "" || domainUrl == "" || JWT == ""
}