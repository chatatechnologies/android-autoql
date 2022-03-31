package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.extension.toIntNotNull
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn
import chata.can.chata_ai.retrofit.data.model.query.QueryEntity
import java.util.*

class OrderRow(private val queryEntity: QueryEntity) {
	private val searchColumn = SearchColumn()

	fun setOrderRowByDate() {
		queryEntity.run {
			val aDates = ArrayList<Pair< List<String>, Date>>()
			var iDate = searchColumn.getTypeColumn(columnsEntity, TypeColumn.DATE_STRING)
			if (iDate != -1) {
				val calendar = GregorianCalendar()
				for (row in rows) {
					val rowDate = row[iDate]
					val aRowDate = rowDate.split("-")
					if (aRowDate.isNotEmpty()) {
						val year = aRowDate[0].toIntNotNull()
						val month = if (aRowDate.size > 1)
							aRowDate[1].toIntNotNull() - 1
						else 1
						calendar.set(year, month, 1, 0, 0)
						aDates.add(Pair(row, calendar.time))
					}
				}

			}
			else {
				iDate = searchColumn.getTypeColumn(columnsEntity, TypeColumn.DATE)
				if (iDate != -1) {
					val calendar = GregorianCalendar()
					for (row in rows)
					{
						val rowDate = row[iDate]
						calendar.timeInMillis = (rowDate.toLongOrNull() ?: 0L) * 1000
						aDates.add(Pair(row, calendar.time))
					}
				}
			}
			if (aDates.isNotEmpty()) {
				val aNewRows = aDates.sortedBy { it.second }
				rows.run {
					clear()
					aNewRows.forEach { add(it.first) }
				}
			}
		}
	}
}