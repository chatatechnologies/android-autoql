package chata.can.chata_ai.retrofit.data.model

import androidx.lifecycle.MutableLiveData
import chata.can.chata_ai.retrofit.core.keySuggestion
import chata.can.chata_ai.retrofit.data.model.ruleQuery.QueryResultData

class QueryModel(
	queryResultData: QueryResultData,
	private val contentResponse: MutableLiveData<String>
	) {
	val displayType = queryResultData.displayType
	val limitRowNum = queryResultData.limitRowNum
	val queryId = queryResultData.queryId
	val columnsModel = ArrayList<ColumnModel>()
	val rows = ArrayList< ArrayList<String> >()

	init {
		columnsModel.addAll(queryResultData.columns)
		queryResultData.rows.forEach {
			val innerRows = ArrayList<String>()
			innerRows.addAll(it)
			rows.add(innerRows)
		}
	}

	fun isSimpleText(): Boolean {
		val sizeLevel1 = rows.size
		return rows.firstOrNull()?.let {
			val sizeLevel2 = it.size
			return sizeLevel1 == 1 && sizeLevel2 == 1
		} ?: run { false }
	}

	fun getSimpleText(): String {
		return rows.firstOrNull()?.let {
			it.firstOrNull() ?: run { "" }
		} ?: run { "" }
	}

	fun defineContent() {
		when {
			rows.size == 0 || isSimpleText() || displayType == keySuggestion -> {
				columnsModel.firstOrNull()?.let {
					contentResponse.postValue( getSimpleText() )
				}
			}
		}
	}
}