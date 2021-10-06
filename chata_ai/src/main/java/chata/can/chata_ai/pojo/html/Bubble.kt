package chata.can.chata_ai.pojo.html

object Bubble
{
	fun getBubble(): String
	{
		return """function setBubble() {
  var svg = svgMulti().append('g')
		.attr('transform', `translate(${'$'}{margin.left + 60}, ${'$'}{margin.top})`);

  // Add X axis
  var x = d3.scaleBand()
    .domain(aCatHeatX)
    .range([ 0, width ]);
  svg.append('g')
    .attr('transform', 'translate(0,' + height + ')')
    .call(
      d3.axisBottom(x)
      .tickSize(0))
    .call(g => g.select('.domain').remove())
    .selectAll('text')
    .attr('transform', 'translate(0, 10)')
    .attr('fill', '#909090');
  
  var y = d3.scaleBand()
    .range([ height, 0 ])
    .domain(aCatHeatY)
  svg.append("g")
    .call(
      d3.axisLeft(y)
      .tickSize(0)
      .tickFormat(x =>`${'$'}{getFirst10(x)}`))
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
    .selectAll('text')
      .attr('fill', '#909090');

  // Add dots
  svg.append('g')
    .selectAll('dot')
    .data(data)
    // .data(data1)
    .enter()
    .append('circle')
    .attr('id', function(item, i){ return `${'$'}{item.name}_${'$'}{item.group}`;})
    .attr('cx', function (d) { return x(d.group) + (x.bandwidth() / 2); } )
    .attr('cy', function (d) { return y(d.name) + (y.bandwidth() / 2); } )
    .attr('r', function (d) { return d.value; })
    .style('fill', '#26a7df')
    .style('opacity', '0.65')
    .attr('stroke', '#26a7e9')
    .on('click', function(_) {
      var idParent = this.id;
      drillDown(idParent);
    });
		
	//Add X axis label:
  addText(svg, 'end', 16, 0, (width / 2) + margin.top, height + margin.left, '#808080', '', axisY);
	
	//Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), 0  -margin.bottom + 25, '#808080', '', axisX);
}
"""
	}
}