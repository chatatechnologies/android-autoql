package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponseModel
import chata.can.chata_ai.retrofit.data.network.RuleQueryService

class RuleQueryRepository {
	private val api = RuleQueryService()

	suspend fun getRuleQuery(): RuleQueryResponseModel/*RuleQueryResponseModel*/ {
		val response = api.getRuleQuery()
		response.toString()
		return response
	}
}