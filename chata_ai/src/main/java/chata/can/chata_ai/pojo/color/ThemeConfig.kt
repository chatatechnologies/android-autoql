package chata.can.chata_ai.pojo.color

data class ThemeConfig(
	var theme: String = "dark",
	var accentColor: String = "",
	val fontFamily: String = "sans-serif",
	val chartColors: ArrayList<String> = arrayListOf("#355C7D", "#6C5B7B", "#C06C84", "#F67280", "#F8B195")
)