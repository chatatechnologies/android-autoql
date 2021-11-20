package chata.can.request_native

import chata.can.chata_ai.Executor
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object BaseRequest
{
	fun getBaseRequest()
	{
		Executor({
			val sUrl = "https://carlos-buruel-ortiz.000webhostapp.com/mensaje.json"
			val url = URL(sUrl)

			val connection = url.openConnection() as HttpURLConnection
			connection.requestMethod = "GET"
			connection.doOutput = true

			val writer = OutputStreamWriter(connection.outputStream)
			writer.flush()

			val reader =  BufferedReader(InputStreamReader(connection.inputStream))

			reader.forEachLine { line ->
				println("line -> $line")
			}

			writer.close()
		},{
			println("Finish")
		}).execute()
	}
}