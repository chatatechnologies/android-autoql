package chata.can.request_native

import chata.can.chata_ai.Executor
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class BaseRequest
{
	fun getBaseRequest(requestData: RequestData)
	{
		var responseCode = 0
		val response = StringBuilder()

		Executor({
			try {
				//			get url
//			val sURL = "https://carlos-buruel-ortiz.000webhostapp.com/mensaje.json"
//			post url
//			val sURL = "https://backend-staging.chata.io/api/v1/login"
				val url = URL(requestData.url)

				val connection = url.openConnection() as HttpURLConnection
//			connection.requestMethod = "${RequestMethod.GET}"
//			connection.requestMethod = "${RequestMethod.POST}"
				connection.requestMethod = "GET"



				connection.doOutput = false

//				val writer = DataOutputStream(connection.outputStream)
//				requestData.parameters?.let {
//					writer.writeBytes(ParameterStringBuilder.getParamsString(it))
//				}
//				writer.flush()
//				writer.close()


				connection.connect()

				responseCode = connection.responseCode

				val reader = BufferedReader(
					InputStreamReader(
						if (responseCode > 299)
							connection.errorStream
						else
							connection.inputStream)
				)

				reader.forEachLine { line ->
					response.append(line)
				}

				connection.disconnect()
			} catch (ex: Exception)
			{
				ex.printStackTrace()
			}
		},{
			val json = JSONObject()
			json.put("CODE", responseCode)
			json.put("RESPONSE", response)
			println(json)
		}).execute()
	}
}