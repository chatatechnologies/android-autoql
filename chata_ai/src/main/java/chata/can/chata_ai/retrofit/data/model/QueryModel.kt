package chata.can.chata_ai.retrofit.data.model

import chata.can.chata_ai.retrofit.QueryResultEntity
import chata.can.chata_ai.retrofit.core.formatValue
import chata.can.chata_ai.retrofit.core.internalServiceError
import chata.can.chata_ai.retrofit.core.keySuggestion
import chata.can.chata_ai.retrofit.data.model.ruleQuery.*

class QueryModel(private val queryResultEntity: QueryResultEntity) {

	private fun isSimpleText(): Boolean {
		val rows = queryResultEntity.rows
		val sizeLevel1 = rows.size
		return rows.firstOrNull()?.let {
			val sizeLevel2 = it.size
			return sizeLevel1 == 1 && sizeLevel2 == 1
		} ?: run { false }
	}

	private fun getSimpleText(): String {
		return queryResultEntity.rows.firstOrNull()?.let {
			it.firstOrNull() ?: run { "" }
		} ?: run { "" }
	}

	private fun errorTypeRuleQuery() = textTypeRuleQuery(internalServiceError)

	fun defineContent(): TypeRuleQuery {
		queryResultEntity.run {
			val value = when {
				rows.isEmpty() || isSimpleText() || displayType == keySuggestion -> {
					queryResultEntity.columnsEntity.firstOrNull()?.let { column ->
						val value = getSimpleText().formatValue(column)
						textTypeRuleQuery(value)
//					webTypeRuleQuery("<p style=\"color: blueviolet;\">Text for webView</p>")
					} ?: run { errorTypeRuleQuery() }
				}
				else -> errorTypeRuleQuery()
			}
			return value
		}
	}
}