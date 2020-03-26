package chata.can.chata_ai.pojo.chat

import org.json.JSONObject

open class SimpleQuery(json: JSONObject)
{
	val query = json.optString("query")
}