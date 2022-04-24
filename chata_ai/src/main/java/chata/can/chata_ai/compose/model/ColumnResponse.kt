package chata.can.chata_ai.compose.model

data class ColumnResponse(
	val display_name: String,
	val groupable: Boolean,
	val is_visible: Boolean,
	val multi_series: Boolean,
	val name: String,
	val type: String
)
