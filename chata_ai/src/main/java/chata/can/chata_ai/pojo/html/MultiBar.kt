package chata.can.chata_ai.pojo.html

object MultiBar
{
	fun getMultiBar(): String
	{
		return """
function setMultiBar() {
  var svg = svgMulti().append('g')
		.attr('transform', `translate(${'$'}{margin.bottom}, ${'$'}{margin.top})`);

  var withReduce = width - 100;
	var keys1 = Object.keys(dataTmp[0]);
  const subgroups = keys1.slice(1);

  // List of groups = species here = value of the first column called group -> I show them on the X axis
  const groups = dataTmp.map(d => d.name);

  // Add X axis
  const x = d3.scaleBand()
    .domain(groups)
    .range([height, 0])
    .padding([0.2]);
  var axis = axisMulti(svg, true, x, 0, 5, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
  completeAxisMultiple(axis, -10, -25, -45);

    // Add Y axis
  const y = d3.scaleLinear()
    .domain([0, getMaxValue()])
    .range([0, withReduce]);

  axis = axisMulti(svg, false, y, height, 0, formatAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set lines by each value for y axis
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('y2', -height))
  completeAxisMultiple(axis, 10, 10, -45);

  // Another scale for subgroup position?
  const xSubgroup = d3.scaleBand()
    .domain(subgroups)
    .range([x.bandwidth(), 0])
    .padding([0.05]);//TODO CHANGE

    // color palette = one color per subgroup
  const color = d3.scaleOrdinal()
    .domain(subgroups)
    .range(colorPie);

    // Show the bars
  svg.append('g')
    .selectAll('g')
    // Enter in data = loop group per group
    .data(dataTmp)
    .join('g')
      .attr('transform', d => `translate(0, ${'$'}{x(d.name)})`)
			.attr('id', function(_,i){ return 'item_' + i;})
    .selectAll('rect')
    .data(function(d) { return subgroups.map(function(key) { return {key: key, value: d[key]}; }); })
    .join('rect')
      .attr('x', d => 0)
      .attr('y', d => xSubgroup(d.key))
      .attr('height', xSubgroup.bandwidth())
      .attr('width', d => y(d.value))
      .attr('fill', d => color(d.key))
      .on('click', function () {
        var idParent = this.parentNode.id
        var aData = idParent.split('_')
        if (aData.length > 0)
        {
          var index = aData[1];
          var value = `${'$'}{drillX[index]}_${'$'}{index}`;
          drillDown(value)
        }
      });
			
	//Add X axis label:
  addText(svg, 'end', 16, 0, (withReduce / 2) + margin.top, height + margin.bottom - 25, '#808080', axisY, getAxisY(), function () {
    modalCategories(TypeManage.SELECTABLE, this.id);
  });

  //Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), -margin.left - 20, '#808080', axisX, getAxisX(), function () {
    modalCategories(TypeManage.PLAIN, this.id);
  });
	
  var factorBack = margin.top;
	var aCategoryTmp = getMultiCategory();
  if (aCategoryTmp.length - indexIgnore.length >= 3)
  {
    addText(svg, 'start', 16, 0, withReduce + margin.right - 10, 0, '#808080', '', 'Category');
    for (var index = 0; index < aCategoryTmp.length; index++)
    {
      if (!indexIgnore.includes(index)) 
      {
        var item = aCategoryTmp[index];
        addText(svg, 'start', 12, 0, withReduce + margin.right + 10, factorBack, '#808080', `id_${'$'}{index}`, item, function () {
          var id = this.id;
          adminMulti(id, subgroups);
        });
        addCircle(svg, withReduce + margin.right - 5, factorBack - 5, 5, colorPie[indexCircle(index)], `idcircle_${'$'}{index}`,
        function () {
          return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
        },
        function () {
          var id = this.id;
          adminMulti(id, subgroups);
        });
        factorBack += 20;
      }
    }
  }
}"""
	}
}