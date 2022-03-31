package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn
import chata.can.chata_ai.retrofit.ColumnEntity
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn

class SearchColumn {
	fun getTypeColumn(aColumns: List<ColumnEntity>, type: TypeColumn): Int {
		var position = -1
		for (index in aColumns.indices) {
			if (aColumns[index].typeColumn == type) {
				position = index
				break
			}
		}
		return position
	}

	fun isDateColumn(columns: List<ColumnEntity>): Boolean {
		val posD = getTypeColumn(columns, TypeColumn.DATE)
		if (posD == -1)
		{
			val posDS = getTypeColumn(columns, TypeColumn.DATE_STRING)
			return posDS != -1
		}
		return true
	}
}