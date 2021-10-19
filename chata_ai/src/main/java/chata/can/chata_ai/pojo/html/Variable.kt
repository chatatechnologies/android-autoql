package chata.can.chata_ai.pojo.html

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.webView.DataD3

object Variable
{
	private fun String.tableOrPivot(): String
	{
		return if (isEmpty()) "TypeEnum.TABLE" else "#idTableDataPivot"
	}

	fun getVariables(dataD3: DataD3, backgroundColor: String): String
	{

		val typeChart =
			if (dataD3.updateTable || dataD3.updatePivot)
			{
				dataD3.updateTable = false
				dataD3.updatePivot = false
				dataD3.pivot.tableOrPivot()
			}
			else if (dataD3.isColumn && !dataD3.isDashboard)
				if (dataD3.isBi) "TypeEnum.COLUMN" else "stacked_column"
			else
				when(dataD3.type)
				{
					"table" -> dataD3.pivot.tableOrPivot()
					"pivot_table" -> "#idTableDataPivot"
					"" -> dataD3.pivot.tableOrPivot()
					else -> dataD3.type
				}

		val color1 = SinglentonDrawer.aChartColors[0]
		val sColors = SinglentonDrawer.aChartColors.joinTo(StringBuilder("["), postfix = "]") {
			"\"$it\""
		}
		return """
//region not mutable
const TypeEnum = Object.freeze({
  "TABLE": 1,
  "PIVOT": 2,
  "COLUMN": 3,
  "BAR": 4,
  "LINE": 5,
  "PIE": 6,
  "HEATMAP": 7,
  "BUBBLE": 8,
  "STACKED_BAR": 9,
  "STACKED_COLUMN": 10,
  "STACKED_AREA": 11,
  "UNKNOWN": 0});
	
const TypeManage = Object.freeze({
  'SELECTABLE': 'SELECTABLE',
  'PLAIN': 'PLAIN'
});

var backgroundColor = '$backgroundColor';
var colorPie = $sColors;
var colorBi = ['$color1'];//Main color for bars

var axisX = '${dataD3.xAxis}';
var axisY = '${dataD3.yAxis}';
var nColumns = 0;

//Main data
var indexData = ${dataD3.indexData};
var dataTmp = [];
var data = ${dataD3.data};
var aStacked = ${dataD3.dataStacked};
var aStackedTmp = ${dataD3.dataStacked};
var aCategoryX = ${dataD3.aCategoryX};
var data2 = ${dataD3.data2};
var aAllData = ${dataD3.aAllData};
var aMaxData = ${dataD3.aMaxData};
var dataFormatted = ${dataD3.dataFormatted};
var opacityMarked = [];
var indexIgnore = [];
var drillTableY = ${dataD3.drillTableY};
var drillX = ${dataD3.drillX};
var aDrillData = ${dataD3.drillList};
var limitName = 0;
var isCurrency = true;
var maxValue = ${dataD3.max};
var minValue = ${dataD3.min};
var maxValue2 = ${dataD3.max2};
var minValue2 = ${dataD3.min2};
var aMax = ${dataD3.aMax};
var aMax2 = ${dataD3.aMax2};
var aCategory = ${dataD3.categories};
var aCategory2 = ${dataD3.categories2};
var aCommon = ${dataD3.catCommon};
//endregion
//axis X for heatmap
var aCatHeatX = ${dataD3.catHeatX};
//axis Y for heatmap
var aCatHeatY = ${dataD3.catHeatY};

//REGION max letters in name
for (const item in getDataOrMulti()) {
  var value = getDataOrMulti()[item].name.length;
  if (limitName < value) {
    limitName = value;
    if (limitName > 10) {
      limitName = 110;
      break;
    } else {
      limitName *= 10;
    }
  }
}
//ENDREGION

//REGION get max value
for (const item in getDataOrMulti()) {
  var value = getDataOrMulti()[item].value;
  if (getMaxValue() < value) {
    maxValue = value;
  }
}
//ENDREGION

//The left margin makes the left border visible
var typeChart = $typeChart;
var digits = digitsCount(getMaxValue());
var _plusSingle = digits == 1 ? 10 : 0;
var _maxValue = (digits * 8) + 35 + _plusSingle;
var _bottom = typeChart == isHorizontal() ? _maxValue : limitName;
var _left = typeChart == isHorizontal() ? limitName : _maxValue;
//width dynamic, height dynamic
var margin = {
  top: 20,
  right: 20,
  bottom: _bottom,
  left: _left
};
var width = 0;
var height = 0;
var isAgain = false;"""
	}
}