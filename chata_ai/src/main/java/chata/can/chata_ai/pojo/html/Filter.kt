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
  var index = words[1];
  var subGroup = subgroups[index];
  //
  console.log(subGroup);
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
    console.log('exist on -> ' + tmp);
  }
  else opacityMarked.push(index);
  console.log('indices->' + opacityMarked);

  // for (position in dataTmp) {
  //   var element = data[position];
  //   var copied = Object.assign({}, element);//copy new object

  //   copied[subGroup] = exist ? element[subGroup] : 0;
  //   dataTmp[position] = copied;
  // }
  // isAgain = true;
  // updateData(typeChart, true);
}

function adminOpacity(id) {
  var words = id.split('_');
  var index = words[1];
  isAgain = true;
  var item = data[index];
  var copied = Object.assign({}, item);

  if (opacityMarked.includes(index)) {
    opacityMarked.splice(0, 1);
    copied.value = item.value;
  } else {
    opacityMarked.push(index);
    copied.value = 0;
  }
  dataTmp[index] = copied;
  updateData(typeChart, true);
}"""
	}
}