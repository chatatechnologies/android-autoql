package chata.can.chata_ai.pojo.html

object MultiColumn
{
	fun getMultiColumn(): String
	{
		return """function setMultiColumn() {
	var factor = nColumns === 3 ? 10 : 0;
	margin.left = margin.left + 15;
	margin.bottom = margin.bottom + factor;
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
		.attr('transform', `translate(${'$'}{margin.left}, ${'$'}{margin.top})`);

	var aCategoryTmp = nColumns == 3 ? getCategoriesStack(): getMultiCategory();
  var hasCategories = (aCategoryTmp.length - indexIgnore.length >= 3);

  var spaceCategory;
  if (hasCategories) {
    spaceCategory = 105;
  } else {
    spaceCategory = 0;
  }
  var withReduce = width - spaceCategory;
  var keys1 = Object.keys(dataTmp[0]);
  const subgroups = keys1.slice(1);

  // List of groups = species here = value of the first column called group -> I show them on the X axis
  const groups = dataTmp.map(d => d.name);

  var domain1, domain2;
	var isNegative = getNegativeValue();
  if (isNegative) {
	  var max = getMaxValue();
    var min = getMinValue();
	
    domain1 = min * 1.01;
    domain2 = max * 1.01;
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
    completeAxisMultiple(axis, -5, 3, -45);
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
			
	var is3Columns = nColumns === 3;
  var xTitle = is3Columns ? getAxisY() : getAxisX();
  var xTitleId = is3Columns ? axisY : axisX;
  var yTitle = is3Columns ? axisMiddle + '▼' : getAxisY();
  var yTitleId = is3Columns ? axisX : getAxisY();
  var menuTitle = is3Columns ? getAxisX(true) : 'Category';
  var menuTitleId = is3Columns ? axisX : 'Category';
			
	//Add X axis label:
  addText(svg, 'end', 16, 0, withReduce / 2 + sizeByLetter(xTitle.length), height + margin.bottom, '#808080', xTitleId, xTitle, function () {
    var type;
    if (nColumns === 3) type = TypeManage.DATA; else type = TypeManage.PLAIN;
    modalCategories(type, this.id);
  });

  //Y axis label:
  addText(svg, 'end', 16, -90, -height / 2, -margin.left + 15, '#808080', yTitleId, yTitle, function () {
    var type;
    if (nColumns === 3) type = TypeManage.CATEGORIES; else type = TypeManage.SELECTABLE;
    modalCategories(type, this.id);
  });
	
	var factorBack = margin.top;
  if (hasCategories)
  {
    addText(svg, 'start', 16, 0, withReduce + margin.right - 15, 0, '#808080', menuTitleId, menuTitle, function () {
      if (nColumns == 3) {
        modalCategories(TypeManage.DATA, this.id);
      }
    });

    var index = 0;
    aCategoryTmp.forEach(item => {
      if (!indexIgnore.includes(index))
      {
        addText(svg, 'start', 12, 0, withReduce + margin.right, factorBack, '#808080', `id_${'$'}{index}`, item, function () {
          var id = this.id;
          nColumns == 3 ? adminDataMulti(id, subgroups) : adminMulti(id, subgroups);
        });
        addCircle(svg, withReduce + margin.right - 10, factorBack - 5, 5, colorPie[indexCircle(index)], `idcircle_${'$'}{index}`,
        function () {
          return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
        },
        function () {
          var id = this.id;
          nColumns == 3 ? adminDataMulti(id, subgroups) : adminMulti(id, subgroups);
        });
        factorBack += 20;
      }
      index++;
    });
  }
}
"""
	}
}