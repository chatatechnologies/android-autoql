package chata.can.chata_ai.request.authentication

import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.urlChataIO
import com.android.volley.Request

object Authentication
{
	fun getAuthorizationJWT() = hashMapOf("Authorization" to "Bearer ${DataMessenger.JWT}")

	/**
	 * @param username for login (admin)
	 * @param password for login (admin123)
	 * @param listener Listener for catch response
	 */
	fun callLogin(username: String, password: String, listener: StatusResponse)
	{
		val url = "$urlChataIO${api1}login"

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
		val url = "$urlChataIO${api1}jwt?display_name=$userId&project_id=$projectId"
		val mAuthorization = hashMapOf("Authorization" to "Bearer $beaverToken")
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			infoHolder = hashMapOf("nameService" to "callJWL"),
			listener = listener)
	}
}