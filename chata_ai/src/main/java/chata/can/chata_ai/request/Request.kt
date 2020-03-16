package chata.can.chata_ai.request

import com.android.volley.Request
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Request
{
	fun executeRequest(
		method: Int,
		urlString: String
	)
	{
		val url = URL(urlString)
		val connection = openConnection(url)
		setRequestProperty(connection)

		when(method)
		{
			Method.GET ->
			{
				connection.requestMethod = "GET"
			}
			Method.POST ->
			{
				connection.requestMethod = "POST"
			}
			Method.PUT ->
			{
				connection.requestMethod = "PUT"
			}
			Method.DELETE ->
			{
				connection.requestMethod = "DELETE"
			}
		}
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

	private fun getBody()
	{

	}
}