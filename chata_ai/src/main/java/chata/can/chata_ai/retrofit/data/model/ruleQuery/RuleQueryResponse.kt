package chata.can.chata_ai.retrofit.data.model.ruleQuery

import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import org.json.JSONObject

class RuleQueryResponse(private val queryResultData: QueryResultData) {

	val contentResponse = ""

	fun getResponse() {
		// columns
		// display_type
		// limit_row_num
		// query_id
		// rows
		// sql

		val json = JSONObject().put("query", "")
		val queryBase = QueryBase(json).apply {
			//region columns
			val _columns = queryResultData.columns.map {
				val typeColumn = enumValueOfOrNull(it.type) ?: run { TypeDataQuery.UNKNOWN }
				ColumnQuery(
					it.groupable,
					typeColumn,
					it.name,
					it.displayName,
					it.isVisible
				)
			}
			_columns.toString()
			//endregion
		}

	}
}