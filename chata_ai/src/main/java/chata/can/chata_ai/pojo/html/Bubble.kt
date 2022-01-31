package chata.can.chata_ai.pojo.html

object Bubble
{
	fun getBubble(): String
	{
		return """function setBubble() {
  var svg = svgMulti().append('g')
		.attr('transform', `translate(${'$'}{margin.left + 60}, ${'$'}{margin.top})`);
		
	var _aCatHeatX = [];
  var _aCatHeatY = [];

  var stackedData = getStackedData();
  stackedData.map(function(item) {
    var vGroup = item.name;
    if (_aCatHeatX.indexOf(vGroup) === -1) {
    _aCatHeatX.push(vGroup);
    }

    var innerKeys = Object.keys(item);
    innerKeys.map(function(key) {
      if (key != 'name' && _aCatHeatY.indexOf(key) === -1) {
          _aCatHeatY.push(key);
      }
    });
  });

  // Add X axis
  var x = d3.scaleBand()
    .domain(_aCatHeatX)
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
    .domain(_aCatHeatY)
  svg.append("g")
    .call(
      d3.axisLeft(y)
      .tickSize(0)
      .tickFormat(x =>`${'$'}{getFirst10(x)}`))
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
    .selectAll('text')
      .attr('fill', '#909090');
			
	var bandX = x.bandwidth();
  var bandY = y.bandwidth();

  var minRadio = Math.min(bandX, bandY) / 2;

  // Add dots
  svg.append('g')
    .selectAll('dot')
    .data(data)
    .enter()
    .append('circle')
    .attr('id', function(item, i){ return `${'$'}{item.name}_${'$'}{item.group}`;})
    .attr('cx', function (d) { return x(d.group) + (x.bandwidth() / 2); } )
    .attr('cy', function (d) { return y(d.name) + (y.bandwidth() / 2); } )
    .attr('r', function (d) { return d.value * minRadio / maxValue2; })
    .style('fill', colorBi[0])
    .style('opacity', '0.65')
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