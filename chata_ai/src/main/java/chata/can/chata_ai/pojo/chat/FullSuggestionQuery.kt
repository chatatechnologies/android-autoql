package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.extension.hasInList
import chata.can.chata_ai.extension.optJSONArrayInList
import chata.can.chata_ai.extension.optStringInList
import com.carlos.buruel.textviewspinner.model.Suggestion
import org.json.JSONObject

class FullSuggestionQuery(json: JSONObject): SimpleQuery(json)
{
	var initQuery = ""
	val aSuggestion = ArrayList<Suggestion>()
	val mapSuggestion = LinkedHashMap<String, ArrayList<String>>()
	init {
		initQuery = json.optStringInList(arrayListOf("query", "text"))
		if (json.hasInList(arrayListOf("full_suggestion","replacements")))
		{
			val jaSuggestion = json.optJSONArrayInList(arrayListOf("full_suggestion", "replacements"))
			val aSuggestionsTmp = ArrayList<Suggestion>()
			for (index in 0 until jaSuggestion.length())
			{
				val jsonItem = jaSuggestion.getJSONObject(index)
				aSuggestionsTmp.add(Suggestion(jsonItem))
			}
			val aWords = initQuery.split(" ")
			for (word in aWords)
			{
				val start = initQuery.indexOf(word)
				val end = start + word.length
				val suggestion = Suggestion(word, start, end)
				aSuggestionsTmp.find { it.start == suggestion.start && it.end == suggestion.end }?.let {
					suggestion.aSuggestion = ArrayList()
					it.aSuggestion?.let {
						itSuggestion ->
						suggestion.aSuggestion?.addAll(itSuggestion)
						suggestion.aSuggestion?.add("${suggestion.text} (Original term)")
					}
				}
				aSuggestion.add(suggestion)
			}

//			for (index in 0 until jaSuggestion.length())
//			{
//				val jsonItem = jaSuggestion.getJSONObject(index)
//				val start = jsonItem.getInt("start")
//				val end = jsonItem.getInt("end")
//
//				val key = initQuery.substring(start, end)
//				if (jsonItem.has("suggestion"))
//				{
//					val suggestion = jsonItem.getString("suggestion")
//					mapSuggestion[key] = ArrayList()
//					mapSuggestion[key]?.add(suggestion)
//				}
//				else if (jsonItem.hasInList(arrayListOf("suggestion_list", "suggestions")))
//				{
//					mapSuggestion[key] = ArrayList()
//					val aJsonList = jsonItem.optJSONArrayInList(arrayListOf("suggestion_list", "suggestions"))
//					for (iList in 0 until aJsonList.length())
//					{
//						val oJson = aJsonList.getJSONObject(iList)
//						var suggestion = oJson.optString("text") ?: ""
//						val extra = oJson.optString("value_label") ?: ""
//						if (extra.isNotEmpty())
//						{
//							suggestion = "$suggestion ($extra)"
//						}
//						mapSuggestion[key]?.add(suggestion)
//					}
//				}
//			}
		}
	}
}