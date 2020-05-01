package chata.can.chata_ai.request.query

import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request

object QueryRequest
{
	fun callQuery(
		query: String,
		listener: StatusResponse,
		infoHolder: HashMap<String, Any> ?= null)
	{
		var header: HashMap<String, String> ?= null

		val mParams = hashMapOf<String, Any>(
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
				mParams["source"] = "data_messenger.user"
//				mParams["source"] = "dashboards.user"
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
}