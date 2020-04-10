package chata.can.chata_ai.pojo.webView

import androidx.core.content.ContextCompat
import chata.can.chata_ai.activity.chat.PropertyChatActivity
import chata.can.chata_ai.pojo.color.ThemeColor

object HtmlMarked
{
	fun getHTML(
		dataForWebView: DataForWebView): String
	{
		var backgroundColor = "#FFFFFF"
		var textColor = "#FFFFFF"

		with(ThemeColor.currentColor)
		{
			PropertyChatActivity.context?.let {
				backgroundColor = "#" + Integer.toHexString(ContextCompat.getColor(
					it,
					drawerBackgroundColor) and 0x00ffffff)

				textColor = "#" + Integer.toHexString(ContextCompat.getColor(
					it,
					drawerColorPrimary) and 0x00ffffff)
			}
		}

		return """<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>

<script src="https://unpkg.com/sticky-table-headers"></script>
<style type="text/css">
    body, table, th{
      background: $backgroundColor !important;
      color: $textColor !important;
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
      display: table;
      min-width: 100%;
      white-space: nowrap;
      border-collapse: separate;
      border-spacing: 0px!important;
      border-color: grey;
    }
    table {
      font-size: 12px;
    }
    tr td:first-child {
      text-align: center;
    }
    td {
      padding: 3px;
      text-align: center!important;
    }
		.green {
		  color: #2ECC40;
		}
		.red {
		  color: red;
		}
</style>
<title></title>
</head>
<body>
	${dataForWebView.table}
	<div id="container" class="container" style="display:none;"></div>
	${dataForWebView.datePivot}
<script>
	var aId = ["nativeTable", "bar", "column", "line", "pie", "bubble", "heat", "pivotTable"];
  function toggleCharts(idChart)
  {
		changeGraphic(idChart);
    /*aId.forEach(
      element =>
      {
        if (element != idChart)
        {
          ${'$'}("#" + element).hide("fast");//show
        }
      }
    );
    ${'$'}("#" + idChart).show("fast");//show*/
  }
	
	
	var supportTables = ["pie","column","bar","line","word_cloud"];
	var type = 'table';
	var xAxis = 'Product';
	var yAxis = 'Sale  Line Item (Sum)';
	var dashBoardActive = false;
	var rows = "400px";
	
	var dataTableBasic = [];
	var dataTablePivot = [];
	var dataColumnPivot = [];
	//dataChartBi 
	var dataChartBi = [["Direct Advance Level", 86500.00], ["Direct Basic Level", 889750.00], ["Direct Premium Level", 188260.00], ["Re-seller Advance level", 752040.00], ["Re-seller Basic Level", 3739400.00], ["Re-seller Premium Level", 1154608.00]];
	var datachartTri = [];
	
	//var dataChartLine = [86500.00, 889750.00, 188260.00, 752040.00, 3739400.00, 1154608.00];
	var dataChartLine = [];
	//var categoriesX = ["Direct Advance Level", "Direct Basic Level", "Direct Premium Level", "Re-seller Advance level", "Re-seller Basic Level", "Re-seller Premium Level"];
	var categoriesX = [];
	//var categoriesY = [86500.00, 889750.00, 188260.00, 752040.00, 3739400.00, 1154608.00];
	var categoriesY = [];
	//var drillX = ["Direct Advance Level", "Direct Basic Level", "Direct Premium Level", "Re-seller Advance level", "Re-seller Basic Level", "Re-seller Premium Level"];
	var drillX = [];
	//var drillY = [86500.0, 889750.0, 188260.0, 752040.0, 3739400.0, 1154608.0];
	var drillY = [];
	
	//var dataCloud = [{"name":"Direct Advance Level","weight":1},{"name":"Direct Basic Level","weight":1},{"name":"Direct Premium Level","weight":1},{"name":"Re-seller Advance level","weight":1},{"name":"Re-seller Basic Level","weight":1},{"name":"Re-seller Premium Level","weight":1}];
	var dataCloud = [];
	
	var dataForecasting = [];
	
	var colorAxis = "rgb(222,222,222)";
	var colorFill = "rgb(79,84,90)";
	var color1 = "rgb(38,167,223)";
	var color2 = "rgb(165,205,57)";
</script>
<script src="https://rinconarte.com.mx/chata/chat2.0.js"></script>
</body>
</html>"""
	}
}