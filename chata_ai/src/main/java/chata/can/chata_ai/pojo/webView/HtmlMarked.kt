package chata.can.chata_ai.pojo.webView

import androidx.core.content.ContextCompat
import chata.can.chata_ai.context.ContextActivity
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors
import chata.can.chata_ai.pojo.SinglentonDrawer.darkThemeColor
import chata.can.chata_ai.pojo.SinglentonDrawer.lightThemeColor
import chata.can.chata_ai.pojo.SinglentonDrawer.themeColor
import chata.can.chata_ai.pojo.color.ThemeColor

object HtmlMarked
{
	fun getHTML(
		dataForWebView: DataForWebView): String
	{
		val isBi = dataForWebView.isBi
		var backgroundColor = "#FFFFFF"
		var textColor = "#FFFFFF"

		with(ThemeColor.currentColor)
		{
			ContextActivity.context?.let {
				backgroundColor = "#" + Integer.toHexString(ContextCompat.getColor(
					it,
					drawerBackgroundColor) and 0x00ffffff)

				textColor = "#" + Integer.toHexString(ContextCompat.getColor(
					it,
					drawerColorPrimary) and 0x00ffffff)
			}
		}
		val color1: String = if (themeColor == "light") lightThemeColor else darkThemeColor

		val sColors = aChartColors.joinTo(StringBuilder("["), postfix = "]") {
			"\"$it\""
		}

		return with(dataForWebView)
		{
			"""<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=no">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://unpkg.com/sticky-table-headers"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/heatmap.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<link href="https://fonts.googleapis.com/css?family=Titillium+Web” rel=“stylesheet">
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
</head>
<body>
<div id='container' class='container'></div>
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
    .green{
        color: #2ecc40;
    }
    .red {
        color: red;
    }
    .highcharts-credits, .highcharts-button-symbol, .highcharts-button-box, .highcharts-exporting-group {
        display: none;
    }
		${if (!isBi) ".highcharts-background {\n" +
				"      fill: $backgroundColor !important;\n" +
				"    }" else ""}
</style>
<title></title>
</head>
<body>
	${dataForWebView.table}
	<div id="container" class="container" style="display:none;"></div>
	${dataForWebView.datePivot}
<script>
	var type = '${dataForWebView.type}';
	var xAxis = '';
	var yAxis = '';
	var dashBoardActive = false;
	var rows = "400px";

	var dataChartBi = ${if (isBi) dataChartBi else "[]"};
	var datachartTri = ${if (isBi) "[]" else dataChartBi};
	
	var dataChartLine = ${if (isBi) catY else catYS};
	var categoriesX = $catX;
	var categoriesY = ${if (isBi) catYS else catY};
	
	var drillX = ${dataForWebView.drillX};
	var drillY = ${dataForWebView.drillY};
	
	var colorAxis = "$textColor";
  var colorFill = "$backgroundColor";
	var color1 = "$color1";
	var actual = "";
	var positions = [0,0];
	var colors = $sColors;
	var chart;
	var colorGhost = 'rgba(0,0,0,0)';
	var styleTooltip = {color: '#fff', display: 'none'}
	var subTitle = { text:'' }
	var yAxisTitle = {
		title: {
			text: yAxis,
			style: {
				color: colorAxis
			}
		}
	}
	var xAxisStyle = { color: colorAxis };
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
          style: styleTooltip,
          formatter: function () {
            if (dataChartBi.length > 0){
                 drillDown(drillX[this.point.x])
            } else{
                 drillDown(drillX[this.point.y]);
            }
            return "";
          }
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
            function changeGraphic(graphic) {
       if (graphic == "table" || graphic == "pivot_column" || graphic == "date_pivot" || graphic == "compare_table"){
           typeTable()
       }
       else{
           typeChart(graphic)
       }
    }
    function typeTable(){
       //${'$'}('#container').hide(400);
    }
		function finalSize(invert){
        var defaultWidth = "100%";
        var defaultHeight = "90%";
        var dynamicWidthSize = ""+categoriesX.length * 10+"%";
        var widthSize = categoriesX.length <= 10 ? defaultWidth : dynamicWidthSize;
        var dynamicHeightSize = ""+categoriesY.length * 10+"%";
        var heightSize = categoriesY.length <= 10 ? defaultHeight : dynamicHeightSize;
        var heightSizeFinal = invert ? heightSize : defaultHeight;
        ${'$'}('.container, #container').css({ "width": widthSize, "position": "absolute","height":heightSizeFinal, "z-index": "0" });
    }
    function typeChart(graphic){
       var inverted = graphic == "column" || graphic == "line" ? false : true;
       switch (graphic)
       {
           case "pie":
                pieType();
                break;
           case "line":
                lineType();
               break;
           case "word_cloud":
               cloudType();
               break;
           case "column":
           case "bar":
                biType(graphic,inverted);
                break;
           case "contrast_bar":
           case "contrast_line":
           case "contrast_column":
               biType3(graphic,inverted);
                break;
           case "forecasting":
           case "status_forecasting":
               foresType(graphic);
               break;
           case "bubble":
           case "heatmap":
                triType(graphic);
                break;
           case "stacked_bar":
                stackedType(true);
                break;
           case "stacked_column":
                stackedType(false);
                break;
        }
    }
            function pieType(){
       chart.destroy()
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
    function lineType(){
			finalSize(false);
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
                        style: styleTooltip,
                        formatter: function () {
                            drillDown(drillX[this.point.x])
                                 
                            return "";
                        }
                    },
                });
                
        }
    }
    function biType(type,inverted){
				finalSize(inverted);      
        chart.destroy()
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
                           rotation: inverted ? 0 : 60,
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
    function biType3(type,inverted){
				finalSize(inverted);
        typeFinal = type.replace("contrast_", "");
        chart.destroy();
                chart = Highcharts.chart('container', {
                    chart: {
                        type: typeFinal
                    },
										title: subTitle,
                    subTitle: subTitle,
                    xAxis: {
                         gridLineWidth: 0,
                         categories: categoriesX,
                         labels: {
                           rotation:90,
                           style: xAxisStyle
                         },                         
                         title: {
                           text: xAxis
                         }
                       },
                    series: dataChartLine,
                    colors: colors
                });
    }
            function triType(type){
        finalSize(false);
        chart.destroy();
                chart = Highcharts.chart('container', {
                    chart: {
                        type: type,
                        inverted: false
                    },
                    title: subTitle,
                    subTitle: subTitle,
                    xAxis: {
                        gridLineWidth: 0,
                        categories: categoriesX,
                        labels: {
                            rotation: 50,
                            step:1,
                            style: xAxisStyle
                        },
                        title: {
                            text: xAxis
                        }
                    },
                    yAxis: {
                        min: 0,
                        gridLineWidth: 0,
                        categories: categoriesY,
                        title: {
                            text: yAxis
                        },
                        labels: {
                           style: {
                               color: colorAxis
                           }
                         },
                    },
                    colorAxis: {
                        reversed: false,
                        min: 0,
                        minColor: colorFill,
                        maxColor: colors[0]
                    },
                    legend: {
                        align: 'right',
                        layout: 'vertical',
                        margin: 0,
                        reversed: false,
                        verticalAlign: 'middle'
                    },
                    tooltip: {
                        backgroundColor: colorGhost,
                         style: styleTooltip,
                        formatter: function () {
                             drillDown(""+categoriesX[this.point.x]+"_"+drillY[this.point.y])
                            return "";
                        }
                    },
                    series: [{
                            colorByPoint: false,
                            dataLabels: {
                                enabled: false
                            },
                            data: datachartTri
                   }]
                });
    }
    
    function stackedType(invert){
				finalSize(invert);
        chart.destroy();
                chart = Highcharts.chart('container', {
                    chart: {
                        type: 'column',
                        inverted: invert
                    },
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
                        enabled: false
                    },
                    xAxis: {
                        categories: categoriesX,
                        labels: {
                            rotation: 40,
                            step:1,
                            style: xAxisStyle
                        },
                        title: {
                            text: xAxis
                        }
                    },
                    dataLabels: {
                        enabled: false,
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    tooltip: {
                         backgroundColor: colorGhost,
                         style: styleTooltip,
                         formatter: function () {
                             
                              drillDown(""+categoriesX[this.point.x]+"_"+this.series.name)
                           return "";
                         }
                       },
                    colors: colors,
                    plotOptions: {
                        column: {
                            stacking: 'normal'
                        }
                    },
                    series: dataChartLine
                });
    }
startScript();
function startScript() {
  ${'$'}('.container, #idTableBasic, table').css({ "width": "100%", "position": "absolute","height":"90%", "z-index": "0" });
    chart = Highcharts.chart('container', defaultChart);
    //changeGraphic('column');
    var typeTmp = '';
    if (type == 'table') {
        typeTmp = 'column';
    }
    else {
        typeTmp = type;
    }
    changeGraphic(typeTmp);
}
hideAll();
function hideAll(){
    ${'$'}('.container, #container, #idTableBasic, table').css({ "width": "100%", "position": "absolute","height":"90%", "z-index": "0" });
    //${'$'}( "#idTableDataPivot, #container" ).hide();

    if (type != "table") {
      ${'$'}( "#idTableDataPivot, #idTableBasic" ).hide();
    }
    else {
      ${'$'}( "#idTableDataPivot, #container" ).hide();
    }
}
function hideTables(idHide, idShow, selectType) {
    ${'$'}( idHide ).hide("slow");
    ${'$'}( idShow ).show("slow");
    ${'$'}('.container, #container').css({ "width": "100%", "position": "absolute","height":"90%", "z-index": "0" });
    changeGraphic(selectType);
}
${'$'}('td').click(function() { 
      var tri = datachartTri.length > 0;
      var bi = dataChartBi.length > 0;
    if (bi || tri){
        var ${'$'}this = ${'$'}(this);
        var col = ${'$'}this.index();
        var row = ${'$'}this.closest('tr').index();
        var final = row + "_" + col;
       drillDown( final );
    }
});

function drillDown(position)
{
    try
    {
        Android.boundMethod(position);
    } catch(err)
    {
        console.log('The native context does not exist yet: '+position);
    };
}
</script>
</body>
</html>"""
		}
	}
}
