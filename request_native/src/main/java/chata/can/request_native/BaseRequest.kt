package chata.can.request_native

import chata.can.chata_ai.Executor
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
			} catch (ex: Exception)
			{
				ex.printStackTrace()
			}
		},{
			val responseCode = pairResponse?.responseCode ?: 0
			val json = JSONObject().apply {
				put("CODE", responseCode)
				put("RESPONSE", pairResponse?.responseBody ?: "")
			}
			//region POST response
			requestData.dataHolder?.let {
				for ((key, value) in it)
					json.put(key, value)
			}
			//endregion
			if (responseCode > 299)
				listener.onSuccess(json)
			else
				listener.onFailure(json)
		}).execute()
	}
}