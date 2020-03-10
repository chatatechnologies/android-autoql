package chata.can.chata_ai.request

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

object RequestApi
{
	fun callStringRequest(pathUrl: String)
	{
		try
		{
			val url = URL(pathUrl)
			val connection = url.openConnection() as HttpURLConnection
			connection.connectTimeout = 5000
			connection.readTimeout = 5000
			connection.setRequestProperty("Content-Type", "application/json")
			connection.doOutput = true
			connection.requestMethod = "POST"

			val jsonTest = JSONObject()
			jsonTest.put("scope" , "read")
			jsonTest.put("grant_type", "password")
			jsonTest.put("username", "carlos@stx.com.mx")
			jsonTest.put("password", "\$Chata124")

			Thread(Runnable {
				val output = OutputStreamWriter(connection.outputStream)
				output.write(jsonTest.toString())
				output.close()

				val inputStreamReader = InputStreamReader(connection.inputStream)
				connection.responseCode

				//val allText = connection.inputStream.bufferedReader().use(BufferedReader::readText)

				val reader = BufferedReader(inputStreamReader)
				val sOut = ""
			}).start()
		}
		catch(ex: MalformedURLException)
		{
			Log.e("ERROR", "MalformedURLException: ${ex.message ?: ""}")
		}
		catch(ex: ProtocolException)
		{
			Log.e("ERROR", "ProtocolException: ${ex.message ?: ""}")
		}
		catch (ex: IOException)
		{
			Log.e("ERROR", "IOException: ${ex.message ?: ""}")
		}
		catch (ex: Exception)
		{
			Log.e("ERROR", "Exception: ${ex.message ?: ""}")
		}
		finally
		{

		}
	}
}