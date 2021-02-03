function setDonut() {
  var arc = d3.arc()
    .outerRadius(radius - 10)
		.innerRadius(100);

	var pie = d3.pie()
		.sort(null)
		.value(function(d) {
			//return d.count;
			return d.value;
		});

	var svg = d3.select('body').append('svg')
		.attr('width', width)
		.attr('height', height)
		.append('g')
		.attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

	var g = svg.selectAll('.arc')
		.data(pie(data))
		.enter().append('g');

	var scaleColorPie = d3.scaleOrdinal().domain(data).range(colorPie);

	g.append('path')
		.attr('d', arc)
		.style('fill', function(d,i) {
			return scaleColorPie(d.value);
		})
		.on('click', function(d) {
			drillDown(d.value);
		});

	g.append('text')
		.attr('transform', function(d) {
			var _d = arc.centroid(d);
			_d[0] *= 1.6;	//multiply by a constant factor
			_d[1] *= 1.6;	//multiply by a constant factor
			return 'translate(' + _d + ')';
		})
		.attr('dy', '.50em')
		.style('text-anchor', 'middle')
		.text(function(d) {
			return d.data.name;
			// if(d.data.percentage < 8) {
			// 	return '';
			// }
			// return d.data.percentage + '%';
		});
}