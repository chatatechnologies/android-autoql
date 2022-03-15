package chata.can.chata_ai.retrofit.data.model.ruleQuery

import chata.can.chata_ai.retrofit.QueryResultEntity
import chata.can.chata_ai.retrofit.data.model.QueryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RuleQueryResponse {
	suspend fun getRuleQueryResponse(queryResultData: QueryResultEntity): TypeRuleQuery {
		return withContext(Dispatchers.IO) {
			val queryModel = QueryModel(queryResultData)
			queryModel.defineContent()
		}
	}
}