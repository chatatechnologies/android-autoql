package chata.can.chata_ai.request.query

import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import java.net.URLEncoder

object QueryRequest
{
	fun callQuery(
		query: String,
		listener: StatusResponse,
		source: String,
		infoHolder: HashMap<String, Any> ?= null)
	{
		var header: HashMap<String, String> ?= null
		val mParams = hashMapOf<String, Any>(
			"text" to query,
			//"debug" to true,
			"test" to true)

		val url = if (AutoQLData.notLoginData())
		{
			mParams["source"] = "data_messenger"
			mParams["user_id"] = "demo"
			mParams["customer_id"] = "demo"
			"$urlStaging${api1}chata/query"
		}
		else
		{
			with(AutoQLData)
			{
				header = getAuthorizationJWT()
				header?.let {
					it["accept-language"] = SinglentonDrawer.languageCode
				}
				mParams["source"] = source
				mParams["translation"] = "include"

				infoHolder?.let {
					it["user_selection"]?.let { mUserSelection ->
						if (mUserSelection is HashMap<*, *>)
						{
							if (source == "data_messenger.validation")
							{
								mParams["user_selection"] = arrayListOf(mUserSelection)
							}
							else
							{
								mParams["user_selection"] = mUserSelection
							}
						}
					}
				}

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
		mData: HashMap<String, Any>)
	{
		val queryId = mData["query_id"] ?: ""
		with(AutoQLData)
		{
			val wordsEncode = URLEncoder.encode(words, "UTF-8").replace("+", " ")
			val url = "$domainUrl/autoql/${api1}query/related-queries?key=$apiKey" +
				"&search=$wordsEncode&scope=narrow&query_id=$queryId"
			mData["nameService"] = "callRelatedQueries"

			val header = getAuthorizationJWT()
			header["accept-language"] = SinglentonDrawer.languageCode

			callStringRequest(
				Request.Method.GET,
				url,
				typeJSON,
				headers = header,
				infoHolder = mData,
				listener = listener)
		}
	}
}