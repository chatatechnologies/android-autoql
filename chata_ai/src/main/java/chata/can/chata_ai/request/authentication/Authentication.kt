package chata.can.chata_ai.request.authentication

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.getMainURL
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import com.android.volley.Request

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

		val mParams = hashMapOf(
			"username" to username,
			"password" to password)

		callStringRequest(
			Request.Method.POST,
			url,
			parameters = mParams,
			infoHolder = hashMapOf("nameService" to "callLogin"),
			listener = listener)
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
		val mAuthorization = hashMapOf("Authorization" to "Bearer $beaverToken")
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			infoHolder = hashMapOf("nameService" to "callJWL"),
			listener = listener)
	}

	fun callRelatedQuery(listener: StatusResponse)
	{
		val url = "${AutoQLData.domainUrl}/autoql/${api1}query/related-queries?key=${AutoQLData.apiKey}&search=test"
		val header = getAuthorizationJWT()
		header["accept-language"] = SinglentonDrawer.languageCode

		callStringRequest(
			Request.Method.GET,
			url,
			headers = header,
			infoHolder = hashMapOf("nameService" to "callRelatedQuery"),
			listener = listener
		)
	}

	fun callTopics(listener: StatusResponse)
	{
		val url = "${getMainURL()}${api1}topics?" +
			"key=${AutoQLData.apiKey}&project_id=${AutoQLData.projectId}"
		val header = getAuthorizationJWT()
		header["accept-language"] = SinglentonDrawer.languageCode
		header["Integrator-Domain"] = AutoQLData.domainUrl

		callStringRequest(
			Request.Method.GET,
			url,
			headers = header,
			infoHolder = hashMapOf("nameService" to "callTopics"),
			listener = listener
		)
	}
}