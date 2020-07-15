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
						val aTmp = itSuggestion[0].split(" (")
						updateQuery(suggestion, aTmp[0])
					}
				}
				aSuggestion.add(suggestion)
			}
		}
	}

	private fun updateQuery(suggestion: Suggestion, newText: String)
	{
		val oldText = suggestion.text
		initQuery = initQuery.replace(oldText, newText)
		suggestion.text = newText
		suggestion.start = initQuery.indexOf(newText)
		suggestion.end = suggestion.start + newText.length
	}
}