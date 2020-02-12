package chata.can.chata_ai.pojo.request

import org.json.JSONArray
import org.json.JSONObject

interface StatusResponse
{
    fun onFailure(jsonObject: JSONObject?)
    fun onSuccess(jsonObject: JSONObject? = null, jsonArray: JSONArray? = null)
}