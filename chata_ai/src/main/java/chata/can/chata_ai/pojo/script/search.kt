package chata.can.chata_ai.pojo.script

//fun hasValueInColumn(queryBase: QueryBase)
fun hasNotValueInColumn(
	aRows: ArrayList<ArrayList<String>>,
	aSourceIndex: ArrayList<Int>,//That should have 1 item as minimal
	valueSearch: Float
): Int
{
	var indexOut = -1
	for (index in aSourceIndex)
	{
		if (indexOut == -1)
		{
			for (row in aRows)
			{
				if (index < row.size)
				{
					val value = row[index].toFloatOrNull() ?: 0f
					if (value != valueSearch)
					{
						indexOut = index
						break
					}
				}
			}
		}
	}
	return indexOut
}