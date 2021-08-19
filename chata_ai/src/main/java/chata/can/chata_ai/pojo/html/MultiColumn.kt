package chata.can.chata_ai.pojo.html

object MultiColumn
{
	fun getMultiColumn(): String
	{
		return """
function setMultiColumn() {
  var svg = svgMulti().append('g')
    .attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

	var withReduce = width - 100;
  var keys1 = Object.keys(dataTmp[0]);
  const subgroups = keys1.slice(1);

  // List of groups = species here = value of the first column called group -> I show them on the X axis
  const groups = dataTmp.map(d => d.name);

  // Add X axis
  const x = d3.scaleBand()
    .domain(groups)
    .range([0, withReduce])
    .padding([0.2]);
  var axis = axisMulti(svg, false, x, 0, height, splitAxis);
	axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    .selectAll('text')
    //rotate text
    .attr('transform', 'translate(10,10)rotate(-45)')
    .attr('fill', '#909090')
    .style('text-anchor', 'end');

  // Add Y axis
  const y = d3.scaleLinear()
    .domain([0, maxValue])
    .range([ height, 0 ]);
  svg.append('g')
    .call(
      d3.axisLeft(y)
      //remove short line for Y axis
      .tickSize(0)
      .tickFormat(x =>`${'$'}{fformat(x)}`))
    //region set lines by each value for y axis
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('x2', withReduce))
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
    .selectAll('text')
	  .attr('transform', 'translate(0,0)rotate(0)')
    .attr('fill', '#909090');

  // Another scale for subgroup position?
  const xSubgroup = d3.scaleBand()
    .domain(subgroups)
    .range([0, x.bandwidth()])
    .padding([0.05]);

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
      .attr('transform', d => `translate(${'$'}{x(d.name)}, 0)`)
			.attr('id', function(_,i){ return 'item_' + i;})
    .selectAll('rect')
    .data(function(d) { return subgroups.map(function(key) { return {key: key, value: d[key]}; }); })
    .join('rect')
      .attr('x', d => xSubgroup(d.key))
      .attr('y', d => y(d.value))
      .attr('width', xSubgroup.bandwidth())
      .attr('height', d => height - y(d.value))
      .attr('fill', d => color(d.key))
      .on('click', function () {
        var idParent = this.parentNode.id;
        var aData = idParent.split('_');
        if (aData.length > 0)
        {
          var index = aData[1];
          var value = `${'$'}{drillX[index]}_${'$'}{index}`;
          drillDown(value)
        }
      });
			
	//Add X axis label:
  addText(svg, 'end', 16, 0, (withReduce / 2) + margin.top, height + margin.bottom - 10, '#808080', axisX, getAxisX(), function () {
    modalCategories(TypeManage.PLAIN, this.id);
	});

	//Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), -margin.left + 20, '#808080', axisY, getAxisY(), function () {
    modalCategories(TypeManage.SELECTABLE, this.id);
	});
			
	//variable global
  var aCategory = ['Cost', 'Rate (Cost)', 'Revenue', 'Rate (Revenue)', 'Ticket Total Amount'];
  var factorBack = margin.top;
  for (const index in aCategory)
  {
    var item = aCategory[index];
    svg.append('text')
      .style('font-size', 12)
      .attr('x', withReduce + margin.right + 10)
      .attr('y', factorBack)
      .attr('fill', '#808080')
			.attr('id', `id_${'$'}{index}`)
      .text(item)
			.on('click', function() {
        var id = this.id;
        adminMulti(id, subgroups);
      });

    svg.append('circle')
      .attr("cx", withReduce + margin.right - 5)
      .attr("cy", factorBack - 5)
      .attr("r", 5)
      .attr("fill", colorPie[index])
			.attr('id', `idcircle_${'$'}{index}`)
			.attr('style', function () {
        return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
      })
			.on('click', function() {
        var id = this.id;
        adminMulti(id, subgroups);
      });

    factorBack += 20;
  }
}"""
	}
}