package chata.can.chata_ai.pojo.html

object MultiColumn
{
	fun getMultiColumn(): String
	{
		return """function setMultiColumn() {
	margin.left = margin.left + 15;
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
		.attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

	var withReduce = width - 80;
  var keys1 = Object.keys(dataTmp[0]);
  const subgroups = keys1.slice(1);

  // List of groups = species here = value of the first column called group -> I show them on the X axis
  const groups = dataTmp.map(d => d.name);

  var max = Math.max(...aMax);
  var min = Math.min(...aMin);

  var domain1, domain2;
	var isNegative = getNegativeValue();
  if (isNegative) {
    domain1 = min * 1.005;
    domain2 = max * 1.005;
  } else {
    domain1 = 0;
    domain2 = getMaxValue();
  }

  //#region DEFINE Axis Y
  const x = d3.scaleBand()
    .domain(groups)
    .range([0, withReduce])
    .padding([0.2]);
  var axis = axisMulti(svg, false, x, height, 5, splitAxis);
  axis = axis
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    completeAxisMultiple(axis, 10, 10, -45);
    //#endregion
  
  //#region DEFINE Axis Y
  /* This scale produces negative output for negatve input */
  var yScale = d3.scaleLinear()
    .domain([domain1, domain2])
    .range([height, 0]);
	var refererZero = yScale(0);
  
  axis = axisMulti(svg, true, yScale, 0, 0, formatAxis);
  axis = axis
    //region set lines by each value for y axis
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('x2', withReduce))
      .call(g => g.select('.domain').remove())
  completeAxisMultiple(axis, 0, 0, 0);
  //#endregion

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
      .attr('y', function(d) {
        if (isNegative && yScale(d.value) > refererZero) {
          factor = refererZero;
        } else {
          factor = yScale( d.value)
        }
        return factor;
      })
      .attr('width', xSubgroup.bandwidth())
      .attr('height', d => Math.abs( yScale(d.value) - yScale(0)) )
      .attr('fill', d => color(d.key))
      .on('click', function () {
        var idParent = this.parentNode.id;
        var aData = idParent.split('_');
        if (aData.length > 0)
        {
          var index = aData[1];
          var mValue = aDrillData[indexData][index];
          var value = `${'$'}{mValue}_${'$'}{index}`;
          drillDown(value);
        }
      });
			
	//Add X axis label:
  addText(svg, 'end', 16, 0, withReduce / 2, height + margin.bottom, '#808080', axisX, getAxisX(), function () {
    modalCategories(TypeManage.PLAIN, this.id);
  });

  //Y axis label:
  addText(svg, 'end', 16, -90, -height / 2, -margin.left + 15, '#808080', axisY, getAxisY(), function () {
    modalCategories(TypeManage.SELECTABLE, this.id);
  });
}
"""
	}
}