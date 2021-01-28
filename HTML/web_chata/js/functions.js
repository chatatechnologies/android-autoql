function digitsCount(n) {
	var count = 0;
	if ( n >= 1) ++count;

	while (n/ 10 >= 1) {
		n /= 10;
		++count;
	}
	return count;
}

function angle(d) {
	var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
	return a > 90 ? a - 180 : a;
}

function drillDown(content) {
	try {
		//console.log("Contenido: " + content);
		Android.drillDown(content);
	} catch(err) {
		console.log("Contenido: " + content);
	};
}