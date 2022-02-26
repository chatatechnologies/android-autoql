package chata.can.chata_ai.pojo.html

object Event
{
	fun getEvents(): String {
		return """
${'$'}('td').click(function() {
  var ${'$'}this = ${'$'}(this);
  var row = ${'$'}this.closest('tr').index();
  var column = ${'$'}this.closest('td').index();
  var firstColumn = ${'$'}this.closest('tr');
  var finalText = firstColumn[0].firstChild.innerText;
  var strDate = firstColumn[0].children[1].innerText;
  var index = aCategoryX.indexOf(finalText);
	if (is3Columns()) {
    switch (typeChart) {
      case TypeEnum.TABLE:
        var secondCell = firstColumn[0].children[1].innerText;
        finalText += `_${'$'}{secondCell}`;
        break;
      case TypeEnum.PIVOT:
        if (column == 0) return;
        var secondCell = isCurrency ? aCatHeatY[column - 1] : aCatHeatX[column - 1];
        finalText = isCurrency ? `${'$'}{finalText}_${'$'}{secondCell}` : `${'$'}{secondCell}_${'$'}{finalText}`;
        break;
      default:
        break;
    }
	}
  else
    finalText = drillX[index];
  drillDown(finalText);
});"""
	}
}