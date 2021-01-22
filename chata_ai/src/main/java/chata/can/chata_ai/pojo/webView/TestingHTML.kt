package chata.can.chata_ai.pojo.webView

object TestingHTML
{
	fun getHtmlTest(): String
	{
		return """<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=no">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="d3.v6.min.js"></script>
<!-- <script src="https://d3js.org/d3.v6.min.js"></script> -->

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
  <div>
    <!-- <button class="button" onclick="updateData(TypeEnum.TABLE)">TABLE</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.PIVOT)">PIVOT</button> -->
    <button class="button" onclick="updateData(TypeEnum.COLUMN)">COLUMN</button>
    <!-- <button class="button" onclick="updateData(TypeEnum.BAR)">BAR</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.LINE)">LINE</button> -->
    <button class="button" onclick="updateData(TypeEnum.PIE)">PIE</button>
    <!-- <button class="button" onclick="updateData(TypeEnum.HEATMAP)">HEATMAP</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.BUBBLE)">BUBBLE</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.STACKED_BAR)">STACKED BAR</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.STACKED_COLUMN)">STACKED COLUMN</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.STACKED_AREA)">STACKED AREA</button> -->
  </div>
  <script>
    function digitsCount(n) {
      var count = 0;
      if ( n >= 1) ++count;

      while (n/ 10 >= 1) {
        n /= 10;
        ++count;
      }
      return count;
    }

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
    var typeChart;

    //NO IMPORTANT FOR SCRIPT FINAL
    var data = [
      {name: "may. 2019", value: 100500.00},
      {name: "ago. 2019", value: 122868.00},
      {name: "sep. 2019", value: 57326.75},
      {name: "oct. 2019", value: 104254.00},
      {name: "nov. 2019", value: 106630.75},
    ];
    var maxValue = 106630.75;//put here max value
    var minValue = 57326.75;//put here min value

    //The left margin makes the left border visible
    var margin = {
      top: 20,
      right: 20,
      bottom: 72 + 10,//factor count letter by 7
      left: ((digitsCount(maxValue) - 1) * 10 + 30)//plus 30 for Y axis title
    },
    width = 960 - margin.left - margin.right,
    height = 550 - margin.top - margin.bottom,
    radius = Math.min(width, height) / 2;

    var colorPie = ["#355c7d", "#6c5b7b", "#c06c84", "#f67280", "#f8b195"];
    var colorBi = ['#26a7df'];//Main color for bars

    var scaleColorPie = d3.scaleOrdinal().range(colorPie);
    var scaleColorBi = d3.scaleOrdinal().range(colorBi);

    function angle(d) {
      var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
      return a > 90 ? a - 180 : a;
    }

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
            console.log("BAR");
            setBar();
            break;
          case TypeEnum.PIE:
            console.log("PIE");
            setPie();
            break;
          default:
            console.log("default");
        }
      }
    }

    function clearSvg() {
      //remove svg
      d3.select('svg').remove();
    }

    function setBar() {
      var svg = d3.select('body').append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
      .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

      x.domain(data.map(function(d) { return d.name; }));
      y.domain([0, d3.max(data, function(d) { return d.value; })]);

      svg.selectAll()
      .data(data)
      .enter().append('rect')
      .style('fill', function(d) {
        return scaleColorBi(d.value);
      })
      .attr('x', function(d) { return x(d.name); })
      .attr('width', x.bandwidth())
      .attr('y', function(d) { return y(d.value); })
      .attr('height', function(d) { return height - y(d.value); })
      .on('click', function(_, item) {
        console.log('Name: ' + item.name + ', Value: ' + item.value)
      });

    //the X DATA for axis bar
    svg.append('g')
      .attr('transform', 'translate(0,' + height + ')')
      .call(d3.axisBottom(x))
      //.selectAll('.tick line').attr('opacity', 0.1)
      //Remove line on domain for X axis
      .call(g => g.select('.domain').remove())
      //region set opacity for each tick item
      .call(g => g.selectAll('.tick line')
        .attr('opacity', 0.2))
        //endregion
      .selectAll('text')
      //rotate text
      .attr('transform', 'translate(10,10)rotate(-45)')
      //Set color each item on X axis
      .attr('fill', '#909090')
      .style('text-anchor', 'end');

    //the Y DATA for axis bar
    svg.append('g')
      //Format numbers on axis
      .call(
        d3.axisLeft(y)
        //remove short line for Y axis
        .tickSize(0)
        //format data for each number on Y axis
        .tickFormat(x => `${'$'}{fformat(x)}`))
      //region set lines by each value for y axis
      .call(g => g.selectAll('.tick line').clone()
        .attr('stroke-opacity', 0.1)
        .attr('x2', width))
      //endregion
      //Remove line on domain for Y axis
      .call(g => g.select('.domain').remove())
      .selectAll('text')
      .attr('fill', '#909090');

    //Add X axis label:
    svg.append('text')
      .attr('text-anchor', 'end')
      .style('font-size', 16)
      .attr('x', (width / 2) + margin.top)//for center
      .attr('y', height + margin.bottom - 10)//for set on bottom with -10
      .attr('fill', '#808080')
      .text('Month');

    //Y axis label:
    svg.append('text')
      .attr('text-anchor', 'end')
      .style('font-size', 16)
      .attr('transform', 'rotate(-90)')
      .attr('y', -margin.left + 20)
      .attr('x', margin.top + (-height / 2))//center Y axis title
      .attr('fill', '#808080')
      .text('Total Order Amount');
    }
    
    function setPie() {
      var svg = d3.select('body')
      .append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

      var pie = d3.pie()
        .value(function(d) {
          return d.value;
        });

      var g = svg.selectAll()
        .data(pie(data))
        .enter().append("g");

      var arc = d3.arc()
        .innerRadius(0)
        .outerRadius(radius);

      g.append('path')
        .attr('d', arc)
        .style('fill', function(d) {
          return scaleColorPie(d.data.value);
        });

      var labelArc = d3.arc()
        .innerRadius(radius - 60)
        .outerRadius(radius - 60);

      g.append("text")
        .style('fill', 'white')
        .attr("dy", ".35em")
        .attr("transform", function(d) {
          return 'translate(' + arc.centroid(d) + '), rotate('+ angle(d) +')';
        })
        .style("text-anchor", "middle")
        .text(function(d) {
          return d.data.name;
        });
    }
    
    updateData(TypeEnum.COLUMN);
</script>
</body>
</html>"""
	}
}