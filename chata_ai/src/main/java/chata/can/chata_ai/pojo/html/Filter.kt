package chata.can.chata_ai.pojo.html

object Filter
{
	fun getFilter(): String
	{
		return """
${'$'}('#idTableBasic tfoot th').each(function () {
    var indexInput = ${'$'}(this).index();
    var title = ${'$'}(this).text();
    var idInput = title.replace(' ', '_').replace('(', '_').replace(')', '_').replace('&', '_') + '_Basic';
	${'$'}(this).html(
        '<input id=' + idInput +
        ' type="text" placeholder="filter column..."/>');

    ${'$'}("#" + idInput).on('input', function () {
        var filter = ${'$'}(this).val().toUpperCase();
        var table = document.getElementById("idTableBasic");
        var tr = table.getElementsByTagName("tr");

        for (index = 0; index < tr.length; index++) {
            td = tr[index].getElementsByTagName("td")[indexInput];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
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
    var idInput = title.replace(' ', '_').replace('(', '_').replace(')', '_').replace('&', '_') + '_DataPivot';
	${'$'}(this).html(
        '<input id=' + idInput +
        ' type="text" placeholder="Search on ' + title + '"/>');

    ${'$'}("#" + idInput).on('input', function () {
        var filter = ${'$'}(this).val().toUpperCase();
        var table = document.getElementById("idTableDataPivot");
        var tr = table.getElementsByTagName("tr");

        for (index = 0; index < tr.length; index++) {
            td = tr[index].getElementsByTagName("td")[indexInput];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
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
    var aTotalIndices = [];
    var key = `${'$'}{indexData}_${'$'}{isCurrency ? 1 : 2}`;
    var aTmp = aMaxData.length !== 0 ? aMaxData[key] : [];
    var aTmp = aMaxData[key];
    for (index = 0; index < aTmp.length; index++)
    {
      if (indexIgnore.includes(index)) continue;
      var value = aTmp[index];
      aTotalIndices.push(value);
    }
    var maxMath = Math.max.apply(null, aTotalIndices);
    return maxMath !== 0 ? maxMath : 1;
  }
  else
  {
    return maxValue;
  }
}

function getMinValue()
{
  return isCurrency ? minValue : minValue2;
}

function getDataOrMulti()
{
	return data.length !== 0 ? data : getDataMulti();
}

function getDataMulti()
{
	if (aAllData.length !== 0)
	{
    var key = `${'$'}{indexData}_${'$'}{isCurrency ? 1 : 2}`;
    return aAllData[key];
	}
	else return [];
}

function getMultiCategory()
{
  return isCurrency ? aCategory : aCategory2;
}

function indexCircle(index)
{
  return index % colorPie.length;
}"""
	}
}