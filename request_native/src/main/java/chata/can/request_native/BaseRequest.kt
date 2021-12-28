package chata.can.request_native

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class BaseRequest(
	private val requestData: RequestData,
  private val listener: StatusResponse,
	private val reference: String = "query")
{
	fun execute()
	{
		var pairResponse: PairResponse ?= null

		Executor({
			try {
				val url = URL(requestData.url)

				val connection = url.openConnection() as HttpURLConnection
				connection.requestMethod = "${requestData.requestMethod}"

				connection.readTimeout = 60000//10 seconds
				connection.connectTimeout = 6000
				connection.setChunkedStreamingMode(0)

				requestData.header?.let { RequestProperty.setProperties(connection, it) }

				connection.doOutput = ConfigRequestMethod.getDoOutput(requestData.requestMethod)

				ManageBody.sendBody(connection, requestData, reference)

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

			val json = try {
				JSONObject(response).apply {
					addDataHolder(requestData.getHolder())
				}
			} catch (ex: Exception)
			{
				try {
					val jsonArray = JSONArray(response)
					listener.onSuccessResponse(jsonArray = jsonArray)
					JSONObject().put("array", jsonArray)
				} catch (ex: Exception)
				{
					JSONObject().apply {
						put("RESPONSE", response)
						addDataHolder(requestData.getHolder())
					}
				}
			}

			json.addDataHolder(requestData.getHolder())
			if (responseCode > 299)
			{
				listener.onFailureResponse(json.put("CODE", responseCode))
			}
			else
				listener.onSuccessResponse(json)
		}).execute()
	}

	private fun JSONObject.addDataHolder(map: HashMap<String, Any>)
	{
		for ((key, value) in map)
			put(key, value)
	}
}