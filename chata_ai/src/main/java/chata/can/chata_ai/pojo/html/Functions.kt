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
  if (typeChart === TypeEnum.LINE) {
    if (nColumns === 2) {
      var width1 = getWidthMargin();
      width = (width1 * 1.5);
    } else {
      width = getWidthMargin();
    }
  } else {
    switch (typeChart) {
      case TypeEnum.COLUMN:
      case TypeEnum.BAR:
      case TypeEnum.PIE:
        var width1 = getWidthMargin();
        width = (width1 * 1.5);
        break;
      default:
        width = getWidthMargin();
        break;
    }
  }
  height = ${'$'}(window).height() - margin.top - margin.bottom;
  radius = Math.min(width, height) / 2;
}

function getWidthMargin() {
  return ${'$'}(window).width() - margin.left - margin.right;
}

function isMultiple(typeChart) {
  return nColumns > 3 && (typeChart == TypeEnum.BAR || typeChart == TypeEnum.COLUMN || typeChart == TypeEnum.LINE);
}

function updateData(tmpChart, isReload) {
  //region set nColumns
	var aTmpData = getDataOrMulti();
  var keys = Object.keys(aTmpData.length !== 0 ?  aTmpData[0] : {'':''});
  nColumns = keys.length;
  //endregion
	var _isMultiple = isMultiple(tmpChart);
	if (dataTmp.length && (isAgain || _isMultiple))
  {
		if (_isMultiple)
      typeChart = tmpChart;
    isAgain = false;
  }
  else
  {
    dataTmp = [];
		var aTmp = getDataOrMulti();
    aTmp.forEach(element => {
			var copied = Object.assign({}, element);
			indexIgnore.forEach(index => {
        delete copied[`time_${'$'}{index}`];
      });
			dataTmp.push(copied);
		});
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
				case TypeEnum.HEATMAP:
					setHeatMap();
					break;
				case TypeEnum.BUBBLE:
          setBubble();
          break;
        case TypeEnum.STACKED_BAR:
          setStackedBar();
          break;
				case TypeEnum.STACKED_COLUMN:
					setStackedColumn();
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
		.attr('height', height + margin.top + margin.left + 30);
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
    .on('click', click);
}

function addCircle(svg, cx, cy, r, fill, id, fStyle, fClick) {
  svg.append('circle')
    .attr('cx', cx)
    .attr('cy', cy)
    .attr('r', r)
    .attr('fill', fill)
    .attr('id', id)
    .attr('style', fStyle)
    .on('click', fClick);
}

function splitAxis(x) {
  return `${'$'}{getFirst10(x.split('_')[0])}`;
}

function formatAxis(y) {
  return `${'$'}{fformat(y)}`;
}

function axisMulti(svg, isLeft, xBand, height, _tickSize, formatAxis) {
  var svg = svg.append("g");
  if (height !== undefined)
  {
    svg = svg.attr('transform', `translate(0,${'$'}{height})`)
  }
  var axis = isLeft ? d3.axisLeft(xBand) : d3.axisBottom(xBand);
  axis = axis.tickSize(_tickSize).tickFormat(x => formatAxis(x));
	svg.call(axis);
  return svg;
}

function completeAxisMultiple(axis, pointX, pointY, rotate) {
  axis
    .selectAll('text')
    //rotate text
    .attr('transform', `translate(${'$'}{pointX}, ${'$'}{pointY})rotate(${'$'}{rotate})`)
    //Set color each item on X axis
    .attr('fill', '#909090')
    .style('text-anchor', 'end');
}

function setMultiCategory(aIndex, _IsCurrency, onlyIndex) {
	if (onlyIndex !== undefined) {
    var aTmp = _IsCurrency ? aCategory : aCategory2;
    axisY = aTmp[onlyIndex];
  } else {
    axisY = _IsCurrency ? 'Amount' : 'Quantity';
  }
  indexIgnore = [];
  aIndex.forEach(index => {
    indexIgnore.push(index);
  });
	isCurrency = _IsCurrency;
  dataTmp = [];
  updateData(typeChart, true);
}

function setIndexData(indexRoot, indexCommon) {
  var common = aCommon[indexCommon];
  axisX = common;
  indexData = indexRoot;
  dataTmp = [];
  updateData(typeChart, true);
}"""
	}
	//endregion
}