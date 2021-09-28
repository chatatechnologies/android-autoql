package chata.can.chata_ai.pojo.html

object Bubble
{
	fun getBubble(): String
	{
		return """function setBubble()
{
  var margin = {top: 10, right: 20, bottom: 30, left: 50},
    width = 700 - margin.left - margin.right,
    height = 450 - margin.top - margin.bottom;
  var data1 = [{"country":"Afghanistan","continent":"Asia","lifeExp":43.828,"pop":31889923,"gdpPercap":974.5803384},{"country":"Albania","continent":"Europe","lifeExp":76.423,"pop":3600523,"gdpPercap":5937.029526},{"country":"Algeria","continent":"Africa","lifeExp":72.301,"pop":33333216,"gdpPercap":6223.367465},{"country":"Angola","continent":"Africa","lifeExp":42.731,"pop":12420476,"gdpPercap":4797.231267},{"country":"Argentina","continent":"Americas","lifeExp":75.32,"pop":40301927,"gdpPercap":12779.37964},{"country":"Australia","continent":"Oceania","lifeExp":81.235,"pop":20434176,"gdpPercap":34435.36744},{"country":"Austria","continent":"Europe","lifeExp":79.829,"pop":8199783,"gdpPercap":36126.4927},{"country":"Bahrain","continent":"Asia","lifeExp":75.635,"pop":708573,"gdpPercap":29796.04834},{"country":"Bangladesh","continent":"Asia","lifeExp":64.062,"pop":150448339,"gdpPercap":1391.253792},{"country":"Belgium","continent":"Europe","lifeExp":79.441,"pop":10392226,"gdpPercap":33692.60508},{"country":"Benin","continent":"Africa","lifeExp":56.728,"pop":8078314,"gdpPercap":1441.284873},{"country":"Bolivia","continent":"Americas","lifeExp":65.554,"pop":9119152,"gdpPercap":3822.137084},{"country":"Bosnia and Herzegovina","continent":"Europe","lifeExp":74.852,"pop":4552198,"gdpPercap":7446.298803},{"country":"Botswana","continent":"Africa","lifeExp":50.728,"pop":1639131,"gdpPercap":12569.85177},{"country":"Brazil","continent":"Americas","lifeExp":72.39,"pop":190010647,"gdpPercap":9065.800825},{"country":"Bulgaria","continent":"Europe","lifeExp":73.005,"pop":7322858,"gdpPercap":10680.79282},{"country":"Burkina Faso","continent":"Africa","lifeExp":52.295,"pop":14326203,"gdpPercap":1217.032994},{"country":"Burundi","continent":"Africa","lifeExp":49.58,"pop":8390505,"gdpPercap":430.0706916},{"country":"Cambodia","continent":"Asia","lifeExp":59.723,"pop":14131858,"gdpPercap":1713.778686},{"country":"Cameroon","continent":"Africa","lifeExp":50.43,"pop":17696293,"gdpPercap":2042.09524},{"country":"Canada","continent":"Americas","lifeExp":80.653,"pop":33390141,"gdpPercap":36319.23501},{"country":"Central African Republic","continent":"Africa","lifeExp":44.741,"pop":4369038,"gdpPercap":706.016537},{"country":"Chad","continent":"Africa","lifeExp":50.651,"pop":10238807,"gdpPercap":1704.063724},{"country":"Chile","continent":"Americas","lifeExp":78.553,"pop":16284741,"gdpPercap":13171.63885},{"country":"China","continent":"Asia","lifeExp":72.961,"pop":1318683096,"gdpPercap":4959.114854},{"country":"Colombia","continent":"Americas","lifeExp":72.889,"pop":44227550,"gdpPercap":7006.580419},{"country":"Comoros","continent":"Africa","lifeExp":65.152,"pop":710960,"gdpPercap":986.1478792},{"country":"Congo, Dem. Rep.","continent":"Africa","lifeExp":46.462,"pop":64606759,"gdpPercap":277.5518587},{"country":"Congo, Rep.","continent":"Africa","lifeExp":55.322,"pop":3800610,"gdpPercap":3632.557798},{"country":"Costa Rica","continent":"Americas","lifeExp":78.782,"pop":4133884,"gdpPercap":9645.06142},{"country":"Cote d'Ivoire","continent":"Africa","lifeExp":48.328,"pop":18013409,"gdpPercap":1544.750112},{"country":"Croatia","continent":"Europe","lifeExp":75.748,"pop":4493312,"gdpPercap":14619.22272},{"country":"Cuba","continent":"Americas","lifeExp":78.273,"pop":11416987,"gdpPercap":8948.102923},{"country":"Czech Republic","continent":"Europe","lifeExp":76.486,"pop":10228744,"gdpPercap":22833.30851},{"country":"Denmark","continent":"Europe","lifeExp":78.332,"pop":5468120,"gdpPercap":35278.41874},{"country":"Djibouti","continent":"Africa","lifeExp":54.791,"pop":496374,"gdpPercap":2082.481567},{"country":"Dominican Republic","continent":"Americas","lifeExp":72.235,"pop":9319622,"gdpPercap":6025.374752}];
  var svg = d3.select('body')
    .append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
  
  // Add X axis
  var x = d3.scaleLinear()
    .domain([0, 10000])
    .range([ 0, width ]);
  svg.append('g')
    .attr('transform', 'translate(0,' + height + ')')
    .call(d3.axisBottom(x));

  // Add Y axis
  var y = d3.scaleLinear()
    .domain([35, 90])
    .range([ height, 0]);
  svg.append('g')
    .call(d3.axisLeft(y));

  // Add a scale for bubble size
  var z = d3.scaleLinear()
    .domain([200000, 1310000000])
    .range([ 1, 40]);

  // Add dots
  svg.append('g')
    .selectAll('dot')
    .data(data1)
    .enter()
    .append('circle')
    .attr('cx', function (d) { return x(d.gdpPercap); } )
    .attr('cy', function (d) { return y(d.lifeExp); } )
    .attr('r', function (d) { return z(d.pop); } )
    .style('fill', '#26a7df')
    .style('opacity', '0.7')
    .attr('stroke', '#26a7df')
    .on('click', function(d) {
      console.log("group: " + d.country);
    });
}"""
	}
}