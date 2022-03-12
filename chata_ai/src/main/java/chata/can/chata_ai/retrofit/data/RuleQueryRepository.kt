package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponseModel
import chata.can.chata_ai.retrofit.data.network.RuleQueryService

class RuleQueryRepository {
	private val api = RuleQueryService()

	suspend fun getRuleQuery(idRule: String): RuleQueryResponseModel {
		val response = api.getRuleQuery(idRule)
		return response
	}
}