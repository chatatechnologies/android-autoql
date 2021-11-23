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
				parameters?.let { parameter ->
					val bodyRequest = this.header?.get("Content-Type")?.let {
						ParameterStringBuilder.encodeJSON(parameter)
					} ?: run {
						ParameterStringBuilder.getParamsString(parameter)
					}
					writer.writeBytes(bodyRequest)
				}
				writer.flush()
				writer.close()
			}
		}

	}
}