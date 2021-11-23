package chata.can.chata_ai.request.dashboard

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData.JWT
import chata.can.chata_ai.pojo.autoQL.AutoQLData.apiKey
import chata.can.chata_ai.pojo.autoQL.AutoQLData.domainUrl
import chata.can.chata_ai.pojo.autoQL.AutoQLData.projectId
import chata.can.chata_ai.pojo.getMainURL
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import com.android.volley.Request
import org.json.JSONObject

object Dashboard
{
	fun getDashboard(listener: chata.can.request_native.StatusResponse)
	{
		val url = "${getMainURL()}${api1}dashboards?key=$apiKey&project_id=$projectId"
		val requestData = RequestData(
			RequestMethod.GET,
			url,
			header = hashMapOf("Authorization" to "Bearer $JWT", "Integrator-Domain" to domainUrl),
			dataHolder = hashMapOf("nameService" to "getDashboard")
		)
		BaseRequest(requestData, listener).execute()
//		callStringRequest(
//			Request.Method.GET,
//			url,
//			headers = mAuthorization,
//			infoHolder = hashMapOf("nameService" to "getDashboard"),
//			listener = listener)
	}
}