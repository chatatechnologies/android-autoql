package chata.can.chata_ai.request.dashboard

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData.JWT
import chata.can.chata_ai.pojo.autoQL.AutoQLData.apiKey
import chata.can.chata_ai.pojo.autoQL.AutoQLData.domainUrl
import chata.can.chata_ai.pojo.autoQL.AutoQLData.projectId
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.urlChataIO
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