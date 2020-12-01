package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.context.ContextActivity
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.getFooterScript
import chata.can.chata_ai.model.validateArray
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.color.ThemeColor

object DashboardMaker
{
	//region HTML
	fun getHeader(isTri: Boolean = false): String
	{
		return """<!DOCTYPE html>
    <html lang="en">
    <head>
    <title></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=no">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://unpkg.com/sticky-table-headers"></script>
		${(getHTMLCharts(isTri))}
		<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
    <meta http-equiv='cache-control' content='no-cache'>
    <meta http-equiv='expires' content='0'>
    <meta http-equiv='pragma' content='no-cache'>
		</head>
    <body>
		${(getHTMLStyle())}
		"""
	}

	private fun getHTMLCharts(isTri: Boolean): String
	{
		return if (isTri) """<script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/highcharts-more.js"></script>
        <script src="https://code.highcharts.com/modules/heatmap.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>"""
		else
			"""<script src="https://code.highcharts.com/highcharts.js"></script>"""
	}

	private fun getHTMLStyle(): String
	{
		val chataDrawerWebViewBackground = ""
		val chataDrawerWebViewText = ""
		return """<style type="text/css">
        body, table, th{
            background: $chataDrawerWebViewBackground!important;
            color: $chataDrawerWebViewText!important;
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
            font-family: '-apple-system','HelveticaNeue';
            font-size: 15.5px;
        }
        tr td:first-child {
            text-align: center;
        }
        td {
            padding: 3px;
            text-align: center!important;
        }
        td, th {
          font-family: '-apple-system','HelveticaNeue';
          font-size: 15.5px;
          max-width: 200px;
          white-space: nowrap;
          width: 50px;
          overflow: hidden;
          text-overflow: ellipsis;
          border: 0.5px solid #cccccc;
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
            fill: $chataDrawerWebViewBackground!important;
        }
        .splitView{
            position: relative;
        }
    </style>"""
	}

	fun getHTMLFooter(
		aRows: ArrayList<ArrayList<String>>,
		aColumnQuery: List<ColumnQuery>,
		types: List<TypeDataQuery>,
		drills: ArrayList<String>,
		type: String,
		split: Boolean = false
	): String
	{
		var scriptJS = ""
		if (aRows.isNotEmpty() && aColumnQuery.isNotEmpty())
		{
			getChartFooter(aRows, aColumnQuery, types, drills)
			getFooterScript()
		}

		return ""
	}

	fun getChartFooter(
		row: ArrayList<ArrayList<String>>,
		aColumnQuery: List<ColumnQuery>,
		type: List<TypeDataQuery>,
		drills: ArrayList<String>,
		mainType: String = "idTableBasic")
	{
		val dataSpecial = ArrayList<ArrayList<Any>>()
		val dataSpecialActive = false
		val categoriesX = ArrayList<String>()
		val categoriesY = ArrayList<String>()
		val drillTableY = ArrayList<String>()
		val drillY = ArrayList<String>()
		val stacked = ArrayList<ArrayList<Double>>()

		var catXFormat = row.map {
			validateArray(it,0).toString().formatWithColumn(aColumnQuery[0])
		}
		var dataChartLine = row.map {
			validateArray(it,1).toString().toDouble()
		}
		if (type.size == 3)
		{

		}
	}

	//endregion



	private fun String.tableOrPivot(): String
	{
		return if (isEmpty()) "#idTableBasic" else "#idTableDataPivot"
	}

	fun getHTML(dataForWebView: DataForWebView): String
	{
		val isBi = dataForWebView.isBi
		var backgroundColor = "#FFFFFF"
		var textColor = "#000000"

		with(ThemeColor.currentColor)
		{
			ContextActivity.context?.let {
				backgroundColor = "#" + Integer.toHexString(
					it.getParsedColor(drawerBackgroundColor) and 0x00ffffff)
				textColor = "#" + Integer.toHexString(
					it.getParsedColor(drawerTextColorPrimary) and 0x00ffffff)
			}
		}
//		val color1: String = if (themeColor == "light") lightThemeColor else darkThemeColor
		val color1 = aChartColors[0]
		val sColors = aChartColors.joinTo(StringBuilder("["), postfix = "]") {
			"\"$it\""
		}

		val typeChart = when(dataForWebView.type)
		{
			"table" -> dataForWebView.datePivot.tableOrPivot()
			"pivot_table" -> "#idTableDataPivot"
			"" -> dataForWebView.datePivot.tableOrPivot()
			else -> dataForWebView.type
		}

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
        display: table;
        min-width: 100%;
        white-space: nowrap;
        border-collapse: separate;
        border-spacing: 0px!important;
        border-color: grey;
    }
    table {
        font-size: 16px;
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
        fill: $backgroundColor!important;
    }
    .splitView{
        position: relative;
    }
</style>
<div class="splitView">
<div id='container' class='container'></div>
    ${dataForWebView.datePivot}
    ${dataForWebView.table}
</div>
<script>
    var type = '$typeChart';
    var xAxis = '${dataForWebView.xAxis}';
    var yAxis = '${dataForWebView.yAxis}';
		
