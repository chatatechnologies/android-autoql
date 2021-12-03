package chata.can.chata_ai.pojo.html

object Column
{
	fun getColumn(): String
	{
		return """function setColumn() {
  var svg = d3.select('body').append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  var x = d3.scaleBand()
    .range([0, width])
    .padding(0.1);
  var y = d3.scaleLinear()
    .range([height, 0]);

  x.domain(data.map(function(d) { return d.name; }));
  y.domain([getMinDomain(), d3.max(data, function(d) { return d.value; })]);

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
    .on('click', function(_, d) {
      var index = data.indexOf(d);
      var value = drillX[index];
      drillDown(value);
    });

//the X DATA for axis bar
svg.append('g')
  .attr('transform', `translate(0, ${'$'}{height})`)
  .call(
    d3.axisBottom(x)
    .tickFormat(x =>`${'$'}{getFirst10(x)}`))
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
	  .call(
	    g => g.selectAll('.tick line')
	    .clone()
	    .attr('stroke-opacity', 0.1)
	    .attr('x2', width))
	  //endregion
	  //Remove line on domain for Y axis
	  .call(g => g.select('.domain').remove())
	  .selectAll('text')
		.attr('transform', 'translate(0,-8)rotate(-45)')  
	  .attr('fill', '#909090');

	//Add X axis label:
  addText(svg, 'end', 14, 0, (width / 2) + margin.top, height + margin.top + margin.bottom - 25, '#808080', '', axisX);
    
  //Y axis label:
  addText(svg, 'end', 14, -90, margin.top - (height / 2), -margin.left + 15, '#808080', '', axisY);
}
"""
	}
}