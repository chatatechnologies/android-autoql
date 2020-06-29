package chata.can.chata_ai.pojo.webView

import androidx.core.content.ContextCompat
import chata.can.chata_ai.context.ContextActivity
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors
import chata.can.chata_ai.pojo.SinglentonDrawer.darkThemeColor
import chata.can.chata_ai.pojo.SinglentonDrawer.lightThemeColor
import chata.can.chata_ai.pojo.SinglentonDrawer.themeColor
import chata.can.chata_ai.pojo.color.ThemeColor

object DashboardMaker
{
	fun getHTML(dataForWebView: DataForWebView): String
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

		val typeChart = if(dataForWebView.type == "table" || dataForWebView.type.isEmpty())
			if (dataForWebView.datePivot.isEmpty()) "idTableBasic" else "idTableDataPivot"
		else dataForWebView.type

		return with(dataForWebView) {
			"""<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=no">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://unpkg.com/sticky-table-headers"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
${if (isBi) "" else "<script src=\"https://code.highcharts.com/highcharts-more.js\"></script>\n" +
				"<script src=\"https://code.highcharts.com/modules/heatmap.js\"></script>\n" +
				"<script src=\"https://code.highcharts.com/modules/exporting.js\"></script>"}
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
</head>
<body>
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
    .highcharts-credits,.highcharts-button-symbol, .highcharts-exporting-group {
        display: none;
    }
    .highcharts-background{
        fill: $backgroundColor !important;
    }
    .splitView{
        position: relative;
    }
</style>
<div class="splitView">
<div id='container' class='container'></div>
${dataForWebView.table}
${dataForWebView.datePivot}
</div>
<script>
  var type = '$typeChart';
  var xAxis = '${dataForWebView.xAxis}';
  var yAxis = '${dataForWebView.yAxis}';

  var dataChartBi = ${if (isBi) dataChartBi else "[]"};
  var datachartTri = ${if (isBi) "[]" else dataChartBi};

  var dataChartLine = ${if (isBi) catY else catYS};
  var categoriesX = ${ if (isBi) catX else catY};
  var categoriesY = ${if (isBi) catYS else catX};

  var drillX = ${dataForWebView.drillX};
  var drillY = ${dataForWebView.drillY};

  var drillX = [];
  var drillTableY = [];
  var drillSpecial = [];
  var drillY = [];
  var colorAxis = "$textColor";
  var colorFill = "$backgroundColor";
  var color1 = "$color1";
  var actual = "";
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
               drillDown(categoriesX[this.point.x])
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
    ${'$'}('td').click(function() {
      triTypeTable = datachartTri.length > 0;
      var ${'$'}this = ${'$'}(this);
      var row = ${'$'}this.closest('tr').index();
      var column = ${'$'}this.closest('td').index();
      var firstColumn = ${'$'}this.closest('tr');
      var finalText = firstColumn[0].firstChild.innerText;
      var strDate = firstColumn[0].children[1].innerText;
      if (type == "idTableDataPivot" ){
          finalText += "_"+drillSpecial[column - 1];
      } else if (type == "idTableDatePivot" ) {
          finalText = ${'$'}this[0].childNodes[0].id
      } else if (type == "idTableBascic" && triTypeTable ) {
          finalText += "_"+drillTableY[row];
      }
      //var d = new Date( Date.parse('2017 2') );
      drillDown( finalText );
    });
    function drillDown(position){
        try {
            webkit.messageHandlers.drillDown.postMessage(position);
        } catch(err) {
            console.log(position);
        };
    }
    function changeGraphic(graphic) {
       if (graphic == "idTableBasic" || graphic == "idTableDataPivot" || graphic == "idTableDatePivot"){
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
        ${'$'}('.container, #container').css({ "width": widthSize, "position": "relative","height":heightSizeFinal, "z-index": "0" });
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
           case "stacked_area":
           case "stacked_line":
                stackedArea();
                break;
        }
    }
            function pieType(){
       ${'$'}('.container, #container').css({ "width": "100%", "position": "relative","height":"90%", "z-index": "0" });
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
                        formatter: function () {
                            drillDown(categoriesX[this.point.x])
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
												 reversed: false,
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
                        }],
                    tooltip: {
                        backgroundColor: colorGhost,
                        style: styleTooltip,
                        formatter: function () {
                            drillDown(categoriesX[this.point.x])
                            return "";
                        }
                    }
                });
    }
    function biType3(type,inverted){
        finalSize(inverted);
        typeFinal = type.replace("contrast_", "");
        chart.destroy()
                chart = Highcharts.chart('container', {
                    chart: {
                        type: typeFinal
                    },
                    colors: colors,
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
                    series: dataChartLine
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
        var rotation = invert ? 10 : 40
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
                            rotation: rotation,
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
                             var position = categoriesY.indexOf(this.series.name);
                             console.log(position);
                             drillDown(""+categoriesX[this.point.x]+"_"+drillY[position]);
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
function stackedArea(){

    var rotation = 40
    chart.destroy();
            chart = Highcharts.chart('container', {
                chart: {
                    type: "area",
                    inverted: false
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
                        rotation: rotation,
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
                         var position = categoriesY.indexOf(this.series.name);
                         console.log(position);
                         drillDown(""+categoriesX[this.point.x]+"_"+drillY[position]);
                       return "";
                     }
                   },
                colors: [colors[0]],
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
    chart = Highcharts.chart('container', defaultChart);
}
hideAll();
function hideAll(){
    ${'$'}('table').css({ "width": "100%", "position": "relative","height":"90%", "z-index": "0" });
    ${'$'}( "#idTableBasic, #idTableDataPivot, #idTableDatePivot, #container" ).hide();
    if (type == "idTableBasic" || type == "idTableDataPivot" || type == "idTableDatePivot"){
        ${'$'}("#"+type).show()
    } else{
        ${'$'}("#container").show()
        changeGraphic(type);
        if (false){
            ${'$'}( "#idTableBasic").show()
        }
    }
}
function hideTables(idHide, idShow, type2) {
    ${'$'}( idHide ).hide("slow");
    ${'$'}( idShow ).show("slow");
    type = type2;
    changeGraphic(type2);
}
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
</html>
"""
		}
	}
}