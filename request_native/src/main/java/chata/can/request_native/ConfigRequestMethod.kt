package chata.can.request_native

object ConfigRequestMethod
{
	fun getDoOutput(method: RequestMethod): Boolean
	{
		return when(method)
		{
			RequestMethod.GET -> false
			RequestMethod.POST -> true
		}
	}
}