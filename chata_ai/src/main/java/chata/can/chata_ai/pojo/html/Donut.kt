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
		.data(pie(data))
		.enter().append('g');

	var scaleColorPie = d3.scaleOrdinal().domain(data).range(colorPie);

	g.append('path')
		.attr('d', arc)
		.style('fill', function(_, i) {
			return colorPie[i];
		})
		.on('click', function(_, i) {
			var index = i.index;
			drillDown(data[index].value);
		});
		
	var factorBack = -(maxHeight / 2) - 30;
		
  for (const index in data) {
    var item = data[index];
    svg.append('text')
    .style('font-size', 12)
    .attr('x', -(maxWidth / 2) - 20)
    .attr('y', factorBack)
    .attr('fill', '#808080')
    .text(item.name + ': ' + item.value);
		
    svg.append('circle')
      .attr("cx", -(maxWidth / 2) - 30)
      .attr("cy", factorBack - 5)
      .attr("r", 5)
      .attr("fill", colorPie[index])
    factorBack += 20;
  }
}
//END DONUT"""
	}
}