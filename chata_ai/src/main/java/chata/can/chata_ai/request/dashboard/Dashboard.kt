package chata.can.chata_ai.request.dashboard

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot.JWT
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot.apiKey
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot.domainUrl
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.urlChataIO
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot.projectId
import com.android.volley.Request

object Dashboard
{
	fun getDashboard(listener: StatusResponse)
	{
		val url = "${urlChataIO}${api1}dashboards?key=$apiKey&project_id=$projectId"
		val mAuthorization = hashMapOf(
			"Authorization" to "Bearer $JWT",
			"Integrator-Domain" to domainUrl)
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			infoHolder = hashMapOf("nameService" to "getDashboard"),
			listener = listener)
	}
}