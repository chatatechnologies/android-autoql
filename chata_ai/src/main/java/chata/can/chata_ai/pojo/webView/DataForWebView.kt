package chata.can.chata_ai.pojo.webView

class DataForWebView(
	var table: String = "",
	var rowsTable: Int = 1,
	var datePivot: String = "",
	var rowsPivot: Int = 1)
{
	var catX = "[]"
	var catY = "[]"
	var catYS = "[]"
	var dataChartBi = "[]"
	var drillX = "[]"
	var drillY = "[]"
	var isBi = true

	var type = "table"
	var xAxis = ""
	var yAxis = ""

	val aXAxis = ArrayList<String>()
	val aYAxis = ArrayList<String>()
}