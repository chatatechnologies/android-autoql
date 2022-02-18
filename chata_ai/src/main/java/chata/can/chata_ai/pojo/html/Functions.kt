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

function isHorizontal() {
  return typeChart == TypeEnum.BAR;
}

function clearSvg() {
	//remove svg
	d3.select('svg').remove();
}

function getLimitName() {
  if (limitName == -1) {
    switch (typeChart) {
      case TypeEnum.COLUMN:
			case TypeEnum.STACKED_COLUMN:
      case TypeEnum.LINE:
        return 80;
      default:
      return 110;
    }
  } else return limitName;
}

function updateSize() {
	var _bottom = (isHorizontal() ? _maxValue : getLimitName()) + marginAxis;
	var _left = (isHorizontal() ? getLimitName() : _maxValue) + marginAxis;

	margin.left = _left;
	margin.bottom = _bottom;

  height = ${'$'}(window).height() - margin.top - margin.bottom;
  width = getWidthMargin();

  if (typeChart === TypeEnum.LINE) {
    if (nColumns === 2) {
      width = width * 2.0;
      height = height * 2.5;
    } else {
      width = width * 4.0;
      height = height * 1.5;
    }
  } else {
    switch (typeChart) {
      case TypeEnum.BAR:
			case TypeEnum.STACKED_BAR:
        width = width * 2;
        if (nColumns == 2) {
          height = height * 2.5;
        } else {
          height = height * 2.0;
        }
        break;
      case TypeEnum.COLUMN:
			case TypeEnum.STACKED_COLUMN:
				height = height * 2;
        if (nColumns == 2) {
          width = width * 2.5;
        } else {
          width = width * 2.0;
        }
        break;
			case TypeEnum.BUBBLE:
      case TypeEnum.HEATMAP:
        width = width * 2;
        height = height * 1.5;
        break;
      case TypeEnum.PIE:
      default:
        //width = width;
        //height = height1;
        break;
    }
  }
  radius = Math.min(width, height) / 2;
}

function getWidthMargin() {
  return ${'$'}(window).width() - margin.left - margin.right;
}

function isMultiple(typeChart) {
  return nColumns > 3 && (typeChart == TypeEnum.BAR || typeChart == TypeEnum.COLUMN || typeChart == TypeEnum.LINE);
}

function updateData(tmpChart, isReload) {
	//console.log("height " + ${'$'}(window).height());
  //console.log("width " + ${'$'}(window).width());
	
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
		Android.drillDown(content);
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

function updateSelected(index_value) {
  try {
    Android.updateSelected(index_value);
  } catch (err) {
    console.log(`Good content: ${'$'}{index_value}`);
  }
}

function axisEndPoints(axisSource) {
  var axis = axisSource;
  if (axis.length > 14) {
    axis = axis.slice(0, -3) + '...'
  }
  return axis;
}

function getAxisX(isMenu) {
	var axis = axisX;
  if (isMenu !== undefined) {
    axis = axisEndPoints(axis);
  }
  var extra = '';
  if (nColumns > 2) {
    extra = '▼';
  }
  return `${'$'}{axis} ${'$'}{extra}`;
}

function getAxisY() {
	var axis = axisY;
	if (isMenu !== undefined) {
    axis = axisEndPoints(axis);
  }
  var extra = '';
  if (nColumns > 2) {
    extra = '▼';
  }
  return `${'$'}{axis} ${'$'}{extra}`;
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
	var category = x;
  if (x === "null") {
    category = 'Untitled Category';
  }
  return `${'$'}{getFirst10(category.split('_')[0])}`;
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