package chata.can.chata_ai.pojo.webView

class DataD3
{
	var type = "table"
	var table = ""
	var data = ""

	var catX = "[]"
	var catY = "[]"

	var drillX = ""

	var drillY = ""
	var drillTableY = "[]"

	var datePivot: String = ""

	var xAxis = ""
	var yAxis = ""

	var min = -1
	var max = -1

	var isBi = true
	var isColumn = false
	var isDashboard = false
	//region flag control
	var updateTable = false
	var updatePivot = false
	//endregion
}