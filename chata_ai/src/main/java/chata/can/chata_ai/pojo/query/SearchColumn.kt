package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.extension.isNumber
import chata.can.chata_ai.extension.isUnCountable
import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object SearchColumn
{
	fun getSeriesColumn(queryBase: QueryBase)
	{
		queryBase.run {
			aCurrency.clear()
			aQuality.clear()
			aCommon.clear()
			for ((index, column) in aColumn.withIndex())
			{
				val pair = Pair(index, column)
				when(column.type)
				{
					TypeDataQuery.DOLLAR_AMT ->
					{
						column.isSelected = true
						aCurrency.add(pair)
					}
					TypeDataQuery.QUANTITY ->
					{
						column.isSelected = false
						aQuality.add(pair)
					}
					TypeDataQuery.STRING, TypeDataQuery.DATE ->
					{
						column.isSelected = false
						aCommon.add(pair)
					}
					else -> {}
				}
			}
		}
	}

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

	//search type column in specific
	fun getTypeColumn(
		aColumns: ArrayList<ColumnQuery>,
		type: TypeDataQuery
	): Int
	{
		var position = -1
		for (index in aColumns.indices)
		{
			if (aColumns[index].type == type)
			{
				position = index
				break
			}
		}
		return position
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

	fun getCountIndices(
		aColumns: ArrayList<ColumnQuery>,
		type: ArrayList<TypeDataQuery>,
		count: Int = 0,
		ignoreCount: Int = 0): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aColumns.indices)
		{
			if (ignoreCount > index)
				continue
			if (aColumns[index].type in type)
			{
				aIndices.add(index)
				if (count != 0 && count == aIndices.size) break
			}
		}
		return aIndices
	}

	fun getMinMaxColumns(
		aRows: ArrayList<ArrayList<String>>,
		aIndices: ArrayList<Int>
	): Pair<Int, Int>
	{
		val aInt = ArrayList<Double>()
		for (row in aRows)
		{
			for (index in aIndices)
			{
				aInt.add(row[index].toDoubleNotNull())
			}
		}
		val max = (aInt.maxOrNull() ?: 0.0) + 100.0
		val min = (aInt.minOrNull() ?: 0.0) - 100.0
		return Pair(max.toInt() + 1, min.toInt() - 1)
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