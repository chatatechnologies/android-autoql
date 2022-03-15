package chata.can.chata_ai.retrofit

import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.retrofit.data.model.ColumnModel
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn
import chata.can.chata_ai.retrofit.data.model.ruleQuery.QueryResultData

fun QueryResultData.toQueryResultEntity(): QueryResultEntity {
	return QueryResultEntity(columns, displayType, limitRowNum, queryId, rowLimit, rows)
}

class QueryResultEntity(
	private val columns: List<ColumnModel>,
	val displayType: String,
	val limitRowNum: Int,
	val queryId: String,
	val rowLimit: Int,
	val rows: List< List<String> >
) {
	val columnsEntity = columns.map { it.toColumnEntity() }
}

fun ColumnModel.toColumnEntity(): ColumnEntity {
	return ColumnEntity(displayName, groupable, isVisible, multiSeries, name, type)
}

class ColumnEntity(
	val displayName: String,
	val groupable: Boolean,
	val isVisible: Boolean,
	val multiSeries: Boolean,
	val name: String,
	val type: String
) {
	val typeColumn = enumValueOfOrNull(
		type
	) ?: run { TypeColumn.UNKNOWN }
}