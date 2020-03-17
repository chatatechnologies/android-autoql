package chata.can.chata_ai.request

class RequestData(
	val method: Int,
	val urlString: String)
{
	var contentType = ""
	var mHeaders: HashMap<String, String>? = null
	var mParams: HashMap<String, String>? = null
	var mParamsTypes: HashMap<String, Any>? = null

	/** When contentType not is JSON APPLICATION **/
	fun getParams(): HashMap<String, String>?
	{
		return if (contentType.isEmpty())
		{
			mParams ?: HashMap()
		}
		else null
	}

	fun getBodyContentType()
	{
		"application/x-www-form-urlencoded; charset=" + Constant.DEFAULT_PARAMS_ENCODING
	}

	fun getHeaders() = mHeaders ?: HashMap()

	fun getBody()
	{
		if (contentType.isEmpty())
		{
			//to build the param
		}
		else
		{
			val params = mParams ?: mParamsTypes ?: HashMap()
			val map = params.map {
				(key, value) ->
				key to value
			}.toMap()
			//JSONObject(map).toString().toByteArray()
		}
	}
}