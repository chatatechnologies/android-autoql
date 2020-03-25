package chata.can.chata_ai.request.query

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlBase
import chata.can.chata_ai.pojo.urlStaging
import com.android.volley.Request

object QueryRequest
{
	fun callSafetyNet(query: String, listener: StatusResponse)
	{
		val url = "$urlBase${api1}safetynet?q=$query&" +
			"projectId=1&user_id=demo&customer_id=demo"
		callStringRequest(Request.Method.GET,url, typeJSON, listener = listener)
	}

	fun callQuery(
		query: String,
		listener: StatusResponse,
		infoHolder: HashMap<String, Any> ?= null)
	{
		val url = "$urlStaging${api1}chata/query"
		val mParams = hashMapOf<String, Any>(
			"text" to query,
			"source" to "data_messenger",
			"debug" to true,
			"test" to true,
			"user_id" to "demo",
			"customer_id" to "demo")

		callStringRequest(
			Request.Method.POST,
			url,
			typeJSON,
			parametersAny = mParams,
			infoHolder = infoHolder,
			listener = listener)
	}
}