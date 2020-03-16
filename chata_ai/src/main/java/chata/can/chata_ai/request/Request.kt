package chata.can.chata_ai.request

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object Request
{
	fun executeRequest(
		method: Int,
		urlString: String
	)
	{
		val url = URL(urlString)
		val connection = openConnection(url)
		setRequestProperty(connection)

		connection.requestMethod = when(method)
		{
			Method.GET -> "GET"
			Method.POST -> "POST"
			Method.PUT -> "PUT"
			Method.DELETE -> "DELETE"
			else -> "POST"
		}

		Thread(Runnable {
			addBody(connection)
			val responseCode = connection.responseCode

			val inputStreamReader = InputStreamReader(connection.inputStream)
			val reader = BufferedReader(inputStreamReader)

			val out = StringBuilder()

			var line: String?
			while (reader.readLine().also { line = it } != null)
			{
				out.append(line)
			}

			println(out.toString())
		}).start()
	}

	@Throws(IOException::class)
	private fun createConnection(url: URL): HttpURLConnection
	{
		val connection = url.openConnection() as HttpURLConnection
		connection.instanceFollowRedirects = true
		return connection
	}

	@Throws(IOException::class)
	private fun openConnection(url: URL): HttpURLConnection
	{
		val connection = createConnection(url)
		connection.connectTimeout = 5000
		connection.readTimeout = 5000
		connection.useCaches = false
		connection.doInput = true
		return connection
	}

	private fun setRequestProperty(connection: HttpURLConnection)
	{
		val map = hashMapOf("Authorization" to "Basic Y2hhdGE6Y2hhdGE=")

		for (headerName in map.keys)
		{
			connection.setRequestProperty(headerName, map[headerName])
		}
	}

	private val HEADER_CONTENT_TYPE = "Content-Type"
	private val DEFAULT_PARAMS_ENCODING = "UTF-8"
	@Throws(IOException::class)
	private fun addBody(connection: HttpURLConnection)
	{
		val body = buildParams(hashMapOf())
		connection.doOutput = true

		if (!connection.requestProperties.containsKey(HEADER_CONTENT_TYPE))
		{
			connection.setRequestProperty(
				HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=$DEFAULT_PARAMS_ENCODING")
		}
		val out = DataOutputStream(connection.outputStream)
		out.write(body)
		out.close()
	}

	fun buildParams(params: HashMap<String, String>): ByteArray
	{
		val DEFAULT_PARAMS_ENCODING = "UTF-8"
		val encodedParams = StringBuilder()
		try
		{
			for (entry in params.entries)
			{
				encodedParams.append(URLEncoder.encode(entry.key, DEFAULT_PARAMS_ENCODING))
				encodedParams.append('=')
				encodedParams.append(URLEncoder.encode(entry.value, DEFAULT_PARAMS_ENCODING))
				encodedParams.append('&')
			}
			return encodedParams.toString().toByteArray(charset(DEFAULT_PARAMS_ENCODING))
		}
		catch (uee: UnsupportedEncodingException)
		{
			throw RuntimeException("Encoding not supported: $DEFAULT_PARAMS_ENCODING", uee)
		}
	}
}