package chata.can.chata_ai.request.dashboard

import chata.can.chata_ai.pojo.DataMessenger.JWT
import chata.can.chata_ai.pojo.DataMessenger.apiKey
import chata.can.chata_ai.pojo.DataMessenger.domainUrl
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import com.android.volley.Request

object Dashboard
{
	fun getDashboard(
		beaverToken: String,
		listener: StatusResponse)
	{
		val url = "https://backend-staging.chata.io/api/v1/dashboards?key=$apiKey"
		val mAuthorization = hashMapOf(
			"Authorization" to "Bearer $JWT",
			"Integrator-Domain" to domainUrl)
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			listener = listener)
	}
}