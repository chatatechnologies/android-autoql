package com.carlos.buruel.textviewspinner.model

class Suggestion(
	var text: String,
	var start: Int,
	var end: Int,
	val aSuggestion: ArrayList<String>?)
{
	var position = 0
}