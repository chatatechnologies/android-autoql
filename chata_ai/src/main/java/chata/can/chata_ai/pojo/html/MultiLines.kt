package chata.can.chata_ai.pojo.html

object MultiLines
{
	fun getMultiLine(): String
	{
		return """
			function setMultiLine() {
  // append the svg object to the body of the page
  var svg = d3.select('body').append('svg')
    .attr('width', width + margin.bottom + margin.right)
    .attr('height', height + margin.top + margin.left)
  .append('g')
    .attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  var keys1 = Object.keys(data[0]);
  const allGroups = keys1.slice(1);
  
  // Reformat the data: we need an array of arrays of {x, y} tuples
  var dataReady = allGroups.map( function(grpName) { // .map allows to do something for each element of the list
    return {
      name: grpName,
      values: data.map(function(d) {
        return {subname: d.name, value: +d[grpName]};
      })
    };
  });

  var myColor = d3.scaleOrdinal()
    .domain(allGroups)
    .range(colorPie);

    // Add X axis --> it is a date format
  var x = d3.scaleBand()
    .domain(data.map(function(d) { return d.name; }))
    .range([0, width])
    .padding(1);

  svg.append("g")
  .attr('transform', `translate(0,${'$'}{height})`)
  .call(
    d3.axisBottom(x)
    .tickFormat(x =>`${'$'}{x.split('_')[0]}`)
  )
  //Remove line on domain for X axis
  .call(g => g.select('.domain').remove())
  //region set opacity for each tick item
  .call(
    g => g.selectAll('.tick line')
    .attr('opacity', 0.2))
  .selectAll('text')
  //rotate text
  .attr('transform', 'translate(10,10)rotate(-45)')
  //Set color each item on X axis
  .attr('fill', '#909090')
  .style('text-anchor', 'end');

    // Add Y axis
  var y = d3.scaleLinear()
    .domain([0, 3360.0])//maxValue is here
    .range([height, 0]);
  svg.append("g")
    .call(
      d3.axisLeft(y)
      .tickSize(0)
      .tickFormat(x => `${'$'}{fformat(x)}`)
    )
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('x2', width)
    )
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
    .selectAll('text')
    .attr('fill', '#909090');

    // Add the lines
  var line = d3.line()
    .x(function(d) { return x(d.subname) })
    .y(function(d) { return y(d.value) });
  svg.selectAll("myLines")
    .data(dataReady)
    .enter()
    .append("path")
      .attr("d", function(d){ return line(d.values) } )
      .attr("stroke", function(d){ return myColor(d.name) })
      .style("stroke-width", 1)
      .style("fill", "none");
}"""
	}
}