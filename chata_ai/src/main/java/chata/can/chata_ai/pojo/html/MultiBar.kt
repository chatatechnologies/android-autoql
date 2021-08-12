package chata.can.chata_ai.pojo.html

object MultiBar
{
	fun getMultiBar(): String
	{
		return """
function setMultiBar() {
  var svg = svgMulti().append('g')
		.attr('transform', 'translate(' + margin.bottom + ',' + margin.top + ')');

  var keys1 = Object.keys(data[0]);
  const subgroups = keys1.slice(1);

  // List of groups = species here = value of the first column called group -> I show them on the X axis
  const groups = data.map(d => d.name);

  // Add X axis
  const x = d3.scaleBand()
    .domain(groups)
    .range([height, 0])
    .padding([0.2]);
  svg.append("g")
    .call(
      d3.axisLeft(x)
        .tickFormat(x => `${'$'}{x.split('_')[0]}`)
    )
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set opacity for each tick item
    .call(g => g.selectAll('.tick line')
      .attr('opacity', 0.2))
    .selectAll('text')
    //rotate text
    .attr('transform', 'translate(-10,-25)rotate(-45)')
    .attr('fill', '#909090')
    .style('text-anchor', 'end');

    // Add Y axis
  const y = d3.scaleLinear()
    .domain([0, maxValue])
    .range([0, width]);

    svg.append("g")
    .attr('transform', `translate(0, ${'$'}{height})`)
    .call(
      d3.axisBottom(y)
      .tickFormat(x =>`${'$'}{fformat(x)}`)
    )
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //region set lines by each value for y axis
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('y2', -height))
    .selectAll('text')
    //rotate text
    .attr('transform', 'translate(10,10)rotate(-45)')
    .attr('fill', '#909090')
    .style('text-anchor', 'end');

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
    .data(data)
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
    svg.append('text')
			.attr('text-anchor', 'end')
			.style('font-size', 16)
			.attr('x', (width / 2) + margin.top)//for center
			.attr('y', height + margin.bottom - 10)//for set on bottom with -10
			.attr('fill', '#808080')
			.attr('id', axisY)
			.text(getAxisY())
			.on('click', function () {
	      modalCategories(TypeManage.SELECTABLE, this.id);
	    });

    //Y axis label:
    svg.append('text')
			.attr('text-anchor', 'end')
			.style('font-size', 16)
			.attr('transform', 'rotate(-90)')
			.attr('y', -margin.left - 20)
			.attr('x', margin.top + (-height / 2))//center Y axis title
			.attr('fill', '#808080')
			.attr('id', axisX)
			.text(getAxisX())
			.on('click', function () {
	      modalCategories(TypeManage.PLAIN, this.id);
	    });
}"""
	}
}