package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.html.Actions.getActions
import chata.can.chata_ai.pojo.html.Bar.getBar
import chata.can.chata_ai.pojo.html.Column.getColumn
import chata.can.chata_ai.pojo.html.Donut.getDonut
import chata.can.chata_ai.pojo.html.Functions.getFunctions
import chata.can.chata_ai.pojo.html.Line.getLine
import chata.can.chata_ai.pojo.html.Variable.getVariables

object TestingHTML
{
	fun getHtmlTest(): String
	{
		return """
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=no">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://d3js.org/d3.v6.min.js"></script>
<title></title>
<style>
  /*border for svg*/
  svg {
    border: 1px solid #aaa;
    /*width: 100%;
    position: relative;
    height: 100%;
    z-index: 0;*/
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
<script>
${getFunctions()}
${getVariables()}
${getBar()}
${getColumn()}
${getLine()}
${getDonut()}
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

var x = d3.scaleBand()
    .range([0, width])
    .padding(0.1);
var y = d3.scaleLinear()
  .range([height, 0]);

function updateData(tmpChart) {
  if (typeChart != tmpChart) {
    typeChart = tmpChart;

    clearSvg();
    switch(typeChart) {
      case TypeEnum.COLUMN:
        console.log("COLUMN");
        setColumn();
        break;
      case TypeEnum.BAR:
        console.log("BAR");
        setBar();
        break;
      case TypeEnum.LINE:
        console.log("LINE");
        setLine();
        break;
      case TypeEnum.PIE:
        console.log("PIE");
        setDonut();
        break;
      default:
        console.log("default");
    }
  }
}

updateData(TypeEnum.COLUMN);
</script>
</body>
</html>"""
	}
}