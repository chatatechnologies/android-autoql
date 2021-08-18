package chata.can.chata_ai.pojo.html

object Functions
{
	//region function
	fun getFunctions(): String
	{
		return """function getFirst10(string) {
  var newString = '';
  if (string.length < 11) {
    newString = string;
  } else {
    newString = string.substring(0, 10) + '...';
  }
  return newString;
}

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

function isHorizontal(type) {
  return (type == TypeEnum.BAR);
}

function clearSvg() {
	//remove svg
	d3.select('svg').remove();
}

function updateSize() {
  if (data.length < 8) {
    width = ${'$'}(window).width() - margin.left - margin.right;
  } else {
    var width1 = ${'$'}(window).width() - margin.left - margin.right;
    width = (width1 / 8) * data.length;
  }
  height = ${'$'}(window).height() - margin.top - margin.bottom;
  radius = Math.min(width, height) / 2;
}

function updateData(tmpChart, isReload) {
	if (isAgain)
  {
    isAgain = false;
  }
  else
  {
    dataTmp = [];
    dataTmp.push.apply(dataTmp, data);
    if (typeChart != tmpChart || isReload) {
      typeChart = tmpChart;
    }
  }

  updateSize();
  //region choose table or chart
  switch (tmpChart) {
    case TypeEnum.TABLE:
    case TypeEnum.PIVOT:
      ${'$'}("svg").hide(0);
      var aID = tmpChart == TypeEnum.TABLE ? ["idTableBasic", "idTableDataPivot"] : ["idTableDataPivot", "idTableBasic"];
      ${'$'}(`#${'$'}{aID[0]}`).show(0);
      ${'$'}(`#${'$'}{aID[1]}`).hide(0);
      break;
    default:
      ${'$'}("#idTableBasic").hide(0);
      ${'$'}("#idTableDataPivot").hide(0);
      clearSvg();
      data.map(function(a1) {
        var keys = Object.keys(a1);
        nColumns = keys.length;
      });
      switch(typeChart) {
        case TypeEnum.COLUMN:
          if (nColumns == 2) {
            setColumn();
          } else {
            setMultiColumn();
          }
        break;
        case TypeEnum.BAR:
          if (nColumns == 2) {
              setBar();
            } else {
              setMultiBar();
            }
          break;
        case TypeEnum.LINE:
          if (nColumns == 2) {
            setLine();
          } else {
            setMultiLine();
          }
          break;
        case TypeEnum.PIE:
          setDonut();
          break;
      }
      break;
  }
  //endregion
}

function drillDown(content) {
	try {
		Android.boundMethod(content);
	} catch(err) {
		console.log("Good content: " + content);
	};
}

function modalCategories(type, content) {
  try {
		Android.modalCategories(type, content);
	} catch(err) {
		console.log(`Good content: ${'$'}{content}; ${'$'}{type}`);
	};
}

function getAxisX() {
  var extra = '';
  if (nColumns > 3) {
    extra = '▼';
  }
  return `${'$'}{axisX} ${'$'}{extra}`;
}

function getAxisY() {
  var extra = '';
  if (nColumns > 3) {
    extra = '▼';
  }
  return `${'$'}{axisY} ${'$'}{extra}`;
}

function svgMulti() {
  var svg = d3.select('body').append('svg')
		.attr('width', width + margin.bottom + margin.right)
		.attr('height', height + margin.top + margin.left + 50);
  return svg;
}

function addText(svg, textAnchor, fontSize, rotate, x, y, fillColor, id, text, click) {
  svg.append('text')
		.attr('text-anchor', textAnchor)
		.style('font-size', fontSize)
    .attr('transform', `rotate(${'$'}{rotate})`)
		.attr('x', x)//for center
		.attr('y', y)//for set on bottom with -10
		.attr('fill', fillColor)
		.attr('id', id)
		.text(text)
    .on('click', click)
}

function axisMulti(svg, isLeft, xBand, height) {
  var svg = svg.append("g");
  if (height !== undefined)
  {
    svg = svg.attr('transform', `translate(0,${'$'}{height})`)
  }
  var axis = isLeft ? 
    d3.axisLeft(xBand).tickFormat(x => `${'$'}{x.split('_')[0]}`) :
    d3.axisBottom(xBand).tickFormat(x =>`${'$'}{fformat(x)}`);
	svg.call(axis);
  return svg;
}"""
	}
	//endregion
}