package chata.can.chata_ai.pojo.html

object StackedBar
{
	fun getStackedBar(): String
	{
		return """function setStackedBar() {
  var svg = svgMulti().append('g')
		.attr('transform', `translate(${'$'}{margin.left + 60}, ${'$'}{margin.top})`);

	var withReduce = width - 100;
	//region rewrite
  var subgroups = [];
  aStacked.map(function(a1) {
    var keys1 = Object.keys(a1);
    keys1.map(function(b1) {
      if (b1 != 'name' && subgroups.indexOf(b1) === -1) {
        subgroups.push(b1);
      }
    });
  });
  //endregion
  var groups = [];
  aStacked.map(function(item) {
    var vGroup = item.name;
    if (groups.indexOf(vGroup) === -1) {
      groups.push(vGroup);
    }
  });
  // Add X axis
  var x = d3.scaleBand()
    .domain(groups)
    .range([0, height])
    .padding(0.2);
  svg.append('g')
    .call(d3.axisLeft(x)
      .tickSize(7)
      .tickSizeOuter(0))
  //set color for domain and ticks
    .style("color", '#909090');

  // Add Y axis
  var y = d3.scaleLinear()
    .domain([0, 130])
    .range([0, withReduce]);
  svg.append('g')
    .attr('transform', 'translate(0,' + height + ')')
    .call(
      d3.axisBottom(y)
      .tickSize(0))
    .call(
			g => g.selectAll('.tick line')
			.clone()
			.attr('stroke-opacity', 0.1)
			.attr('y2', -height))
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
    .selectAll('text')
      .attr('fill', '#909090');;

  // color palette = one color per subgroup
  var colorPie = ["#26a7e9", "#a5cd39", "#dd6a6a", "#ffa700", "#00c1b2"];
  var color = d3.scaleOrdinal()
    .domain(subgroups)
    .range(colorPie);
    
  var stackedData = d3.stack().keys(subgroups)(aStacked);

  // Show the bars
  svg.append('g')
    .selectAll('g')
    // Enter in the stack data = loop key per key = group per group
    .data(stackedData)
    .enter().append('g')
    .attr('fill', function(d) { return color(d.key); })
    .selectAll('rect')
    // enter a second time = loop subgroup per subgroup to add all rectangles
    .data(function(d) { return d; })
    .enter().append('rect')
      .attr('x', function(d) { return y(d[0]); })
      .attr('height', x.bandwidth())
      .attr('y', function(d) { return x(d.data.name); })
      .attr('width', function(d) { return y(d[1]) - y(d[0]); })
      .on('click', function(_, d) {
        var subgroupName = d3.select(this.parentNode).datum().key;
        var subgroupValue = d.data[subgroupName];
        console.log('group: ' + subgroupName + ', value: ' + subgroupValue);
      });
			
	//Add X axis label:
  addText(svg, 'end', 16, 0, (withReduce  / 2) + margin.top, height + margin.left, '#808080', '', getAxisX());
	
	//Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), 0  -margin.bottom + 25, '#808080', '', getAxisY(), function () {
    modalData();
  });

  var withReduce = width - 100;
  var factorBack = margin.top;
  addText(svg, 'start', 16, 0, withReduce + margin.right - 10, 0, '#808080', '', getAxisX());
  for (var index = 0; index < aCategoryX.length; index++) {
    var item = aCategoryX[index];

    addText(svg, 'start', 12, 0, withReduce + margin.right + 10, factorBack, '#808080', `id_${'$'}{index}`, item, function () {
      var id = this.id;
      adminStacked(id, subgroups);
    });

    addCircle(svg, withReduce + margin.right - 5, factorBack - 5, 5, colorPie[indexCircle(index)], `idcircle_${'$'}{index}`,
    function () {
      return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
    },
    function () {
      var id = this.id;
      adminStacked(id, subgroups);
    });

    factorBack += 20;
  }
}
"""
	}
}