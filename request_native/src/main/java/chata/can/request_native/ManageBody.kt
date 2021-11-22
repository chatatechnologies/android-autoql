package chata.can.request_native

import java.io.DataOutputStream
import java.net.HttpURLConnection

object ManageBody
{
	fun sendBody(connection: HttpURLConnection, requestData: RequestData)
	{
		requestData.run {
			if (requestMethod == RequestMethod.POST || requestMethod == RequestMethod.PUT)
			{
				val writer = DataOutputStream(connection.outputStream)
				parameters?.let {
					val bodyRequest = if (requestMethod == RequestMethod.POST)
						ParameterStringBuilder.getParamsString(it)
					else ParameterStringBuilder.encodeJSON(it)
					writer.writeBytes(bodyRequest)
				}
				writer.flush()
				writer.close()
			}
		}

	}
}