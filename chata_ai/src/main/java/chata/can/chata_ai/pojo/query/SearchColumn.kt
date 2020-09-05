package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object SearchColumn
{
	fun getGroupableIndices(aColumns: ArrayList<ColumnQuery>): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		aColumns.forEachIndexed { index, columnQuery ->
			if (columnQuery.isGroupable)
			{
				aIndices.add(index)
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