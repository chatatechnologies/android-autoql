package chata.can.request_native

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

object BuildBody
{
	/**
	 *
	 */
	fun getResponse(connection: HttpURLConnection): PairResponse
	{
		var responseCode = 0
		val responseBody = StringBuilder()
		responseCode = connection.responseCode

		val bufferedReader = BufferedReader(
			InputStreamReader(
				if (responseCode > 299)
					connection.errorStream
				else
					connection.inputStream)
		)

		bufferedReader.forEachLine { line ->
			responseBody.append(line)
		}
		connection.disconnect()

		return PairResponse(responseCode, "$responseBody")
	}
}