    var dataChartBi = ${if (isBi) dataChartBi else dataChartBiWithTri};
    var datachartTri = ${if (isBi) "[]" else dataChartBi};
		
    var dataChartLine = ${if (isBi) catY else catYS};
		var categoriesX = $catX;
		var categoriesY = ${if (isBi) catYS else catY};
		
    var drillX = ${dataForWebView.drillX};
    var drillY = ${dataForWebView.drillY};
		
    var drillTableY = ${dataForWebView.drillTableY};
    var drillSpecial = ${dataForWebView.drillY};
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
		        rotation: -60,
		        style: {
		            color: colorAxis,
		             fontSize:'16px'
		        },
		        formatter: function(){
		         return formatterLabel(this.value);
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
                fontSize:'16px'
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
            } else if ((type == "#idTableBasic" && triTypeTable) || (type == "idTableBasic" && triTypeTable) ) {
                finalText += "_"+drillTableY[row];
            }
            //var d = new Date( Date.parse('2017 2') );
           drillDown( finalText );
    });
		function formatterLabel(value) {
      if (value.length > 7) {
        return value.slice(0, 7) + "...";
      }
     return value;
    }
    function drillDown(position){
        try {
            Android.boundMethod(position);
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
       ${'$'}('.container, #container').css({ "width": "99%", "position": "relative","height":"80%", "z-index": "0" });
       chart.destroy()
       chart = Highcharts.chart('container', {
          
        chart: {
          backgroundColor: colorGhost,
          fill: colorGhost,
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          type: "pie",
          inverted: false
        },
        title: subTitle,
        subTitle: subTitle,
        xAxis: {
          gridLineWidth: 0,
          categories: categoriesX,
          labels: {
		        rotation: -60,
		        style: {
		            color: colorAxis,
		             fontSize:'16px'
		        },
		        formatter: function(){
		         return formatterLabel(this.value);
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
                fontSize:'16px'
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
									        rotation: -60,
									        style: {
									            color: colorAxis,
									             fontSize:'16px'
									        },
									        formatter: function(){
									         return formatterLabel(this.value);
									        }
									       },
                        title: {
                            text: xAxis,
                            style: {
                               color: colorAxis,
                               fontSize:'16px'
                           }
                        }
                    },
                    chart: {
                        type: "line"
                    },
                    series: dataChartLine,
                    tooltip: {
                        formatter: function () {
                            drillDown(drillX[this.point.x])
                            return "";
                        }
                    },
                });
                
        }
    }
    function biType(type,inverted){
				var newCategory = categoriesX;
        if (categoriesX.length == 1 && newCategory[0] === "") {
            var newCategory = categoriesY;
        }
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
                         categories: newCategory,
                         labels: {
										        rotation: -60,
										        style: {
										            color: colorAxis,
										             fontSize:'16px'
										        },
										        formatter: function(){
										         return formatterLabel(this.value);
										        }
										       },
                         
                         title: {
                           text: xAxis
                         }
                       },
										yAxis: {
                        title: {
                            text: yAxis,
                            style: {
                                color: colorAxis
                            }
                        }
                    },
                    series: [{
                            colorByPoint: false,
                            name: newCategory,
                            data: dataChartBi
                        }],
                    tooltip: {
                        backgroundColor: colorGhost,
                        style: styleTooltip,
                        formatter: function () {
                            drillDown(drillX[this.point.x])
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
									        rotation: -60,
									        style: {
									            color: colorAxis,
									             fontSize:'16px'
									        },
									        formatter: function(){
									         return formatterLabel(this.value);
									        }
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
                        inverted: true
                    },
                    title: subTitle,
                    subTitle: subTitle,
                    xAxis: {
											min: 0,
                        gridLineWidth: 0,
                        categories: categoriesX,
                        labels: {
									        rotation: -60,
									        style: {
									            color: colorAxis,
									             fontSize:'16px'
									        },
									        formatter: function(){
									         return formatterLabel(this.value);
									        }
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
									        rotation: -60,
									        style: {
									            color: colorAxis,
									             fontSize:'16px'
									        },
									        formatter: function(){
									         return formatterLabel(this.value);
									        }
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
							        rotation: -60,
							        style: {
							            color: colorAxis,
							             fontSize:'16px'
							        },
							        formatter: function(){
							         return formatterLabel(this.value);
							        }
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
    if (type == "#idTableBasic" || type == "#idTableDataPivot" || type == "#idTableDatePivot"){
        ${'$'}(""+type+"").show()
    } else{
        ${'$'}("#container").show()
        changeGraphic(type);
        if (false){
            ${'$'}( "#idTableBasic").show()
        }
    }
}
function hideTables(idHide, idShow, type2) {
    if (idHide != idShow){
      ${'$'}( idHide ).hide(0);
      ${'$'}( idShow ).show(0);
    }
    //${'$'}( idShow ).show("slow");
    type = type2;
    changeGraphic(type2);
}
</script>
</body>
</html>"""
		}
	}
}