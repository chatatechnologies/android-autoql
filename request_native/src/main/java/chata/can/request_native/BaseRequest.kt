package chata.can.request_native

import chata.can.chata_ai.Executor
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class BaseRequest
{
	fun getBaseRequest()
	{
		Executor({
			//get url
//			val sURL = "https://carlos-buruel-ortiz.000webhostapp.com/mensaje.json"
			//post url
			val sURL = "https://backend-staging.chata.io/api/v1/login"
			val url = URL(sURL)

			val parameters = HashMap<String, Any>()
			parameters["username"] = "admin"
			parameters["password"] = "admin123"

			val connection = url.openConnection() as HttpURLConnection
//			connection.requestMethod = "${RequestMethod.GET}"
			connection.requestMethod = "${RequestMethod.POST}"
			connection.doOutput = true

			val writer = DataOutputStream(connection.outputStream)
			writer.writeBytes(ParameterStringBuilder.getParamsString(parameters))
			writer.flush()

			val reader =  BufferedReader(InputStreamReader(connection.inputStream))

			val sContent = StringBuilder()
			reader.forEachLine { line ->
				sContent.append(line)
				println("line -> $line")
			}

			writer.close()
			sContent.toString()
		},{
			println("Finish")
		}).execute()
	}
}