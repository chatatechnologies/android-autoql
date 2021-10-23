package chata.can.chata_ai.pojo.webView

class DataD3
{
	var type = "table"
	var table = ""
	var pivot = ""

	var indexData = -1
	var data = "[]"
	var data2 = "[]"
	var dataStacked = "[]"
	var aCategoryX = "[]"
	var aAllData = "[]"
	var aMaxData = "[]"
	var dataFormatted = "[]"

	var catHeatX = "[]"
	var catHeatY = "[]"

	var drillX = ""
	var drillList = "[]"

	var drillY = ""
	var drillTableY = "[]"

	var xAxis = ""
	var yAxis = ""
	var middleAxis = ""

	var categories = "[]"
	var categories2 = "[]"
	var catCommon = "[]"

	var min = -1
	var max = -1
	var aMax = ArrayList<Int>()
	var min2 = -1
	var max2 = -1
	var aMax2 = ArrayList<Int>()

	var isBi = true
	var isColumn = false
	var isDashboard = false
	//region flag control
	var updateTable = false
	var updatePivot = false
	//endregion
}