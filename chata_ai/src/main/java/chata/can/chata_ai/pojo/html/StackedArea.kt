package chata.can.chata_ai.pojo.html

object StackedArea {
	fun getStackedArea(): String {
		return """function setStackedArea() {
  // Create SVG and padding for the chart
  const svg = d3
    .select('body').append('svg')
    .attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)

  const strokeWidth = 1.5;
  const svgBase = svg.append('g')
    .attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  const grp = svgBase
    .append('g')
    .attr('transform', `translate(-${'$'}{margin.left}, 0)`);

  var first = stackedArea[0];
  var properties = [];
  if (first !== undefined) {
    var keys1 = Object.keys(first);
    keys1.map(function(b1) {
      if (b1 != 'category' && properties.indexOf(b1) === -1) {
        properties.push(b1);
      }
    }); 
  }

  // Create stack
  const stack = d3.stack().keys(properties);
  const stackedValues = stack(stackedArea);
  const stackedData = [];
  // Copy the stack offsets back into the data.
  stackedValues.forEach((layer, index) => {
    const currentStack = [];
    layer.forEach((d, i) => {
      currentStack.push({
        values: d,
        category: stackedArea[i].category
      });
    });
    stackedData.push(currentStack);
  });

  // Create scales
  const yScale = d3
    .scaleLinear()
    .range([height, 0])
    .domain([
      0,
      d3.max(stackedValues[stackedValues.length - 1], dp => dp[1])]
    );
  const xScale = d3
    //use for to scale from start to final
    .scalePoint()
    .range([0, width])
    .domain(
      stackedArea.map(function(d) { return d.category; })
    )

  const area = d3
    .area()
    .x(d => xScale(d.category))
    .y0(d => yScale(d.values[0]))
    .y1(d => yScale(d.values[1]));

  const series = grp
    .selectAll('.series')
    .data(stackedData)
    .enter()
    .append('g')
    .attr('class', 'series');

  var color = d3.scaleOrdinal()
    .domain(properties)
    .range(colorPie);

  series
    .append('path')
    .attr('transform', `translate(${'$'}{margin.left},0)`)
    .style('fill', (d, i) => {
      return color(properties[i])
    })
    .attr('d', d => area(d));

  // Add the X Axis
  svgBase
    .append('g')
    .attr('transform', `translate(0,${'$'}{height})`)
    .call(
      d3
      .axisBottom(xScale)
      .tickFormat(x =>`${'$'}{getFirst10(x)}`)
    )
    //Remove line on domain for X axis
    .call(g => g.select('.domain').remove())
    //Remove line on domain for X axis
    .call(
      g => g.selectAll('.tick line')
      .attr('opacity', 0.2)
    )
    .selectAll('text')
    //rotate text
    .attr('transform', 'translate(-8,3)rotate(-45)')
    //Set color each item on X axis
    .attr('fill', '#909090')
    .style('text-anchor', 'end');

  // Add the Y Axis
  svgBase
    .append('g')
    .attr('transform', `translate(0, 0)`)
    .call(
      d3.axisLeft(yScale)
      //remove short line for Y axis
	    .tickSize(0)
      //format data for each number on Y axis
	    .tickFormat(x => `${'$'}{fformat(x)}`)
    )
    //region set lines by each value for y axis
	  .call(
	    g => g.selectAll('.tick line')
	    .clone()
	    .attr('stroke-opacity', 0.1)
	    .attr('x2', width)
    )
	  //endregion
    //Remove line on domain for Y axis
	  .call(g => g.select('.domain').remove())
	  .selectAll('text')
		.attr('transform', 'translate(0,-5)')  
	  .attr('fill', '#909090');
}
"""
	}
}