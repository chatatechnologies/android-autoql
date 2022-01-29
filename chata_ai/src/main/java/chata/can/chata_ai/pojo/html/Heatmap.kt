package chata.can.chata_ai.pojo.html

object Heatmap
{
	fun getHeatmap(): String
	{
		return """function setHeatMap() {
  margin.left = margin.left + 50;
  margin.bottom = margin.bottom + 5;
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
    .append('g')
		.attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  var x = d3.scaleBand()
    .range([0, width])
    .domain(aCatHeatY)
    .padding(0.01);

  var axis = axisMulti(svg, false, x, height, 5, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    completeAxisMultiple(axis, -5, 0, -45);

  var y = d3.scaleBand()
    .range([height, 0])
    .domain(aCatHeatX)
    .padding(0.01);

  var axis = axisMulti(svg, true, y, 0, 0, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    completeAxisMultiple(axis, -5, 0, 0);
  
  var myColor = d3.scaleLinear()
    .range([backgroundColor, colorBi[0]])
    .domain([0, maxValue]);
  svg
    .selectAll()
    .data(data, function(d) {return `${'$'}{d.name}:${'$'}{d.group}`; })
    .enter()
    .append('rect')
    .attr('id', function(item, i){ return `${'$'}{item.name}_${'$'}{item.group}`;})
    .attr('x', function(d) { return x(d.name) })
    .attr('y', function(d) { return y(d.group) })
    .attr('width', x.bandwidth() )
    .attr('height', y.bandwidth() )
    .style('fill', function(d) { return myColor(d.value)} )
    .on('click', function(d) {
      var idParent = this.id;
			drillDown(idParent);
    });

  //on left
  addText(svg, 'start', 16, /*angle*/-90, /*X*/-height / 2 - sizeByLetter(axisMiddle.length), /*Y*/-margin.left + 15, '#808080', '', `${'$'}{axisY}`, null);

  //on bottom
  addText(svg, 'end', 16, /*angle*/0, /*X*/(width / 2) + sizeByLetter(axisY.length) / 2, /*Y*/height + margin.bottom - 0, '#808080', '', axisX, null);
}
"""
	}
}