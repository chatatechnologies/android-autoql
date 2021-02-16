package chata.can.chata_ai.pojo.script

import chata.can.chata_ai.extension.toIntNotNull
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn
import java.util.*

fun setOrderRowByDate(queryBase: QueryBase)
{
	queryBase.run {
		val aDates = ArrayList<Pair< ArrayList<String>, Date>>()

		val iDate = SearchColumn.getTypeColumn(aColumn, TypeDataQuery.DATE_STRING)
		if (iDate != -1)
		{
			val calendar = GregorianCalendar()
			for (row in aRows)
			{
				val rowDate = row[iDate]
				val aRowDate = rowDate.split("-")
				if (rowDate.isNotEmpty())
				{
					val year = aRowDate[0].toIntNotNull()
					val month = aRowDate[1].toIntNotNull() - 1
					calendar.set(year, month, 1, 0, 0)
					aDates.add(Pair(row, calendar.time))
				}
			}
			val aNewRows = aDates.sortedByDescending { it.second }
			aRows.run {
				clear()
				for (row in aNewRows)
					add(row.first)
			}
		}
	}
}