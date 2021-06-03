package chata.can.chata_ai.pojo.html

object Line
{
	fun getLine(): String
	{
		return """
function setLine() {
  var svg = d3.select('body').append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // Initialise a X axis:
    var xScaleBand = d3.scaleBand().range([0, width]).padding(1);

    // Initialize an Y axis
    var yScale = d3.scaleLinear().range([height, 0]);

    // create the X axis
    xScaleBand.domain(data.map(function(d) { return d.name; }));
    // create the Y axis
    yScale.domain([0, d3.max(data, function(d) { return d.value }) ]);

    svg.append("g")
      .attr("transform", "translate(0," + height + ")")
      .call(d3.axisBottom(xScaleBand));

    svg.append("g")
      .call(d3.axisLeft(yScale));

    // Updata the line
    svg.selectAll()
      .data([data], function(d){ return d.value })
      .enter()
      .append("path")
      .attr("d", d3.line()
        .x(function(d) { return xScaleBand(d.name); })
        .y(function(d) { return yScale(d.value); })
        //.curve(d3.curveMonotoneX)
      )
      .attr("stroke", colorBi)
      .attr("stroke-width", 1.5)
      .attr("fill", "none");

    svg.selectAll()
      .data(data)
      .enter()
      .append("circle")
      .attr("cx", function(d) { return xScaleBand(d.name) })
      .attr("cy", function(d) { return yScale(d.value) })
      .attr("r", 4)
      .attr("fill", colorBi)
      .on('click', function(d) {
        drillDown(d.value);
      });
	}
"""
	}
}