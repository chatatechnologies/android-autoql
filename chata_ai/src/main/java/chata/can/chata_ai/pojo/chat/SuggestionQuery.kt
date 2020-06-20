package chata.can.chata_ai.pojo.chat

import org.json.JSONArray
import org.json.JSONObject

class SuggestionQuery(jaItems: JSONArray): SimpleQuery(JSONObject())
{
	val aItems = ArrayList<String>()
	init {
		for (index in 0 until jaItems.length())
		{
			val item = jaItems.opt(index).toString()
			aItems.add(item)
		}
	}
}