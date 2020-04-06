package chata.can.chata_ai.pojo

object DataMessenger
{
	var projectId = ""
	var userID = ""
	var apiKey = ""
	var domainUrl = ""
	var username = ""
	var password = ""

	var token = ""
	var JWT = ""

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
	}
}