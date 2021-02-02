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

function clearSvg() {
	//remove svg
	d3.select('svg').remove();
}

function updateData(tmpChart) {
	if (typeChart != tmpChart) {
	  typeChart = tmpChart;

	  clearSvg();
	  switch(typeChart) {
		case TypeEnum.COLUMN:
		  console.log("COLUMN");
		  setColumn();
		  break;
		case TypeEnum.BAR:
		  console.log("BAR");
		  setBar();
		  break;
		case TypeEnum.LINE:
		  console.log("LINE");
		  setLine();
		  break;
		case TypeEnum.PIE:
		  console.log("PIE");
		  setDonut();
		  break;
		default:
		  console.log("default");
	  }
	}
  }

function drillDown(content) {
	try {
		//console.log("Contenido: " + content);
		Android.drillDown(content);
	} catch(err) {
		console.log("Contenido: " + content);
	};
}