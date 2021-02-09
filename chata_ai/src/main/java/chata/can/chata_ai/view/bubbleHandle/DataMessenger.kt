package chata.can.chata_ai.view.bubbleHandle

import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesData
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer

object DataMessenger
{
	var projectId = ""
	var userID = ""
	//init
	var apiKey = ""
	//init
	var domainUrl = ""
	var username = ""
	var password = ""

	//init
	var token = ""
	var JWT = ""
	var loginIsComplete = false
	get() {
		return if (!field) projectId != "" && domainUrl != "" && token != "" && apiKey != "" else field
	}
	var isNecessaryLogin = true

	fun clearData()
	{
		projectId = ""
		userID = ""
		apiKey = ""
		domainUrl = ""
		username = ""
		password = ""
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

	fun isDemo() = !isNecessaryLogin || domainUrl.isEmpty()

	fun notLoginData() = projectId != "" && apiKey != "" && domainUrl != ""
}