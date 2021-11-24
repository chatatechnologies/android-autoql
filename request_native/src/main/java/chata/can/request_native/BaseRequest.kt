package chata.can.request_native

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class BaseRequest(private val requestData: RequestData, private val listener: StatusResponse)
{
	fun execute()
	{
		var pairResponse: PairResponse ?= null

		Executor({
			try {
				val url = URL(requestData.url)

				val connection = url.openConnection() as HttpURLConnection
				connection.requestMethod = "${requestData.requestMethod}"

				requestData.header?.let { RequestProperty.setProperties(connection, it) }

				connection.doOutput = ConfigRequestMethod.getDoOutput(requestData.requestMethod)

				ManageBody.sendBody(connection, requestData)

				pairResponse = BuildBody.getResponse(connection)
			}
			catch (ex: Exception)
			{
				pairResponse = PairResponse(504, "Error")
				ex.printStackTrace()
			}
		},{
			val responseCode = pairResponse?.responseCode ?: 0
			val response = pairResponse?.responseBody ?: ""

			try {
				val jsonObject = JSONObject(response)
				listener.onSuccessResponse(jsonObject)
			} catch (ex: Exception)
			{
				try {
					val jsonArray = JSONArray(response)
					listener.onSuccessResponse(jsonArray = jsonArray)
				} catch (ex: Exception)
				{
					with(JSONObject())
					{
						put("RESPONSE", response)
						addDataHolder(requestData.getHolder())
						listener.onSuccessResponse(this)
					}
				}
			}

//			val json = JSONObject().apply {
//				put("CODE", responseCode)
//				put("RESPONSE", pairResponse?.responseBody ?: "")
//			}
//			//region POST response
//			requestData.dataHolder?.let {
//				for ((key, value) in it)
//					json.put(key, value)
//			}
//			//endregion
//			if (responseCode > 299)
//				listener.onFailureResponse(json)
//			else
//				listener.onSuccessResponse(json)
		}).execute()
	}

	private fun JSONObject.addDataHolder(map: HashMap<String, Any>)
	{
		for ((key, value) in map)
			put(key, value)
	}
}