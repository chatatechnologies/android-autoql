package chata.can.chata_ai.pojo.suggestion

data class RequestSuggestion(
	val query: String,
	val itemText: String,
	val canonical: String,
	val valueLabel: String,
	val start: Int,
	val end: Int)