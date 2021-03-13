package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.extension.optStringInList
import org.json.JSONObject

open class SimpleQuery(json: JSONObject)
{
	val query = json.optStringInList(arrayListOf("query", "text"))
	var typeView = 0
	var isSession = false
	var visibleTop = true
}