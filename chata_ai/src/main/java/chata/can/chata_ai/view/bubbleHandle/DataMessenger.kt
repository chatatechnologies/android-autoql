package chata.can.chata_ai.view.bubbleHandle

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

		SinglentonDrawer.mModel.clear()
		SinglentonDashboard.releaseDashboard()
	}

	fun isDemo() = !isNecessaryLogin || domainUrl.isEmpty()
}