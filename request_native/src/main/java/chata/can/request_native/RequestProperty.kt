package chata.can.request_native

import java.net.HttpURLConnection

object RequestProperty
{
	fun setProperties(connection: HttpURLConnection, header: HashMap<String, String>)
	{
		for ((key, value) in header)
		{
			connection.setRequestProperty(key, value)
		}
	}
}