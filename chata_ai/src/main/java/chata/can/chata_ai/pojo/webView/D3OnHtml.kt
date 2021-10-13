package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.html.Actions.getActions
import chata.can.chata_ai.pojo.html.Bar.getBar
import chata.can.chata_ai.pojo.html.Bubble.getBubble
import chata.can.chata_ai.pojo.html.Column.getColumn
import chata.can.chata_ai.pojo.html.Donut.getDonut
import chata.can.chata_ai.pojo.html.Event.getEvents
import chata.can.chata_ai.pojo.html.Filter.getFilter
import chata.can.chata_ai.pojo.html.Functions.getFunctions
import chata.can.chata_ai.pojo.html.Heatmap.getHeatmap
import chata.can.chata_ai.pojo.html.Line.getLine
import chata.can.chata_ai.pojo.html.MultiBar.getMultiBar
import chata.can.chata_ai.pojo.html.MultiColumn.getMultiColumn
import chata.can.chata_ai.pojo.html.MultiLines.getMultiLine
import chata.can.chata_ai.pojo.html.StackedColumn.getStackedColumn
import chata.can.chata_ai.pojo.html.Variable.getVariables

object D3OnHtml
{
	fun getHtmlTest(dataD3: DataD3): String
	{
		var backgroundColor: String
		var textColor: String
		with(ThemeColor.currentColor)
		{
			backgroundColor = "#" + Integer.toHexString(
				pDrawerBackgroundColor and 0x00ffffff)
			textColor = "#" + Integer.toHexString(
				pDrawerTextColorPrimary and 0x00ffffff)
		}

		return """<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=no">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://d3js.org/d3.v6.min.js"></script>
<title></title>
<style>
	body, table, th{
		background: $backgroundColor!important;
		color: $textColor!important;
  }
	table {
		padding-top: 0px!important;
	}
	th {
		position: sticky;
		top: 0px;
		z-index: 10;
		padding: 10px 3px 5px 3px;
	}
	table {
		font-size: 16px;
		display: table;
		min-width: 100%;
		white-space: nowrap;
		border-collapse: separate;
		border-spacing: 0px!important;
		border-color: grey;
	}
	tr td:first-child {
		text-align: center;
	}
	td {
		padding: 3px;
		text-align: center!important;
  }
	td, th {
		font-size: 16px;
		max-width: 200px;
		white-space: nowrap;
		width: 50px;
		overflow: hidden;
		text-overflow: ellipsis;
		border: 0.5px solid #cccccc;
  }
  span {
    display: none;
  }
  /*border for svg*/
  svg {
    border: 1px solid #aaa;
    /*width: 100%;
    position: relative;
    height: 100%;
    z-index: 0;*/
  }
	tfoot {
    /*display: table-header-group;*/
		display: none;
  }
  .button {
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
  }
</style>
</head>
<body>
${getActions()}
${dataD3.table}
${dataD3.pivot}
<script>
${getFunctions()}
${getVariables(dataD3, backgroundColor)}
${getStackedColumn()}
${getBubble()}
${getHeatmap()}
${getMultiBar()}
${getMultiColumn()}
${getMultiLine()}
${getBar()}
${getColumn()}
${getLine()}
${getDonut()}
${getFilter()}
${getEvents()}
var scaleColorBi = d3.scaleOrdinal().range(colorBi);

//region locale settings
var locale = d3.formatLocale({
  "decimal": ",",//","
  "thousands": ",",//" ",
  "grouping": [3],
  "currency": ["${'$'}", ""]//["€", ""] first if prefix, second is suffix
});
var fformat = locale.format("${'$'},");
//endregion

${'$'}(window).resize(function() {
  updateData(typeChart, true);
});
updateData(typeChart);
</script>
</body>
</html>"""
	}
}