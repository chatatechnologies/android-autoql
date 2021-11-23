package chata.can.request_native

import org.json.JSONObject

interface StatusResponse
{
	fun onFailureResponse(jsonObject: JSONObject)
	fun onSuccessResponse(jsonObject: JSONObject)
}