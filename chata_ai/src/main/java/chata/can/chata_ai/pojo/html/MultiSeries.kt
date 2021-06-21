package chata.can.chata_ai.pojo.html

object MultiSeries
{
	fun getMultiSeries(): String
	{
		return """function setMultiSeries() {
  // append the svg object to the body of the page
  var svg = d3.select('body').append('svg')
    .attr('width', width + margin.bottom + margin.right)
    .attr('height', height + margin.top + margin.left)
  .append('g')
    .attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  var keys1 = Object.keys(data[0]);
  const subgroups = keys1.slice(1);

  // List of groups = species here = value of the first column called group -> I show them on the X axis
  const groups = data.map(d => d.name);

  // Add X axis
  const x = d3.scaleBand()
    .domain(groups)
    .range([0, width])
    .padding([0.2]);
  svg.append("g")
    .attr('transform', `translate(0, ${'$'}{height})`)
    .call(
      d3.axisBottom(x)
        .tickFormat(x => `${'$'}{x.split('_')[0]}`)
    )
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
      .attr('x2', width))
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
    .data(data)
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
      .on('click', function (d, i) {
        var idParent = this.parentNode.id
        var aData = idParent.split('_')
        if (aData.length > 0)
        {
          var index = aData[1]
          var value = drillX[index]
          drillDown(value)
        }
      });
}"""
	}
}