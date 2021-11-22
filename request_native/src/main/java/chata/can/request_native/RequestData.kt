package chata.can.request_native

data class RequestData(
	val requestMethod: RequestMethod,
	val url: String,
	val header: HashMap<String, String> ?= null,
	val parameters: HashMap<String, Any> ?= null
)