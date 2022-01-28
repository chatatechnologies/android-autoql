package chata.can.chata_ai.pojo.html

object MultiLines
{
	fun getMultiLine(): String
	{
		return """function setMultiLine() {
  margin.left = margin.left + 10;
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
    .attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

  var withReduce = width - 100;
	var keys1 = Object.keys(dataTmp[0]);
  const subgroups = keys1.slice(1);
	
	var domain1, domain2;
	var isNegative = getNegativeValue();
  if (isNegative) {
    var max = getMaxValue();
    var min = getMinValue();

    domain1 = min * 1.005;
    domain2 = max * 1.005;
  } else {
    domain1 = 0;
    domain2 = getMaxValue();
  }
  
  // Reformat the data: we need an array of arrays of {x, y} tuples
  var dataReady = subgroups.map( function(grpName) { // .map allows to do something for each element of the list
    return {
      name: grpName,
      values: dataTmp.map(function(d) {
        return {subname: d.name, value: +d[grpName]};
      })
    };
  });

  var myColor = d3.scaleOrdinal()
    .domain(subgroups)
    .range(colorPie);

    // Add X axis --> it is a date format
  var x = d3.scaleBand()
    .domain(dataTmp.map(function(d) { return d.name; }))
    .range([0, withReduce])
    .padding(1);

  var axis = axisMulti(svg, false, x, height, 10, splitAxis);
  axis = axis
  //Remove line on domain for X axis
  .call(g => g.select('.domain').remove())
  //region set opacity for each tick item
  .call(
    g => g.selectAll('.tick line')
    .attr('opacity', 0.2))
  completeAxisMultiple(axis, -10, 5, -45);

  // Add Y axis
  var y = d3.scaleLinear()
    .domain([domain1, domain2])
    .range([height, 0]);
  var axis = axisMulti(svg, true, y, 0, 0, formatAxis);
	axis = axis
    .call(
      g => g.selectAll('.tick line')
      .clone()
      .attr('stroke-opacity', 0.1)
      .attr('x2', withReduce)
    )
    //Remove line on domain for Y axis
    .call(g => g.select('.domain').remove())
  completeAxisMultiple(axis, 0, 0, 0);

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
          var mValue = aDrillData[indexData][index];
          var value = `${'$'}{mValue}_${'$'}{index}`;
          drillDown(value);
	      }
	    });
		
	//Add X axis label:
  addText(svg, 'end', 16, 0, ((withReduce + margin.right + 10) / 2) + margin.top, height + margin.bottom, '#808080', axisY, getAxisY(), function () {
    modalCategories(TypeManage.PLAIN, this.id);
  });

  //Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), -margin.left + 12, '#808080', axisX, getAxisX(), function () {
    modalCategories(TypeManage.SELECTABLE, this.id);
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
}
"""
	}
}