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
	if (typeChart == TypeEnum.TABLE && nColumns == 3)
	{
		var secondCell = firstColumn[0].children[1].innerText;
    finalText += `_${'$'}{secondCell}`;
	}
  else
    finalText = drillX[index];
  drillDown(finalText);
});"""
	}
}