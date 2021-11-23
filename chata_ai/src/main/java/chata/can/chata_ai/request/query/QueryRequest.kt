package chata.can.chata_ai.request.query

import chata.can.chata_ai.Executor
import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import com.android.volley.Request
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
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

		//region request post for query
		Executor({
			val oURL = URL(url)
			val connection = oURL.openConnection() as HttpURLConnection
			connection.requestMethod = "POST"

			connection.setRequestProperty("Authorization", "Bearer ${AutoQLData.JWT}")
			connection.setRequestProperty("accept-language", "es-Us")
			connection.setRequestProperty("Content-Type", "application/json")

			connection.doOutput = true

			val writer = DataOutputStream(connection.outputStream)
			writer.writeBytes("{\"text\": \"Total revenue by month in 2019\"}")
			writer.flush()
			writer.close()

			val responseCode = connection.responseCode

			val bufferedReader = BufferedReader(
				InputStreamReader(
					if (responseCode > 299)
						connection.errorStream
					else
						connection.inputStream)
			)

			val responseBody = StringBuilder()
			bufferedReader.forEachLine { line ->
				responseBody.append(line)
			}
			connection.disconnect()
		},{

		}).execute()
		//endregion

//		val requestData = RequestData(
//			RequestMethod.POST,
//			url,
//			header,
//			mParams,
//			infoHolder
//		)
//		BaseRequest(requestData, object :chata.can.request_native.StatusResponse
//		{
//			override fun onFailureResponse(jsonObject: JSONObject)
//			{
//				jsonObject.toString()
//			}
//
//			override fun onSuccessResponse(jsonObject: JSONObject)
//			{
//				jsonObject.toString()
//			}
//		}).execute()
//		callStringRequest(
//			Request.Method.POST,
//			url,
//			typeJSON,
//			headers = header,
//			parametersAny = mParams,
//			infoHolder = infoHolder,
//			listener = listener)
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