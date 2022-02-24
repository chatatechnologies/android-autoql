package chata.can.chata_ai.pojo.html

object StackedBar
{
	fun getStackedBar(): String
	{
		return """function setStackedBar() {
  margin.left = margin.left + 50;
  margin.bottom = margin.bottom - 30;
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
    .append('g')
		.attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`)

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
    .range([0, height])
    .padding(0.2);
  svg.append('g')
    .call(d3.axisLeft(x)
      .tickSize(7)
      .tickSizeOuter(0)
			.tickFormat(splitAxis)
		)
  //set color for domain and ticks
    .style("color", '#909090');

  // Add Y axis
  var y = d3.scaleLinear()
    .domain([0, getStackedMax()])
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
      .attr('x', function(d) { return y(d[0]); })
      .attr('height', x.bandwidth())
      .attr('y', function(d) { return x(d.data.name); })
      .attr('width', function(d) { return y(d[1]) - y(d[0]); })
      .on('click', function(_, d) {
				var idParent = this.id;
        var subgroupName = d3.select(this.parentNode).datum().key;
        var request = isCurrency ? `${'$'}{idParent}_${'$'}{subgroupName}` : `${'$'}{subgroupName}_${'$'}{idParent}`;
        drillDown(request);
      });
			
	//Add X axis label:
  addText(svg, 'end', 16, -90, sizeByLetter(axisX.length) - height / 2, -margin.bottom - 45, '#808080', axisX, getAxisX(), function () {
    modalCategories(TypeManage.DATA, this.id);
  });

  //Add Y axis label:
  addText(svg, 'end', 16, 0, (withReduce  / 2) + sizeByLetter(axisX.length), height + margin.bottom - 5, '#808080', axisMiddle, axisMiddle + '▼', function () {
    modalCategories(TypeManage.CATEGORIES);
  });

  var factorBack = margin.top;
  addText(svg, 'start', 14, 0, withReduce + margin.right - 10, 0, '#808080', axisY, getAxisY(), function () {
    modalCategories(TypeManage.DATA, this.id);
  });
  var index = 0;
  getCategoriesStack().forEach(item => {
    if (!indexIgnore.includes(index)) {
      addText(svg, 'start', 12, 0, withReduce + margin.right + 10, factorBack, '#808080', `id_${'$'}{index}`, item, function () {
        var id = this.id;
        adminStacked(id);
      });

      addCircle(svg, withReduce + margin.right - 5, factorBack - 5, 5, colorPie[indexCircle(index)], `idcircle_${'$'}{index}`,
      function () {
        return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
      },
      function () {
        var id = this.id;
        adminStacked(id);
      });
      factorBack += 15;
    }
    index++;
  });
}
"""
	}
}