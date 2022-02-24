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
		
	var _aCatHeatX = [];
  var _aCatHeatY = [];
  
  var stackedData = getStackedData();
  stackedData.map(function(item) {
    var vGroup = item.name;
    if (_aCatHeatY.indexOf(vGroup) === -1) {
      _aCatHeatY.push(vGroup);
    }

    var innerKeys = Object.keys(item);
    innerKeys.map(function(key) {
      if (key != 'name' && _aCatHeatX.indexOf(key) === -1) {
        _aCatHeatX.push(key);
      }
    });
  });

  var x = d3.scaleBand()
    .range([0, width])
    .domain(_aCatHeatY)
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
    .domain(_aCatHeatX)
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
    .data(getData(), function(d) {return `${'$'}{d.name}:${'$'}{d.group}`; })
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
      var categories = idParent.split('_');
      var request = isCurrency ? `${'$'}{categories[0]}_${'$'}{categories[1]}` : `${'$'}{categories[1]}_${'$'}{categories[0]}`;
      drillDown(request);
    });

  //on left
  addText(svg, 'start', 16, /*angle*/-90, /*X*/-height / 2 - sizeByLetter(axisMiddle.length), /*Y*/-margin.left + 15, '#808080', '', `${'$'}{axisX}`, null);

  //on bottom
  addText(svg, 'end', 16, /*angle*/0, /*X*/(width / 2) + sizeByLetter(axisY.length) / 2, /*Y*/height + margin.bottom - 0, '#808080', '', axisY, null);
}
"""
	}
}