package chata.can.chata_ai.model

fun getDataPivotColumn(aRows: ArrayList<ArrayList<String>>)
	: Pair<ArrayList<ArrayList<String>>, ArrayList<String>>
{
	val categoriesX = ArrayList<String>()
	val categoriesY = ArrayList<String>()
	var totalSum = ArrayList<DataPivotRow>()
	var totalDate = ArrayList<ArrayList<Any>>()

	for (row in aRows)
	{
		val data1 = row[0]
		val data2 = row[1]
		val data3 = row[2].toDoubleOrNull() ?: 0.0

		if (data1 !in categoriesX)
		{
			categoriesX.add(data1)
		}

		if (data2 !in categoriesY)
		{
			categoriesY.add(data2)
			totalDate.add(arrayListOf())
		}

		val locX = categoriesX.indexOf(data1)
		val locY = categoriesY.indexOf(data2)
		totalDate[locY].add(data3)
		val test = DataPivotRow(locX, locY, data3)
		totalSum.add(test)
	}

	//revert categoriesY
	val final = ArrayList<ArrayList<String>>()
	for ((indexX, category) in categoriesY.withIndex())
	{
		val finalB = arrayListOf(category)
		for ((indexY, _) in categoriesY.withIndex())
		{
			val position = totalSum.indexOfFirst { it.posX == indexX && it.posY == indexY }
			//TODO CHECK TO MONEY
			val value = if (position == -1) "$0" else totalSum[position].value.toString()
			finalB.add(value)
		}
		final.add(finalB)
	}
	val header = arrayListOf("")
	val headerFree = ArrayList<String>()
	for (cat in categoriesX)
	{
		header.add(cat)
		headerFree.add(cat)
	}
	final.add(0, header)
	return Pair(final, headerFree)
}