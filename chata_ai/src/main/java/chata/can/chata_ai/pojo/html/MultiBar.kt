package chata.can.chata_ai.pojo.html

object MultiBar
{
	fun getMultiBar(): String
	{
		return """function setMultiBar() {
  var is3 = is3Columns();
	var marginLeft = margin.left + 15;
	var marginBottom = margin.bottom + 10;
	var svg = d3.select('body').append('svg')
		.attr('width', width + marginLeft + margin.right)
		.attr('height', height + margin.top + marginBottom + 10)
		.append('g')
		.attr('transform', `translate(${'$'}{marginLeft}, ${'$'}{margin.top})`);
		
  var aCategoryTmp = is3 ? getCategoriesStack(): getMultiCategory();
  var hasCategories = (aCategoryTmp.length - indexIgnore.length >= 3);
  
  var spaceCategory;
  if (hasCategories) {
    spaceCategory = 100;
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
  completeAxisMultiple(axis, -5, -10, -45);

    // Add Y axis
  const y = d3.scaleLinear()
    .domain([domain1, domain2])
    .range([0, withReduce]);
	var refererZero = y(0);

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
    completeAxisMultiple(axis, 0, 0, -45);

  // Another scale for subgroup position?
  const xSubgroup = d3.scaleBand()
    .domain(subgroups)
    .range([x.bandwidth(), 0])
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
      .attr('transform', d => `translate(0, ${'$'}{x(d.name)})`)
			.attr('id', function(_,i){ return 'item_' + i;})
    .selectAll('rect')
    .data(function(d) { return subgroups.map(function(key) { return {key: key, value: d[key]}; }); })
    .join('rect')
      .attr('x', function (d) {
        var withValue = y(d.value);
        if (isNegative) {
          if (refererZero < withValue) {
            withValue = refererZero;
          } else {
            withValue = y(d.value);
          }
        } else {
          withValue = 0;
        }
        return withValue;
      })
      .attr('y', d => xSubgroup(d.key))
			.attr('width', function (d) {
        var withValue = y(d.value);
        if (isNegative) {
          if (refererZero < withValue) {
            withValue = withValue - refererZero;
          } else {
            withValue = refererZero - y(d.value);
          }
        }
        return withValue;
      })
      .attr('height', xSubgroup.bandwidth())
      .attr('fill', d => color(d.key))
			.attr('id', function (_,i) { return 'child_' + i; })
      .on('click', function () {
        var idParent = this.parentNode.id;
        if (is3) {
          var idChild = this.id;

          var aParent = idParent.split('_');
          var aChild = idChild.split('_');

          var indexParent = aParent[1];
          var indexChild = aChild[1];

          var prefix = groups[indexParent];
          var suffix = subgroups[indexChild];

          var value = `${'$'}{prefix}_${'$'}{suffix}`;
          drillDown(value);
        } else {
          var aData = idParent.split('_');
          if (aData.length > 0)
          {
            var index = aData[1];
            var mValue = aDrillData[indexData][index];
            var value = `${'$'}{mValue}_${'$'}{index}`;
            drillDown(value);
          }
        }
      });
			
	var xTitle = is3 ? axisMiddle + '▼' : getAxisY();
  var xTitleId = is3 ? axisX : axisY;
  var yTitle = is3 ? getAxisY() : getAxisX();
  var yTitleId = is3 ? axisY : axisX;
  var menuTitle = is3 ? getAxisX() : 'Category';
  var menuTitleId = is3 ? axisX : '';
			
	//Add X axis label:
  addText(svg, 'end', 16, 0, (withReduce / 2) + sizeByLetter(xTitle.length), height + marginBottom - 5, '#808080', xTitleId, xTitle, function () {
    var type = is3 ? TypeManage.CATEGORIES : TypeManage.SELECTABLE;
    modalCategories(type, this.id);
  });

  //Y axis label:
  addText(svg, 'end', 16, -90, margin.top + (-height / 2), -marginLeft + 15, '#808080', yTitleId, yTitle, function () {
    var type = is3 ? TypeManage.DATA : TypeManage.PLAIN;
    modalCategories(type, this.id);
  });
	
  var factorBack = margin.top;
    if (hasCategories)
  {
    addText(svg, 'start', 16, 0, withReduce + margin.right - 10, 0, '#808080', menuTitleId, menuTitle, function () {
      if (is3) {
        modalCategories(TypeManage.DATA, this.id);
      }
    });

    var index = 0;
    aCategoryTmp.forEach(item => {
      if (!indexIgnore.includes(index))
      {
        addText(svg, 'start', 12, 0, withReduce + margin.right + 10, factorBack, '#808080', `id_${'$'}{index}`, item, function () {
          var id = this.id;
          is3 ? console.log('data stacked') : adminMulti(id, subgroups);
        });
        addCircle(svg, withReduce + margin.right - 5, factorBack - 5, 5, colorPie[indexCircle(index)], `idcircle_${'$'}{index}`,
        function () {
          return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
        },
        function () {
          var id = this.id;
          is3 ? console.log('data stacked') : adminMulti(id, subgroups);
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