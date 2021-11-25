package chata.can.chata_ai.request.query

import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.request_native.*
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

		val url = if (!AutoQLData.wasLoginIn)
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
					it["Content-Type"] = "application/json"
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

		val requestData = RequestData(
			RequestMethod.POST,
			url,
			header,
			mParams,
			infoHolder
		)
		BaseRequest(requestData, listener).execute()
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
			val requestData = RequestData(
				RequestMethod.GET,
				url,
				header,
				dataHolder = mData
			)
			BaseRequest(requestData, listener).execute()
		}
	}
}