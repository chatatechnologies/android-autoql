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
			//"debug" to true,
			"test" to true)

		val url = if (DataMessenger.isDemo())
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
				mParams["translation"] = "include"
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
	  listener: StatusResponse,
		mData: HashMap<String, Any> ?= null)
	{
		with(DataMessenger)
		{
			val url = "$domainUrl/autoql/${api1}query/related-queries?key=$apiKey" +
				"&search=$words&scope=narrow"

			val mFinal = if (mData != null)
			{
				mData["nameService"] = "callRelatedQueries"
				mData
			}
			else
			{
				hashMapOf<String, Any>("nameService" to "callRelatedQueries")
			}

			callStringRequest(
				Request.Method.GET,
				url,
				typeJSON,
				headers = getAuthorizationJWT(),
				infoHolder = mFinal,
				listener = listener)
		}
	}
}