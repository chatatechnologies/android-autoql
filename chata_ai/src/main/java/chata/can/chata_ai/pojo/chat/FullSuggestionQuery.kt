package chata.can.chata_ai.pojo.chat

import org.json.JSONObject

class FullSuggestionQuery(json: JSONObject): SimpleQuery(json)
{
	var initQuery = ""
	val mapSuggestion = HashMap<String, ArrayList<String>>()
	init {
//		initQuery = json.optString("query")
		initQuery = json.optString("text")
//		if (json.has("full_suggestion"))
		if (json.has("replacements"))
		{
//			val jaSuggestion = json.getJSONArray("full_suggestion")
			val jaSuggestion = json.getJSONArray("replacements")
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
//				else if (jsonItem.has("suggestion_list"))
				else if (jsonItem.has("suggestions"))
				{
					mapSuggestion[key] = ArrayList()
					val aJsonList = jsonItem.getJSONArray("suggestions")
					for (iList in 0 until aJsonList.length())
					{
						val oJson = aJsonList.getJSONObject(iList)
						var suggestion = oJson.optString("text") ?: ""
						val extra = oJson.optString("value_label") ?: ""
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