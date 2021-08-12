package chata.can.chata_ai.pojo.html

object Line
{
	fun getLine(): String
	{
		return """function setLine() {
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
      .call(
        d3.axisBottom(xScaleBand)
        .tickFormat(x =>`${'$'}{getFirst10(x)}`))
      //Remove line on domain for X axis
      .call(g => g.select('.domain').remove())
			//region set opacity for each tick item
			.call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
      .selectAll('text')
      //rotate text
      .attr('transform', 'translate(10,10)rotate(-45)')
      //Set color each item on X axis
      .attr('fill', '#909090')
      .style('text-anchor', 'end');

    svg.append("g")
      .call(
        d3.axisLeft(yScale)
        .tickSize(0)
        .tickFormat(x => `${'$'}{fformat(x)}`))
      //region set lines by each value for y axis
      .call(
        g => g.selectAll('.tick line')
        .clone()
        .attr('stroke-opacity', 0.1)
        .attr('x2', width)
      )
      //Remove line on domain for Y axis
      .call(g => g.select('.domain').remove())
      .selectAll('text')
      .attr('fill', '#909090');

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
      .attr("r", 5)
      .attr("fill", colorBi)
      .on('click', function(_, d) {
	      var index = data.indexOf(d);
	      var value = drillX[index];
	      drillDown(value);
	    });
			
		//Add X axis label:
    addText(svg, 'end', 16, 0, (width / 2) + margin.top, height + margin.bottom - 10, '#808080', '', axisX);
    //Y axis label:
    addText(svg, 'end', 16, -90, margin.top + (-height / 2), -margin.left + 20, '#808080', '', axisY);
	}"""
	}
}