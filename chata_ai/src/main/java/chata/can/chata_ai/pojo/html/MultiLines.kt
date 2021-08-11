package chata.can.chata_ai.pojo.html

object MultiLines
{
	fun getMultiLine(): String
	{
		return """
function setMultiLine() {
  svgMulti().append('g')
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
			
	// Add the points
  svg
    // First we need to enter in a group
    .selectAll()
    .data(dataReady)
    .enter()
      .append('g')
      .style("fill", function(d){ return myColor(d.name) })
    // Second we need to enter in the 'values' part of this group
    .selectAll()
    .data(function(d){ return d.values })
    .enter()
    .append("circle")
      .attr('id', function(_, i){ return 'item_' + i;})
      .attr("cx", function(d) { return x(d.subname) } )
      .attr("cy", function(d) { return y(d.value) } )
      .attr("r", 5)
      .attr("stroke", "white")
    .on('click', function () {
      var idParent = this.id;
      var aData = idParent.split('_');
      if (aData.length > 0)
      {
        var index = aData[1];
        var value = `${'$'}{drillX[index]}_${'$'}{index}`;
        drillDown(value);
      }
    });
		
		//Add X axis label:
    svg.append('text')
			.attr('text-anchor', 'end')
			.style('font-size', 16)
			.attr('x', (width / 2) + margin.top)//for center
			.attr('y', height + margin.bottom - 10)//for set on bottom with -10
			.attr('fill', '#808080')
			.attr('id', axisX)
			.text(getAxisX())
			.on('click', function () {
        modalCategories(TypeManage.PLAIN, this.id);
			});

		//Y axis label:
    svg.append('text')
			.attr('text-anchor', 'end')
			.style('font-size', 16)
			.attr('transform', 'rotate(-90)')
			.attr('y', -margin.left + 20)
			.attr('x', margin.top + (-height / 2))//center Y axis title
			.attr('fill', '#808080')
			.attr('id', axisY)
			.text(getAxisY())
			.on('click', function () {
        modalCategories(TypeManage.SELECTABLE, this.id);
			});
}"""
	}
}