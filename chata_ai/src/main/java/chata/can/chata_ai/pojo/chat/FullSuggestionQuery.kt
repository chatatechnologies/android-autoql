package chata.can.chata_ai.pojo.chat

import org.json.JSONObject

class FullSuggestionQuery(json: JSONObject): SimpleQuery(json)
{
	var initQuery = ""
	val mapSuggestion = HashMap<String, ArrayList<String>>()
	init {
		initQuery = json.optString("query")
		if (json.has("full_suggestion"))
		{
			val jaSuggestion = json.getJSONArray("full_suggestion")
			for (index in 0 until jaSuggestion.length())
			{
				val jsonItem = jaSuggestion.getJSONObject(index)
				val start = jsonItem.getInt("start")
				val end = jsonItem.getInt("end")

				val key = initQuery.substring(start, end)
				if (jsonItem.has("suggestion") )
				{
					val suggestion = jsonItem.getString("suggestion")
					mapSuggestion[key] = ArrayList()
					mapSuggestion[key]?.add(suggestion)
				}
				else if (jsonItem.has("suggestion_list"))
				{
					mapSuggestion[key] = ArrayList()
					val aJsonList = jsonItem.getJSONArray("suggestion_list")
					for (iList in 0 until aJsonList.length())
					{
						val oJson = aJsonList.getJSONObject(iList)
						var suggestion = if (oJson.has("text")) oJson.getString("text") else ""
						val extra = if (oJson.has("value_label")) oJson.getString("value_label") else ""
						if (extra.isNotEmpty())
						{
							suggestion = "$suggestion ($extra)"
						}
						mapSuggestion[key]?.add(suggestion)
					}
				}
			}
		}
	}
}