package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.extension.isNumber
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

	fun getGroupableIndices(columns: List<ColumnEntity>, count: Int): ArrayList<Int> {
		val aIndices = ArrayList<Int>()
		for (index in columns.indices) {
			if (columns[index].groupable) {
				aIndices.add(index)
				if (count == aIndices.size) break
			}
		}
		return aIndices
	}

	fun getNumberIndices(columns: List<ColumnEntity>, count: Int = 0): ArrayList<Int> {
		val aIndices = ArrayList<Int>()
		for (index in columns.indices) {
			if (columns[index].typeColumn.isNumber()) {
				aIndices.add(index)
				if (count != 0 && count == aIndices.size) break
			}
		}
		return aIndices
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