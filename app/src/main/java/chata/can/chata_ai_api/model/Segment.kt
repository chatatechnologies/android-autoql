package chata.can.chata_ai_api.model

data class Segment(
	val idView: Int,
	val text: String,
	val isActive: Boolean = false,
	val idResource: Int = 0)