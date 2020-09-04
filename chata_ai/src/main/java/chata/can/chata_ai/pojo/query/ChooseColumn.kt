package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object ChooseColumn
{
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