package chata.can.chata_ai.pojo.html

object Donut
{
	fun getDonut(): String
	{
		return """//REGION DONUT
function setDonut() {
  var arc = d3.arc()
    .outerRadius(radius - 10)
		.innerRadius(100);

	var pie = d3.pie()
		.sort(null)
		.value(function(d) {
			return d.value;
		});
		
	var maxWidth = width + margin.left + margin.right;
  var maxHeight = height + margin.top + margin.bottom;

	var svg = d3.select('body').append('svg')
    .attr('width', maxWidth)
    .attr('height', maxHeight)
		.append('g')
		.attr('transform', 'translate(' + maxWidth / 2 + ',' + maxHeight / 2 + ')');

	var g = svg.selectAll('.arc')
		.data(pie(data))
		.enter().append('g');

	var scaleColorPie = d3.scaleOrdinal().domain(data).range(colorPie);

	g.append('path')
		.attr('d', arc)
		.style('fill', function(d,i) {
			return scaleColorPie(d.value);
		})
		.on('click', function(_, i) {
			var index = i.index;
			drillDown(data[index].value);
		});
		
		var factorBack = (data.length * 20) / -2;//-2 I need it to be a negative number
		
		for (const index in data) {
	    var item = data[index];
	    svg.append('text')
	    .style('font-size', 16)
	    .attr('x', -(maxWidth / 2) + margin.left)
	    .attr('y', factorBack)
	    .attr('fill', '#808080')
	    .text(item.name + ': ' + item.value);
	    //console.log(index)
	    factorBack += 20;
	  }
		
	// g.append('text')
	// 	.attr('transform', function(d) {
	// 		var _d = arc.centroid(d);
	// 		_d[0] *= 1.6;	//multiply by a constant factor
	// 		_d[1] *= 1.6;	//multiply by a constant factor
	// 		return 'translate(' + _d + ')';
	// 	})
	// 	.attr('dy', '.50em')
	// 	.style('text-anchor', 'middle')
	// 	.text(function(d) {
	// 		return d.data.name;
	// 	});
	}
//END DONUT"""
	}
}