function setPie() {
    var svg = d3.select('body')
    .append('svg')
    .attr('width', width)
    .attr('height', height)
    .append('g')
    .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

    var pie = d3.pie()
      .value(function(d) {
        return d.value;
      });

    var g = svg.selectAll()
      .data(pie(data))
      .enter().append("g");

    var arc = d3.arc()
      .innerRadius(0)
      .outerRadius(radius);

    g.append('path')
      .attr('d', arc)
      .style('fill', function(d) {
        return scaleColorPie(d.data.value);
      });

    var labelArc = d3.arc()
      .innerRadius(radius - 60)
      .outerRadius(radius - 60);

    g.append("text")
      .style('fill', 'white')
      .attr("dy", ".35em")
      .attr("transform", function(d) {
        return 'translate(' + arc.centroid(d) + '), rotate('+ angle(d) +')';
      })
      .style("text-anchor", "middle")
      .text(function(d) {
        return d.data.name;
      });
  }