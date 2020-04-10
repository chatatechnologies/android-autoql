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

		return with(dataForWebView)
		{
			"""<!DOCTYPE html>
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
	var supportTables = [];
	var type = 'table';
	
	var xAxis = '';
	var yAxis = '';
	var dashBoardActive = false;
	var rows = "400px";
	
	var dataTableBasic = [];
	var dataTablePivot = [];
	var dataColumnPivot = [];
	var dataChartBi = $dataChartBi;
	var datachartTri = [];
	
	var dataChartLine = $catY;
	var categoriesX = $catX;
	//var categoriesY = $catYS;
	var categoriesY = [];
	
	var drillX = [];
	var drillY = [];
	var dataCloud = [];
	var dataForecasting = [];
	
	var colorAxis = "rgb(222,222,222)";
	var colorFill = "rgb(79,84,90)";
	var color1 = "rgb(38,167,223)";
	var color2 = "rgb(165,205,57)";

	var colors = [color1, color2, '#dd6a6a', '#ffa700', '#00c1b2'];
	var styleTooltip = {color: '#fff', display: 'none'}
	var subTitle = { text:'' };
	var xAxisStyle = { color: colorAxis };
	var yAxisTitle = { 
		title: {
			text: yAxis,
			style: {
				color: colorAxis
			}
    }
  };

	var colorGhost = 'rgba(0,0,0,0)';
	var defaultChart =
  {
    chart: {
      backgroundColor: colorGhost,
      fill: colorGhost,
      plotBackgroundColor: null,
      plotBorderWidth: null,
      plotShadow: false,
      type: "column"
    },
    title: subTitle,
    subTitle: subTitle,
    xAxis: {
      gridLineWidth: 0,
      categories: categoriesX,
      labels: {
        rotation: 50,
        style: {
					color: colorAxis,
					fontSize:'8px'
        }
      },

      title: {
				text: xAxis,
        style: {
					color: colorAxis
				}
      }
    },
    yAxis: {
      gridLineWidth: 0,
      labels: {
        style: {
					color: colorAxis,
					fontSize:'8px'
        }
      },
      title: yAxisTitle
    },
    colorAxis: {
      reversed: false,
      min: 0,
      minColor: '#FFFFFF',
      maxColor: '#26a7df'
    },
    showInLegend: true,
    legend: false,
    dataLabels: {
      enabled: false
    },
    tooltip: {
      backgroundColor: colorGhost,
      style: styleTooltip
    },
    plotOptions: {
      pie: {
      allowPointSelect: true,
      cursor: 'pointer',
      dataLabels: {
        enabled: false,
        style: {
          color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
        }
      },
        showInLegend: true
      }
    },
    colors: colors,
    series: [0]
  };

	function startScript()
	{
		chart = Highcharts.chart('container', defaultChart);
	}
	startScript();

	function hideTables()
	{
		${'$'}('#nativeTable').hide(0);
		${'$'}('#pivotTable').hide(0);
		${'$'}('#container').show(400);
		${'$'}('.container').css({ "width": "100%", "position": "absolute","height":"90%", "z-index": "0" });
	}

	function changeGraphic(graphic)
	{
		if (graphic == "table" || graphic == "pivot_column" || graphic == "date_pivot" || graphic == "compare_table")
		{
			typeTable(graphic)
		}
		else
		{
			typeChart(graphic)
		}
	}

	function typeTable(graphic) {
		${'$'}('table').stickyTableHeaders();
		if (graphic == "pivot_column" || graphic == "date_pivot") {
			${'$'}('#nativeTable').hide();
			${'$'}('#pivotTable').show(400)
		}
		else{
			${'$'}('#pivotTable').hide();
			${'$'}('#nativeTable').show(400);
		}
		${'$'}('#container').hide(400);
	}

	function typeChart(graphic)
	{
		var inverted = graphic == "column" || graphic == "line" ? false : true;
		hideTables();

		switch (graphic)
		{
			case "pie":
				pieType();
				break;
			case "line":
				lineType();
			case "column":
			case "bar":
				biType(graphic,inverted);
				break;
		}
	}

	//region pie
	function pieType()
	{
		chart.destroy();
		chart = Highcharts.chart('container', defaultChart);
		chart.update({
			chart: {
				type: "pie",
				fill: colorGhost,
				inverted: false
			},
			series: [{
				colorByPoint: true,
				data: dataChartBi
			}]
		});
	}
	//endregion

	function lineType(){
     if (dataChartBi.length > 0){
         biType("line",false);
     }else{
         chart.destroy();
             chart = Highcharts.chart('container', {
                 colors: colors,
                 title: subTitle,
                 subTitle: subTitle,
                 yAxis: {
                     title: {
                         text: yAxis
                     },
                     labels: {
                        style: {
                            color: colorAxis,
                        }
                      },
                 },
                 legend: {
                            itemStyle: {
                                color: colorAxis,
                                fontWeight: 'bold'
                            }
                        },
                 xAxis: {
                     categories: categoriesX,
                     legend:{
                         step:1
                     },
                     labels: {
                        style: {
                            color: colorAxis,
                        }
                      },
                    
                     title: {
                         text: xAxis,
                         style: {
                            color: colorAxis,
                            fontSize:'8px'
                        }
                     }
                 },
                 chart: {
                     type: "line"
                 },
                 series: dataChartLine,
                 tooltip: {
                     backgroundColor: colorGhost,
                     style: styleTooltip
                 },
             });
             
     }
 }

	function biType(type,inverted) {
		chart.destroy();
		chart = Highcharts.chart('container', defaultChart);
		chart.update({
                 chart: {
                     type: type,
                     inverted: inverted
                 },
                 
                 xAxis: {
                      gridLineWidth: 0,
                      categories: categoriesX,
                      labels: {
                        rotation: 50,
                        style: xAxisStyle
                      },
                      
                      title: {
                        text: xAxis
                      }
                    },
                 series: [{
                         colorByPoint: false,
                         name: categoriesX,
                         data: dataChartBi
                     }]
             });
	}
	
</script>
<script src="https://rinconarte.com.mx/chata/chat2.0.js"></script>
</body>
</html>"""
		}
	}
}