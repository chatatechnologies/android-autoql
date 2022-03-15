package chata.can.chata_ai.retrofit.data.model

import chata.can.chata_ai.retrofit.core.keySuggestion
import chata.can.chata_ai.retrofit.data.model.ruleQuery.*

class QueryModel(queryResultData: QueryResultData) {
	private val displayType = queryResultData.displayType
	private val limitRowNum = queryResultData.limitRowNum
	private val queryId = queryResultData.queryId
	private val columnsModel = ArrayList<ColumnModel>()
	private val rows = ArrayList< ArrayList<String> >()

	init {
		columnsModel.addAll(queryResultData.columns)
		queryResultData.rows.forEach {
			val innerRows = ArrayList<String>()
			innerRows.addAll(it)
			rows.add(innerRows)
		}
	}

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

	fun defineContent(): TypeRuleQuery {
		val value = when {
			rows.size == 0 || isSimpleText() || displayType == keySuggestion -> {
				columnsModel.firstOrNull()?.let {
					textTypeRuleQuery(getSimpleText())
//					webTypeRuleQuery("<p style=\"color: blueviolet;\">Text for webView</p>")
				} ?: run { emptyTypeRuleQuery }
			}
			else -> emptyTypeRuleQuery
		}
		return value
	}
}