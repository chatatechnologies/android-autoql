package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.extension.isNumber
import chata.can.chata_ai.extension.isUnCountable
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object SearchColumn
{
	fun getGroupableIndices(
		aColumns: ArrayList<ColumnQuery>,
		count: Int): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aColumns.indices)
		{
			if (aColumns[index].isGroupable)
			{
				aIndices.add(index)
				if (count == aIndices.size) break
			}
		}
		return aIndices
	}

	fun getUncountableIndices(aColumns: ArrayList<ColumnQuery>): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aColumns.indices)
		{
			if (aColumns[index].type.isUnCountable())
			{
				aIndices.add(index)
			}
		}
		return aIndices
	}

	fun getNumberIndices(
		aColumns: ArrayList<ColumnQuery>,
		count: Int = 0): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aColumns.indices)
		{
			if (aColumns[index].type.isNumber())
			{
				aIndices.add(index)
				if (count != 0 && count == aIndices.size) break
			}
		}
		return aIndices
	}

	fun getTypeIndices(
		aColumns: ArrayList<ColumnQuery>,
		type: TypeDataQuery,
		count: Int,
		ignoreCount: Int = 0): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aColumns.indices)
		{
			if (ignoreCount > index)
				continue
			if (aColumns[index].type == type)
			{
				aIndices.add(index)
				if (count == aIndices.size) break
			}
		}
		return aIndices
	}

	fun hasDecimals(
		aRows: ArrayList<ArrayList<String>>,
		indexCheck: Int): Boolean
	{
		var isDecimal = true
		for (row in aRows)
		{
			if (indexCheck < row.size)
			{
				row[indexCheck].let { string ->
					string.toDoubleOrNull()?.let {
						isDecimal = (it % 1.0) != 0.0
					} ?: run {
						isDecimal = false
					}
				}
			}
		}
		return isDecimal
	}
}