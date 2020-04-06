package chata.can.chata_ai.pojo.color

data class ThemeConfig(
	val theme: String,
	val accentColor: String,
	val fontFamily: String,
	val chartColors: ArrayList<String> = arrayListOf("#26A7E9", "#A5CD39", "#DD6A6A", "#FFA700", "#00C1B2")
)