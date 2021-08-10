package chata.can.chata_ai.pojo.html

object Donut
{
	fun getDonut(): String
	{
		return """
//REGION DONUT
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
    svg.append('text')
    .style('font-size', 12)
    .attr('x', -(maxWidth / 2) - 20)
    .attr('y', factorBack)
    .attr('fill', '#808080')
		.attr('id', `id_${'$'}{index}`)
    .text(item.name + ': ' + item.value)
		.on('click', function() {
			var id = this.id;
      adminOpacity(id);
		});
		
  svg.append('circle')
    .attr("cx", -(maxWidth / 2) - 30)
    .attr("cy", factorBack - 5)
    .attr("r", 5)
    .attr("fill", colorPie[index])
		.attr('id', `idcircle_${'$'}{index}`)
		.attr('style', function () {
      return `opacity: ${'$'}{opacityMarked.includes(index) ? '0.5' : '1'}`;
    })
		.on('click', function() {
			var id = this.id;
      adminOpacity(id);
		});
    factorBack += 20;
  }
}
//END DONUT"""
	}
}