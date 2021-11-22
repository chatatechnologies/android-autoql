package chata.can.request_native

import chata.can.chata_ai.Executor
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class BaseRequest
{
	fun getBaseRequest(requestData: RequestData)
	{
		var pairResponse: PairResponse ?= null

		Executor({
			try {
				//			get url
//			val sURL = "https://carlos-buruel-ortiz.000webhostapp.com/mensaje.json"
//			post url
//			val sURL = "https://backend-staging.chata.io/api/v1/login"
				val url = URL(requestData.url)

				val connection = url.openConnection() as HttpURLConnection
				connection.requestMethod = "${requestData.requestType}"

				requestData.header?.let { RequestProperty.setProperties(connection, it) }

				connection.doOutput = ConfigRequestMethod.getDoOutput(requestData.requestType)

				if (requestData.requestType == RequestMethod.POST)
				{
					val writer = DataOutputStream(connection.outputStream)
					requestData.parameters?.let {
						writer.writeBytes(ParameterStringBuilder.getParamsString(it))
					}
					writer.flush()
					writer.close()
				}

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