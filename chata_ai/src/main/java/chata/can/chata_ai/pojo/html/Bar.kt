package chata.can.chata_ai.pojo.html

object Bar
{
	fun getBar(): String
	{
		return """function setBar() {
	var svg = d3.select('body').append('svg')
		.attr('width', width + margin.bottom + margin.right)
		.attr('height', height + margin.top + margin.left)
		.append('g')
		.attr('transform', 'translate(' + margin.bottom + ',' + margin.top + ')');

	var x = d3.scaleBand()
		.range([height, 0])
		.padding(0.1);
	var y = d3.scaleLinear()
		.range([0, width]);
		
  var minDomain = 0;
  if (minValue2 === -1) {
    var values = [];
    for (let index = 0; index < data.length; index++) {
      const item = data[index];
      values.push(item.value);
    }
    var minimum = Math.min.apply(Math, values);
    var residue = minimum % 5000;
    minimum =  minimum - residue;
    if (minimum > 0) {
      minDomain = minimum;
    }
  }

	x.domain(data.map(function(d) { return d.name; }));
	y.domain([minDomain, d3.max(data, function(d) { return d.value; })]);

	svg.selectAll()
		.data(data)
		.enter().append('rect')
		.style('fill', function(d) {
			return scaleColorBi(d.value);
		})
		.attr('x', function(d) { return x(d.value); })
		.attr('width', function(d) { return y(d.value); })
		.attr('y', function(d) { return x(d.name); })
		.attr('height', x.bandwidth())
		.on('click', function(_, d) {
			var index = data.indexOf(d);
      var value = drillX[index];
      drillDown(value);
		});

	svg.append('g')
		.attr('transform', 'translate(0,' + height + ')')
		.call(
			d3.axisBottom(y)
			.tickSize(0)
			//format data for each number on Y axis
			.tickFormat(x => `${'$'}{fformat(x)}`))
		.call(
			g => g.selectAll('.tick line')
			.clone()
			.attr('stroke-opacity', 0.1)
			.attr('y2', -height))
		//Remove line on domain for X axis
		.call(g => g.select('.domain').remove())
		.selectAll('text')
		.attr('transform', 'translate(10,10)rotate(-45)')
		.attr('fill', '#909090');

	svg.append('g')
		.call(
			d3.axisLeft(x)
      .tickFormat(x =>`${'$'}{getFirst10(x)}`))
		//region set opacity for each tick item
		.call(g => g.selectAll('.tick line')
		.attr('opacity', 0.2))
		//endregion
		//Remove line on domain for Y axis
		.call(g => g.select('.domain').remove())
		.selectAll('text')
		.attr('transform', 'translate(-10,-25)rotate(-45)')
		.attr('fill', '#909090');
	
	  //Add X axis label:
	  addText(svg, 'end', 16, 0, (width / 2) + margin.top, height + margin.left - 10, '#808080', '', axisY);
	
	  //Y axis label:
	  addText(svg, 'end', 16, -90, margin.top + (-height / 2), -margin.bottom + 20, '#808080', '', axisX);
	}
"""
	}
}