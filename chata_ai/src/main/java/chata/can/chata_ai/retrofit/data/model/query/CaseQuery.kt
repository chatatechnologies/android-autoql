package chata.can.chata_ai.retrofit.data.model.query

import chata.can.chata_ai.pojo.query.CountColumn
import chata.can.chata_ai.retrofit.data.model.ColumnModel

class CaseQuery(
	private val columnsModel: ArrayList<ColumnModel>,
	private val numRows: Int
) {
	val countColumn = CountColumn()

	fun getCaseQuery() {
		countColumn.clearData()
	}

	fun getColumnType() {
		for (column in columnsModel) {
			column.type
		}
	}
}

enum class CaseEnumQuery{
	CASE_1,
	CASE_2,
	CASE_3,
	//CASE_4,
	CASE_5,
	CASE_6,
	NO_CASE;
}