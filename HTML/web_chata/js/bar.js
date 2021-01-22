function setBar() {
	var xScaleBand = d3.scaleBand()
		.range([height, 0])
		.padding(0.1);
	var y = d3.scaleLinear()
		.range([0, width]);
	
	
}