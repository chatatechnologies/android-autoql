package chata.can.chata_ai.retrofit.data.model.query

import chata.can.chata_ai.retrofit.core.formatValue
import chata.can.chata_ai.retrofit.core.keySuggestion
import chata.can.chata_ai.retrofit.data.model.ColumnModel
import chata.can.chata_ai.retrofit.toColumnEntity

fun QueryDashboardResponse.queryResponseDataToQueryEntity(): QueryEntity {
	data.run {
		return QueryEntity(
			message = message,
			columns = columns,
			rows = rows,
			rowLimit = rowLimit,
			limitRowNum = limitRowNum,
			displayType = displayType,
			queryId = queryId,
			text = text,
			interpretation = interpretation,
			sql = sql
		)
	}
}

class QueryEntity(
	val message: String,
	val columns: List<ColumnModel>,
	val rows: List< List<String> >,
	val rowLimit: Int,
	val limitRowNum: Int,
	val displayType: String,
	val queryId: String,
	val text: String,
	val interpretation: String,
	val sql: List<String>
) {
	var contentDisplayQuery = ""
	val columnsEntity = columns.map { it.toColumnEntity() }

	private fun isSimpleText(): Boolean {
		val sizeLevel1 = rows.size
		return rows.firstOrNull()?.let {
			val sizeLevel2 = it.size
			return sizeLevel1 == 1 && sizeLevel2 == 1
		} ?: run { false }
	}

	private fun getSimpleText(): String {
		return rows.firstOrNull()?.let {
			it.firstOrNull() ?: run { "" }
		} ?: run { "" }
	}

	fun getContentDisplay(): String {
		return when {
			message == "No Data Found" -> {
				message
			}
			rows.isEmpty() || isSimpleText() || displayType == keySuggestion -> {
				columnsEntity.firstOrNull()?.let { column ->
					getSimpleText().formatValue(column)
				} ?: run { "" }
			}
			else -> ""
		}
	}
























}