package chata.can.request_native

import chata.can.chata_ai.Executor
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class BaseRequest
{
	fun getBaseRequest(requestData: RequestData)
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
			val json = JSONObject()
			json.put("CODE", pairResponse?.responseCode ?: 0)
			json.put("RESPONSE", pairResponse?.responseBody ?: "")
			println(json)
		}).execute()
	}
}