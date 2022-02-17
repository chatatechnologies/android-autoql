package chata.can.chata_ai.pojo.html

object StackedColumn
{
	fun getStackedColumn(): String
	{
		return """function setStackedColumn() {
  margin.left = margin.left + 15;
  margin.bottom = margin.bottom + 10;
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
    .append('g')
		.attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  var withReduce = width - 105;
	//region rewrite
  var subgroups = [];
	var stackedData = getStackedData();
  stackedData.map(function(a1) {
    var keys1 = Object.keys(a1);
    keys1.map(function(b1) {
      if (b1 != 'name' && subgroups.indexOf(b1) === -1) {
        subgroups.push(b1);
      }
    });
  });
  //endregion
  var groups = [];
  stackedData.map(function(item) {
    var vGroup = item.name;
    if (groups.indexOf(vGroup) === -1) {
      groups.push(vGroup);
    }
  });

  // Add X axis
  var x = d3.scaleBand()
    .domain(groups)
    .range([0, withReduce])
    .padding(0.2);

  var axis = axisMulti(svg, false, x, height, 5, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    completeAxisMultiple(axis, -5, 0, -45);

  // Add Y axis
  var y = d3.scaleLinear()
    .domain([0, getStackedMax()])
    .range([ height, 0 ]);

  axis = axisMulti(svg, true, y, 0, 0, formatAxis);
  axis = axis
    //region set lines by each value for y axis
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('x2', withReduce))
      .call(g => g.select('.domain').remove())
  completeAxisMultiple(axis, 0, 0, 0);

  // color palette = one color per subgroup
  var color = d3.scaleOrdinal()
    .domain(subgroups)
    .range(colorPie);

  var stackedData = d3.stack().keys(subgroups)(stackedData);

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
    .enter()
    .append('rect')
    .attr('id', function(item, _) { return `${'$'}{item.data.name}`;})
    .attr('x', function(d) { return x(d.data.name); })
    .attr('height', function(d) { return y(d[0]) - y(d[1]); })
    .attr('y', function(d) { return y(d[1]); })
    .attr('width', x.bandwidth())
    .on('click', function(_, d) {
      var idParent = this.id;
      var subgroupName = d3.select(this.parentNode).datum().key;
      var subgroupValue = d.data[subgroupName];
      drillDown(subgroupName + '_' + idParent);
    });

  //on left
  addText(svg, 'end', 16, /*angle*/-90, /*X*/-height/2 + sizeByLetter(axisMiddle.length), /*Y*/-margin.left + 15, '#808080', '', axisMiddle + '▼', function () {
    modalCategories(TypeManage.CATEGORIES);
  });

  //on bottom
  addText(svg, 'end', 16, /*angle*/0, /*X*/(withReduce / 2) + sizeByLetter(axisY.length), /*Y*/height + margin.bottom - 0, '#808080', axisY, getAxisY(), function () {
    modalCategories(TypeManage.DATA, this.id);
  });

  var factorBack = margin.top;
  addText(svg, 'start', 14, 0, withReduce + margin.right - 15, 0, '#808080', axisX, getAxisX(true), function () {
    modalCategories(TypeManage.DATA, this.id);
  });
  for (var index = 0; index < getCategoriesStack().length; index++) {
		if (indexIgnore.includes(index)) continue;
    var item = getCategoriesStack()[index];

    addText(svg, 'start', 12, 0, withReduce + margin.right + 5, factorBack, '#808080', `id_${'$'}{index}`, item, function () {
      var id = this.id;
      adminStacked(id, subgroups);
    });

    addCircle(svg, withReduce + margin.right - 7, factorBack - 5, 5, colorPie[indexCircle(index)], `idcircle_${'$'}{index}`,
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