package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.extension.isNumber
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

	fun getNumberIndices(
		aColumns: ArrayList<ColumnQuery>,
		count: Int): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aColumns.indices)
		{
			if (aColumns[index].type.isNumber())
			{
				aIndices.add(index)
				if (count == aIndices.size) break
			}
		}
		return aIndices
	}

	fun getIndexString(aColumn: ArrayList<ColumnQuery>): Int
	{
		var position = 0
		aColumn.forEachIndexed { index, columnQuery ->
			if (columnQuery.type == TypeDataQuery.STRING)
			{
				position = index
			}
		}
		return position
	}
}