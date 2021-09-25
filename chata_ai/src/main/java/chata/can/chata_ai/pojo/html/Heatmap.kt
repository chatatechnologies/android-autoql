package chata.can.chata_ai.pojo.html

object Heatmap
{
	fun getHeatmap(): String
	{
		return """function setHeatMap() {
  var svg = svgMulti().append('g')
		.attr('transform', `translate(${'$'}{margin.left + 60}, ${'$'}{margin.top})`);

  var x = d3.scaleBand()
    .range([ 0, width ])
    .domain(aCatHeatX)
    .padding(0.01);
  svg.append('g')
    .attr('transform', 'translate(0,' + height + ')')
    .call(
      d3.axisBottom(x)
      .tickSize(0))
    .call(g => g.select('.domain').remove())
    .selectAll('text')
    .attr('transform', 'translate(0, 10)')
    .attr('fill', '#909090');;

  var y = d3.scaleBand()
    .range([ height, 0 ])
    .domain(aCatHeatY)
    .padding(0.01);
  svg.append("g")
    .call(
      d3.axisLeft(y)
      .tickSize(0)
      .tickFormat(x =>`${'$'}{getFirst10(x)}`))
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
    .selectAll('text')
    .attr('fill', '#909090');
  
  var myColor = d3.scaleLinear()
    .range(["#3B3F46", '#26A7DF'])
    .domain([1, 33]);//Max value allowed
  svg
    .selectAll()
    .data(data, function(d) {return d.group+':'+d.name;})
    .enter()
    .append('rect')
    .attr('id', function(item, i){ return `item_${'$'}{item.group}_${'$'}{item.name}`;})
    .attr('x', function(d) { return x(d.group) })
    .attr('y', function(d) { return y(d.name) })
    .attr('width', x.bandwidth() )
    .attr('height', y.bandwidth() )
    .style('fill', function(d) { return myColor(d.value)} )
    .on('click', function(d) {
      var idParent = this.id;
      console.log(idParent);
      var aData = idParent.split('_');
      if (aData.length > 0)
      {
        var group = aData[1];
        var name = aData[2];//before variable
      }
    });

  //Add X axis label:
  addText(svg, 'end', 16, 0, (width / 2) + margin.top, height + margin.left, '#808080', '', 'year - axisY');
  
  //Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), 0  -margin.bottom + 25, '#808080', '', 'Area - axisX');
}
"""
	}
}