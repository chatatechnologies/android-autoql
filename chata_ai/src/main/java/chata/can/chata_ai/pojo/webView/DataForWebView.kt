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
	var dataChartBiOnTri = "[]"
	var dataChartBi = "[]"
	var drillX = "[]"
	var drillY = "[]"
	var drillTableY = "[]"
	var isBi = true

	var type = "table"
	var min = -1
	var max = -1

	var stackedFrom = -1
	var stackedTo = -1

	var isReverseX = false
}