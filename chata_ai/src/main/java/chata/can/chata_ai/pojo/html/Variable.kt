package chata.can.chata_ai.pojo.html

object Variable
{
	fun getVariables(): String
	{
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
  "UNKNOW": 0});

var colorPie = ["#355c7d", "#6c5b7b", "#c06c84", "#f67280", "#f8b195"];
var colorBi = ['#26a7df'];//Main color for bars

//Main data
var data = [
  {name: "may. 2019", value: 100500.00},
  {name: "ago. 2019", value: 122868.00},
  {name: "sep. 2019", value: 57326.75},
  {name: "oct. 2019", value: 104254.00},
  {name: "nov. 2019", value: 106630.75},
];
var maxValue = 106630.75;//put here max value
var minValue = 57326.75;//put here min value
//endregion

//The left margin makes the left border visible
var typeChart;
var margin = {
  top: 20,
  right: 20,
  bottom: 72 + 10,//factor count letter by 7
  left: ((digitsCount(maxValue) - 1) * 10 + 30)//plus 30 for Y axis title
},
width = 960 - margin.left - margin.right,
height = 550 - margin.top - margin.bottom,
radius = Math.min(width, height) / 2;
"""
	}
}