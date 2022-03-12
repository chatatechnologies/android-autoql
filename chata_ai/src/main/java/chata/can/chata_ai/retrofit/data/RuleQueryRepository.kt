package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.network.RuleQueryService

class RuleQueryRepository {
	private val api = RuleQueryService()

	suspend fun getRuleQuery(): String/*RuleQueryResponseModel*/ {
		val response = api.getRuleQuery()
		return response.queryResult
	}
}