package chata.can.chata_ai.request.dashboard

import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import com.android.volley.Request

object Dashboard
{
	fun getDashboard(
		beaverToken: String,
		listener: StatusResponse)
	{
		val url = "https://backend-staging.chata.io/api/v1/dashboards?key=AIzaSyD2J8pfYPSI8b--HfxliLYB8V5AehPv0ys"
		val mAuthorization = hashMapOf("Authorization" to "Bearer $beaverToken")
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			listener = listener)
	}
}