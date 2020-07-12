package chata.can.chata_ai.pojo.webView

object LineBuilder
{
	fun generateDataChartLine(
		aDataTable: ArrayList<ArrayList<Any>>,
		aCatY: ArrayList<String>): ArrayList<String>
	{
		val aChartLine = ArrayList<String>()
		for ((index, category) in aCatY.withIndex())
		{
			val aDataFilter = aDataTable.filter { it[1] == index }
			val aData = ArrayList<Double>()
			for (value in aDataFilter)
			{
				(value[2] as? Double)?.let {
					aData.add(it)
				}
			}
			val item = "{\"data\":$aData, \"name\":$category}"
			aChartLine.add(item)
		}
		return aChartLine
	}
}