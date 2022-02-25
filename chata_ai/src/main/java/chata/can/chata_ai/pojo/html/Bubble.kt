package chata.can.chata_ai.pojo.html

object Bubble
{
	fun getBubble(): String
	{
		return """function setBubble() {
	var marginLeft = margin.left + 50;
  var marginBottom = margin.bottom + 15;
  var svg = d3.select('body').append('svg')
		.attr('width', width + marginLeft + margin.right)
		.attr('height', height + margin.top + marginBottom)
    .append('g')
		.attr('transform', `translate(${'$'}{marginLeft}, ${'$'}{margin.top})`);
		
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
  var axis = axisMulti(svg, false, x, height, 5, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    completeAxisMultiple(axis, -5, 0, -45);
  
  var y = d3.scaleBand()
    .range([ height, 0 ])
    .domain(_aCatHeatY)

  var axis = axisMulti(svg, true, y, 0, 0, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    completeAxisMultiple(axis, -5, 0, 0);
			
	var bandX = x.bandwidth();
  var bandY = y.bandwidth();

  var minRadio = Math.min(bandX, bandY) / 2;

  isCurrency = !isCurrency;
	// Add dots
  svg.append('g')
    .selectAll('dot')
    .data(getData())
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
      var categories = idParent.split('_');
      var request = isCurrency ? `${'$'}{categories[1]}_${'$'}{categories[0]}` : `${'$'}{categories[0]}_${'$'}{categories[1]}`;
      drillDown(request);
    });
		isCurrency = !isCurrency;
		
	//on left
  addText(svg, 'start', 16, /*angle*/-90, /*X*/-height / 2 - sizeByLetter(axisMiddle.length), /*Y*/-marginLeft + 15, '#808080', '', `${'$'}{axisX}`, null);

  //on bottom
  addText(svg, 'end', 16, /*angle*/0, /*X*/(width / 2) + sizeByLetter(axisY.length) / 2, /*Y*/height + marginBottom - 0, '#808080', '', axisY, null);
}
"""
	}
}