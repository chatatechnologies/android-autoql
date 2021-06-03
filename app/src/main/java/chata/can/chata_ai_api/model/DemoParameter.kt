package chata.can.chata_ai_api.model

data class DemoParameter(
	val label: String,
	val type: TypeParameter,
	val value: String = "false",
	val options: ArrayList<Segment> = ArrayList(),
	val colors: ArrayList<Color> = ArrayList(),
	val hint: String = "",
	val labelId: Int = 0,
	val idView: Int = 0,
	val typeInput: TypeInput = TypeInput.TEXT,
	val isVisible: Boolean = true)