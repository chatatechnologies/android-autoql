package chata.can.chata_ai.retrofit.data.model.ruleQuery

import androidx.lifecycle.MutableLiveData
import chata.can.chata_ai.retrofit.data.model.QueryModel

class RuleQueryResponse(private val queryResultData: QueryResultData) {
	fun getResponse(contentResponse: MutableLiveData<String>) {
		val queryModel = QueryModel(queryResultData, contentResponse)
		queryModel.defineContent()
	}
}