package chata.can.chata_ai.pojo.webView

class DataD3
{
	var type = "table"
	var table = ""
	var rowsTable = 0
	var pivot = ""
	var pivot2 = ""
	var rowsPivot = 0

	var nColumns = 0

	var indexData = -1
	var data = "[]"
	var data2 = "[]"
	var dataStacked = "[]"
	var dataStacked2 = "[]"
	var aCategoryX = "[]"
	var aAllData = "[]"
	var aMaxData = "[]"
	var aMinData = "[]"
	var dataFormatted = "[]"

	var stackedArea = "[]"
	var stackedArea2 = "[]"

	var hasNegative = "{}"
	var catHeatX = "[]"
	var catHeatY = "[]"

	var drillX = "[]"
	var drillList = "[]"

	var drillTableY = "[]"

	var xAxis = ""
	var yAxis = ""
	var middleAxis = ""

	var categories = "[]"
	var categories2 = "[]"
	var catCommon = "[]"

	var min = -1
	var max = -1
	var min2 = -1
	var max2 = -1

	var aMax = ArrayList<Int>()
	var aMax2 = ArrayList<Int>()
	var aMin = ArrayList<Int>()
	var aMin2 = ArrayList<Int>()

	var isBi = true
	var isColumn = false
	var isDashboard = false
	//region flag control
	var updateTable = false
	var updatePivot = false
	//endregion
}