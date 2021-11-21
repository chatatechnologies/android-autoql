package chata.can.request_native

data class RequestData(
	val requestType: RequestMethod,
	val url: String,
	val parameters: HashMap<String, Any> ?= null
)