package chata.can.chata_ai.request.query

import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request

object QueryRequest
{
	fun callQuery(
		query: String,
		listener: StatusResponse,
		source: String,
		infoHolder: HashMap<String, Any> ?= null)
	{
		var header: HashMap<String, String> ?= null

		val mParams = hashMapOf(
			"text" to query,
			"debug" to true,
			"test" to true)

		val url = if (DataMessenger.domainUrl.isEmpty())
		{
			mParams["source"] = "data_messenger"
			mParams["user_id"] = "demo"
			mParams["customer_id"] = "demo"
			"$urlStaging${api1}chata/query"
		}
		else
		{
			with(DataMessenger)
			{
				header = getAuthorizationJWT()
				mParams["source"] = "$source.user"
				"$domainUrl/autoql/${api1}query?key=$apiKey"
			}
		}

		callStringRequest(
			Request.Method.POST,
			url,
			typeJSON,
			headers = header,
			parametersAny = mParams,
			infoHolder = infoHolder,
			listener = listener)
	}

	fun callRelatedQueries(
		words: String,
	  listener: StatusResponse)
	{
		with(DataMessenger)
		{
			val url = "$domainUrl/autoql/${api1}query/related-queries?key=$apiKey" +
				"&search=$words&scope=narrow"

			callStringRequest(
				Request.Method.GET,
				url,
				typeJSON,
				headers = getAuthorizationJWT(),
				infoHolder = hashMapOf("nameService" to "callRelatedQueries"),
				listener = listener)
		}
	}
}