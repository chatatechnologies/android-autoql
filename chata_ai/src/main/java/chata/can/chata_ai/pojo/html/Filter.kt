package chata.can.chata_ai.pojo.html

object Filter
{
	fun getFilter(): String
	{
		return """
${'$'}('#idTableBasic tfoot th').each(function () {
    var indexInput = ${'$'}(this).index();
    var title = ${'$'}(this).text();
    var idInput = title.replace(/\s/g, '_').replace('(', '_').replace(')', '_').replace('&', '_').replace('/', '_') + '_Basic';
	${'$'}(this).html(
        '<input id=' + idInput +
        ' type="text" placeholder="filter column..."/>');

    ${'$'}("#" + idInput).on('input', function () {
        var filter = ${'$'}(this).val().toUpperCase();
        var filterNoComma = filter.replace(',', '');
        var table = document.getElementById("idTableBasic");
        var tr = table.getElementsByTagName("tr");

        for (index = 0; index < tr.length; index++) {
            td = tr[index].getElementsByTagName("td")[indexInput];
            if (td) {
              txtValue = td.textContent || td.innerText;
              txtValueNoComma = txtValue.replace(',', '');

              var txtValueUpper = txtValue.toUpperCase();
              var txtValueNoCommaUpper = txtValueNoComma.toUpperCase();
              var hasFilter1 = txtValueUpper.indexOf(filter) > -1;
              var hasFilterNoComma1 = txtValueUpper.indexOf(filterNoComma) > -1;
              var hasFilter2 = txtValueNoCommaUpper.indexOf(filter) > -1;
              var hasFilterNoComma2 = txtValueNoCommaUpper.indexOf(filterNoComma) > -1;

              if (hasFilter1 || hasFilterNoComma1 || hasFilter2 || hasFilterNoComma2) {
                  tr[index].style.display = "";
              } else {
                  tr[index].style.display = "none";
              }
            }
        }
    });
});

${'$'}('#idTableDataPivot tfoot th').each(function () {
    var indexInput = ${'$'}(this).index();
    var title = ${'$'}(this).text();
    var idInput = title.replace(/\s/g, '_').replace('(', '_').replace(')', '_').replace('&', '_').replace('/', '_') + '_DataPivot';
	${'$'}(this).html(
        '<input id=' + idInput +
        ' type="text" placeholder="Search on ' + title + '"/>');

    ${'$'}("#" + idInput).on('input', function () {
        var filter = ${'$'}(this).val().toUpperCase();
        var filterNoComma = filter.replace(',', '');
        var table = document.getElementById("idTableDataPivot");
        var tr = table.getElementsByTagName("tr");

        for (index = 0; index < tr.length; index++) {
            td = tr[index].getElementsByTagName("td")[indexInput];
            if (td) {
              txtValue = td.textContent || td.innerText;
              txtValueNoComma = txtValue.replace(',', '');

              var txtValueUpper = txtValue.toUpperCase();
              var txtValueNoCommaUpper = txtValueNoComma.toUpperCase();
              var hasFilter1 = txtValueUpper.indexOf(filter) > -1;
              var hasFilterNoComma1 = txtValueUpper.indexOf(filterNoComma) > -1;
              var hasFilter2 = txtValueNoCommaUpper.indexOf(filter) > -1;
              var hasFilterNoComma2 = txtValueNoCommaUpper.indexOf(filterNoComma) > -1;

              if (hasFilter1 || hasFilterNoComma1 || hasFilter2 || hasFilterNoComma2) {
                  tr[index].style.display = "";
              } else {
                  tr[index].style.display = "none";
              }
            }
        }
    });
});

function showFilter() {
	var display = ${'$'}('tfoot').is(':visible') ? 'none' : 'table-header-group';
  ${'$'}('tfoot').css({'display': display});
}

function adminMulti(id, subgroups) {
  var words = id.split('_');
  var index = parseInt(words[1]);
  var subGroup = subgroups[index];
  var exist = opacityMarked.includes(index);
  if (exist) {
    var tmp = -1;
    for (let _index = 0; _index < opacityMarked.length; _index++) {
      if (index == opacityMarked[_index])
      {
        tmp = _index;
        break;
      }
    }
    opacityMarked.splice(tmp, 1);
  }
  else opacityMarked.push(index);

  var aTmp = getDataMulti();
	for (position in dataTmp) {
    var element = aTmp[position];
		var itEdit = dataTmp[position];
  
		itEdit[subGroup] = exist ? element[subGroup] : 0;
  }
  isAgain = true;
  updateData(typeChart, true);
}

function callAdminStacked(aIndex) {
	indexIgnore = aIndex;
  if (aIndex.length > 0) {
    for (let index = 0; index < aIndex.length; index++) {
      const element = aIndex[index];
      controlStacked(element);
    }
  } else {
    for (let index1 = 0; index1 < opacityMarked.length; index1++) {
      const element = opacityMarked[index1];
      controlStacked(element);
    }
  }
  isAgain = true;
  updateData(typeChart, true);
}

function adminStacked(id) {
  var words = id.split('_');
  controlStacked(words[1]);
  isAgain = true;
  updateData(typeChart, true);
}

function controlStacked(id) {
  var index = parseInt(id);
  var exist = opacityMarked.includes(index);
  if (exist) {
    var tmp = -1;
    for (let _index = 0; _index < opacityMarked.length; _index++) {
      if (index == opacityMarked[_index])
      {
        tmp = _index;
        break;
      }
    }
    opacityMarked.splice(tmp, 1);
  }
  else {
    opacityMarked.push(index);
  }
  //#endregion
  //#region set value original or zero
  var sub = getCategoriesStack()[index];

  switch (typeChart) {
    case TypeEnum.BAR:
    case TypeEnum.COLUMN:
      var aTmp = getDataMulti();
      for (position in dataTmp) {
        var element = aTmp[position];
        var itEdit = dataTmp[position];
        itEdit[sub] = exist ? element[sub] : 0;
      }
      break;
    case TypeEnum.STACKED_AREA:
      var aStackedArea = getAreaData();
      for (position in aStackedArea) {
        var element = stackedAreaTmp[position];
        var itEdit = aStackedArea[position];
        var subKey = sub.replace(/\s/g, '_').replace('&', 'AMP').replace("'", 'QUOTE');
        if ( !isNaN( subKey.charAt(0) ) )
          subKey = '_' + subKey;
        itEdit[subKey] = exist ? element[subKey] : 0;
      }
      break
    default:
      var aStacked = getStackedData();
      for (var index1 = 0; index1 < aStacked.length; index1++) {
        var element = aStackedTmp[index1];
        var edit = aStacked[index1];
        edit[sub] = exist ? element[sub] : 0;
      }
      break;
  }
  //#endregion
	updateSelected(`${'$'}{index}_${'$'}{exist}`);
}

function adminOpacity(id) {
  var words = id.split('_');
  var index = words[1];

  var exist = opacityMarked.includes(index);
  if (exist)
  {
    var tmp = -1;
    for (let _index = 0; _index < opacityMarked.length; _index++) {
      if (index == opacityMarked[_index])
      {
        tmp = _index;
        break;
      }
    }
    var item = getDataOrMulti()[index];
    dataTmp[index].value = item.value;
    opacityMarked.splice(tmp, 1);
  }
  else 
  {
    dataTmp[index].value = 0;
    opacityMarked.push(index);
  }
  isAgain = true;
  updateData(typeChart, true);
}

function getMaxValue()
{
	if (aMaxData.length !== 0)
  {
    var aTotalValues = [];
    var key = `${'$'}{indexData}_${'$'}{isCurrency ? 1 : 2}`;
    var aTmp = aMaxData.length !== 0 ? aMaxData[key] : [];
    for (index = 0; index < aTmp.length; index++)
    {
      if (indexIgnore.includes(index)) continue;
      var value = aTmp[index];
      aTotalValues.push(value);
    }
    var maxMath = Math.max.apply(null, aTotalValues);
    return maxMath !== 0 ? maxMath : 1;
  }
  else
  {
    return maxValue;
  }
}

function getMinDomain() {
  var minDomain = 0;
  if (minValue2 === -1) {
    var values = [];
    for (let index = 0; index < data.length; index++) {
      const item = data[index];
      values.push(item.value);
    }
    var minimum = Math.min.apply(Math, values);
    var residue = minimum % 10000;
    minimum =  minimum - residue;
    if (minimum > 0) {
      minDomain = minimum;
    }
  }
  return minDomain;
}

function getMinValue()
{
  if (aMinData.length !== 0)
  {
    var aTotalValues = [];
    var key = `${'$'}{indexData}_${'$'}{isCurrency ? 1 : 2}`;
    var aTmp = aMinData.length !== 0 ? aMinData[key] : [];
    for (index = 0; index < aTmp.length; index++)
    {
      if (indexIgnore.includes(index)) continue;
      var value = aTmp[index];
      aTotalValues.push(value);
    }
    var minMath = Math.min.apply(null, aTotalValues);
    return minMath !== 0 ? minMath : 1;
  }
  else
  {
    return isCurrency ? minValue : minValue2;
  }
}

function getData() {
  return isCurrency ? data : data2;
}

function getDataOrMulti()
{
	return data.length !== 0 && !is3Columns() ? data : getDataMulti();
}

function getDataMulti() {
	if (aAllData.length !== 0)
	{
    var key;
    if (is3Columns()) {
      var key = `${'$'}{isCurrency ? '1_1' : '2_2'}`;
    } else {
      var key = `${'$'}{indexData}_${'$'}{isCurrency ? 1 : 2}`;
    }
    return aAllData[key];
	}
	else return [];
}

function getNegativeValue() {
  if (Object.keys(hasNegative).length === 0) {
    return false;
  } else {
    return hasNegative[`${'$'}{indexData}_${'$'}{isCurrency ? 1 : 2}`];
  }
}

function getStackedMax()
{
  var sumTop = 0;
  var aStacked = getStackedData();
  var aCat = getCategoriesStack();

  aStacked.forEach(iStacked => {
    var sum = 0;
    aCat.forEach(cat => {
      var toSum = iStacked[cat];
      if (toSum !== undefined) {
        sum += toSum;
      }
    });
    if (sumTop < sum) sumTop = sum;
  });
  return sumTop;
}

function setOtherStacked()
{
  isCurrency = !isCurrency;
  var tmp = axisX;
  axisX = axisY;
  axisY = tmp;
  isAgain = true;
  updateData(typeChart, true);
}

function getCategoriesStack()
{
  return isCurrency ? aCatHeatY : aCatHeatX;
}

function getStackedData()
{
  return isCurrency ? aStacked : aStacked2;
}

function getAreaData() {
  return isCurrency ? stackedArea : stackedArea2;
}

function getMultiCategory()
{
  return isCurrency ? aCategory : aCategory2;
}

function indexCircle(index)
{
  return index % colorPie.length;
}

function sizeByLetter(letters) {
  return (letters * 6.7 + 20) / 2;
}

function is2Columns() {
  return nColumns === 2;
}
function is3Columns() {
  return nColumns === 3;
}"""
	}
}