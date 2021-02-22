package chata.can.request_native

import org.json.JSONArray
import org.json.JSONObject

interface StatusResponse
{
	fun onFailure(jsonObject: JSONObject?)
	fun onSuccess(jsonObject: JSONObject? = null, jsonArray: JSONArray? = null)
}