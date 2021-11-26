package chata.can.request_native

data class RequestData(
	val requestMethod: RequestMethod,
	val url: String,
	val header: HashMap<String, String> ?= null,
	val parameters: HashMap<String, Any> ?= null,
	//reference for response
	val dataHolder: HashMap<String, Any> ?= null
)
{
	fun getHolder() = dataHolder ?: HashMap()
}