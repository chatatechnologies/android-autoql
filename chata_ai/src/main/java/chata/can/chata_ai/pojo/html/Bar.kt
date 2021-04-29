package chata.can.chata_ai.pojo.html

object Bar
{
	fun getBar(): String
	{
		return """
function setBar() {
	var svg = d3.select('body').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
		.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

	var x = d3.scaleBand()
		.range([height, 0])
		.padding(0.1);
	var y = d3.scaleLinear()
		.range([0, width]);

	x.domain(data.map(function(d) { return d.name; }));
	y.domain([0, d3.max(data, function(d) { return d.value; })]);

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
		.on('click', function(d) {
			drillDown(d.value);
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
		.attr('fill', '#909090');

	svg.append('g')
		.call(
			d3.axisLeft(x))
		//region set opacity for each tick item
		.call(g => g.selectAll('.tick line')
		.attr('opacity', 0.2))
		//endregion
		//Remove line on domain for Y axis
		.call(g => g.select('.domain').remove())
		.selectAll('text')
		//rotate text
		.attr('transform', 'translate(-10,-20)rotate(-45)')
		.attr('fill', '#909090');

	//Add X axis label:
	svg.append('text')
		.attr('text-anchor', 'end')
		.style('font-size', 16)
		.attr('x', (width / 2) + margin.top)//for center
		.attr('y', height + margin.bottom - 10)//for set on bottom with -10
		.attr('fill', '#808080')
		.text(axisY);

	//Y axis label:
	svg.append('text')
		.attr('text-anchor', 'end')
		.style('font-size', 16)
		.attr('transform', 'rotate(-90)')
		.attr('y', -margin.left + 20)
		.attr('x', margin.top + (-height / 2))//center Y axis title
		.attr('fill', '#808080')
		.text(axisX);
	}
"""
	}
}