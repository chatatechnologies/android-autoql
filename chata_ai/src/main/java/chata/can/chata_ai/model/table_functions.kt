package chata.can.chata_ai.model

fun getDataPivotColumn(aRows: ArrayList<ArrayList<String>>)
{
	val categoriesX = ArrayList<String>()
	val categoriesY = ArrayList<String>()
//	var totalSum: [DataPivotRow] = []
//	var totalDate: [[Any]] = []
	for (row in aRows)
	{
		val data1 = row[0]
		val data2 = row[1]
		val data3 = row[2].toDoubleOrNull() ?: 0.0

		if (categoriesX.indexOf(data1) == -1)
		{
			categoriesX.add(data1)
		}


	}
}