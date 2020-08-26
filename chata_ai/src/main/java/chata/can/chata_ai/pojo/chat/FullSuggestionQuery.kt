package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.extension.hasInList
import chata.can.chata_ai.extension.optJSONArrayInList
import chata.can.chata_ai.extension.optStringInList
import chata.can.chata_ai.view.textViewSpinner.model.Suggestion
import org.json.JSONObject

class FullSuggestionQuery(json: JSONObject): SimpleQuery(json)
{
	private var initQuery = ""
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

			if (aSuggestionsTmp.isNotEmpty())
			{
				//Order suggestion
				aSuggestionsTmp.sortBy { it.start }
				val aWords = ArrayList<String>()
				var header = 0
				var countSuggestion = 0
				do {
					if (countSuggestion < aSuggestionsTmp.size)
					{
						val suggestion = aSuggestionsTmp[countSuggestion]
						header = if (header != suggestion.start)
						{
							val word = initQuery.substring(header, suggestion.start - 1)
							aWords.add(word)
							suggestion.start
						}
						else
						{
							val word = initQuery.substring(suggestion.start, suggestion.end)
							aWords.add(word)
							countSuggestion++
							suggestion.end
						}
					}
					else
					{
						val word = initQuery.substring(header, initQuery.length)
						aWords.add(word)
						header = initQuery.length
					}
				} while (header < initQuery.length)

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