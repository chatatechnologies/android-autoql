package chata.can.chata_ai.request.authentication

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.getMainURL
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import chata.can.request_native.StatusResponse

object Authentication
{
	fun getAuthorizationJWT() = hashMapOf("Authorization" to "Bearer ${AutoQLData.JWT}")

	/**
	 * @param username for login (admin)
	 * @param password for login (admin123)
	 * @param listener Listener for catch response
	 */
	fun callLogin(username: String, password: String, listener: StatusResponse)
	{
		val url = "${getMainURL()}${api1}login"
		val mParams = hashMapOf<String, Any>(
			"username" to username,
			"password" to password)
		val requestData = RequestData(
			RequestMethod.POST,
			url,
			parameters = mParams,
			dataHolder = hashMapOf("nameService" to "callLogin")
		)
		BaseRequest(requestData, listener).execute()
	}

	/**
	 * @param beaverToken string with beaver token
	 * @param userId is user_id parameter (carlos@rinro.com.mx)
	 * @param projectId is project_id parameter (qbo-1)
	 * @param listener Listener for catch response
	 */
	fun callJWL(
		beaverToken: String,
		userId: String,
		projectId: String,
		listener: StatusResponse)
	{
		val url = "${getMainURL()}${api1}jwt?display_name=$userId&project_id=$projectId"
		val requestData = RequestData(
			RequestMethod.GET,
			url,
			header = hashMapOf("Authorization" to "Bearer $beaverToken"),
			dataHolder = hashMapOf("nameService" to "callJWL")
		)
		BaseRequest(requestData, listener).execute()
	}

	fun callRelatedQuery(listener: StatusResponse)
	{
		val url = "${AutoQLData.domainUrl}/autoql/${api1}query/related-queries?key=${AutoQLData.apiKey}&search=test"
		val header = getAuthorizationJWT()
		header["accept-language"] = SinglentonDrawer.languageCode
		val requestData = RequestData(
			RequestMethod.GET,
			url,
			header,
			dataHolder = hashMapOf("nameService" to "callRelatedQuery")
		)
		BaseRequest(requestData, listener).execute()
	}

	fun callTopics(listener: StatusResponse)
	{
		val url = "${getMainURL()}${api1}topics?" +
			"key=${AutoQLData.apiKey}&project_id=${AutoQLData.projectId}"
		val header = getAuthorizationJWT()
		header["accept-language"] = SinglentonDrawer.languageCode
		header["Integrator-Domain"] = AutoQLData.domainUrl
		val requestData = RequestData(
			RequestMethod.GET,
			url,
			header,
			dataHolder = hashMapOf("nameService" to "callTopics")
		)
		BaseRequest(requestData, listener).execute()
	}
}