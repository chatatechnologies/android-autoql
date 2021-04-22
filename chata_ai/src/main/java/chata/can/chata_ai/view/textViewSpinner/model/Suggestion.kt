package chata.can.chata_ai.view.textViewSpinner.model

import org.json.JSONObject

class Suggestion
{
	constructor(
		_text: String,
		_start: Int,
		_end: Int,
		_aSuggestion: ArrayList<Pair<String, String>>? = null)
	{
		text = _text
		start = _start
		end = _end
		aSuggestion = _aSuggestion
	}

	constructor(json: JSONObject)
	{
		text = json.optString("text")
		start = json.getInt("start")
		end = json.getInt("end")

		when
		{
			json.has("suggestion_list") ->
			{
				getList(json, "suggestion_list")
			}
			json.has("suggestions") ->
			{
				getList(json, "suggestions")
			}
		}
	}

	private fun getList(json: JSONObject, key: String)
	{
		json.optJSONArray(key)?.let { jaSuggestion ->
			aSuggestion = ArrayList()
			for (index in 0 until jaSuggestion.length())
			{
				val joSuggestion = jaSuggestion.optJSONObject(index)
				val canonical = joSuggestion.optString("canonical", "")
				var suggestion = joSuggestion.optString("text", "")
				val extra = joSuggestion.optString("value_label", "")
				if (extra.isNotEmpty())
				{
					suggestion = "$suggestion ($extra)"
				}
				aSuggestion?.add(Pair(suggestion, canonical))
			}
		}
	}

	var text = ""
	var start = 0
	var end = 0
	//var aSuggestion: ArrayList<String>? = ArrayList()
	/** Pair<text: String, canonical: String>**/
	var aSuggestion: ArrayList<Pair<String, String>>? = ArrayList()

	var position = 0
}