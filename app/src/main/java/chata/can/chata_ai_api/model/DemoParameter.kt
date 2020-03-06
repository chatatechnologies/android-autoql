package chata.can.chata_ai_api.model

data class DemoParameter(
	val label: String,
	val type: TypeParameter,
	val value: String = "",
	val options: ArrayList<String> = ArrayList())