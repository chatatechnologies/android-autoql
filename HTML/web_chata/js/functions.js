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
		  setColumn();
		  break;
		case TypeEnum.BAR:
		  setBar();
		  break;
		case TypeEnum.LINE:
		  setLine();
		  break;
		case TypeEnum.PIE:
		  setDonut();
		  break;
		default:
		  console.log("default");
	  }
	}
  }

function drillDown(content) {
	try {
		console.log("Good content: " + content);
		// Android.drillDown(content);
	} catch(err) {
		console.log("Wrong content: " + content);
	};
}