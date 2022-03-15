package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.QueryResultEntity
import chata.can.chata_ai.retrofit.data.model.ruleQuery.emptyQueryResultData
import chata.can.chata_ai.retrofit.data.network.RuleQueryService
import chata.can.chata_ai.retrofit.toQueryResultEntity

class RuleQueryRepository {
	private val api = RuleQueryService()

	suspend fun getRuleQuery(idRule: String): QueryResultEntity {
		val response = api.getRuleQuery(idRule)
		val queryResultData = response.queryResult.data ?: emptyQueryResultData()
		return queryResultData.toQueryResultEntity()
	}
}