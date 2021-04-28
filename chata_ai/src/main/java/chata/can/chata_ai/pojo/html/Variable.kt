package chata.can.chata_ai.pojo.html

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.webView.DataForWebView

object Variable
{
	fun getVariables(dataForWebView: DataForWebView): String
	{
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

var colorPie = $sColors;
var colorBi = ['$color1'];//Main color for bars

var axisX = 'Total Order Amount';
var axisY = 'Month';

//Main data
var data = [
  {name: "may. 2019", value: 100500.00},
  {name: "ago. 2019", value: 122868.00},
  {name: "sep. 2019", value: 57326.75},
  {name: "oct. 2019", value: 104254.00},
  {name: "nov. 2019", value: 106630.75},
];
var maxValue = ${dataForWebView.max}
var minValue = ${dataForWebView.min}
//endregion

//The left margin makes the left border visible
var typeChart = TypeEnum.TABLE;
//width dynamic, height dynamic
var width1 = 0;
var height = 0;
var margin = {
  top: 20,
  right: 20,
  bottom: 72 + 10,//factor count letter by 7
  left: ((digitsCount(maxValue) - 1) * 10 + 30)//plus 30 for Y axis title
};
//width = width1 - margin.left - margin.right,
var width = 0;
//height = height - margin.top - margin.bottom,
var height = 0;
//radius = Math.min(width, height) / 2;
"""
	}
}