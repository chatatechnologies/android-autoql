package chata.can.chata_ai.pojo.html

object Donut
{
	fun getDonut(): String
	{
		return """
function setDonut() {
  var arc = d3.arc()
    .outerRadius(radius)
		.innerRadius(radius * 0.5);

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
		.attr('transform', 'translate(' + maxWidth * 0.65 + ',' + maxHeight * 0.65 + ')');

	var g = svg.selectAll('.arc')
		.data(pie(dataTmp))
		.enter().append('g');

	g.append('path')
		.attr('d', arc)
		.style('fill', function(_, i) {
			return colorPie[i];
		})
		.on('click', function(_, i) {
			var index = i.index;
			var value = drillX[index];
			drillDown(value);
		});
		
	var factorBack = -(maxHeight / 2) - 30;
		
  for (const index in data) {
    var item = data[index];
    addText(svg, 'start', 12, 0, -(maxWidth / 2) - 20, factorBack, '#808080', `id_${'$'}{index}`, item.name + ': ' + item.value, function () {
      var id = this.id;
      adminOpacity(id);
    });
		
    addCircle(svg, -(maxWidth / 2) - 30, factorBack - 5, 5, colorPie[index], `idcircle_${'$'}{index}`,
      function () {
        return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
      },
      function () {
        var id = this.id;
        adminOpacity(id);
    });
    factorBack += 20;
  }
}"""
	}
}