package chata.can.chata_ai.pojo.html

object Functions
{
	//region function
	fun getFunctions(): String
	{
		return """
function digitsCount(n) {
	var count = 0;
	if ( n >= 1) ++count;

	while (n/ 10 >= 1) {
		n /= 10;
		++count;
	}
	return count;
}

function clearSvg() {
	//remove svg
	d3.select('svg').remove();
}

function angle(d) {
	var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
	return a > 90 ? a - 180 : a;
}

function drillDown(content) {
	try {
		//console.log("Content: " + content);
		Android.drillDown(content);
	} catch(err) {
		console.log("Content: " + content);
	};
}
"""
	}
	//endregion
}