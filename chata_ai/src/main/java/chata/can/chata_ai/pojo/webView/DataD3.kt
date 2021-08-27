package chata.can.chata_ai.pojo.webView

class DataD3
{
	var type = "table"
	var table = ""
	var pivot = ""

	var data = ""
	var dataFormatted = ""

	var catX = "[]"
	var catY = "[]"

	var drillX = ""

	var drillY = ""
	var drillTableY = "[]"

	var xAxis = ""
	var yAxis = ""

	var categories = "[]"
	var categories2 = "[]"

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