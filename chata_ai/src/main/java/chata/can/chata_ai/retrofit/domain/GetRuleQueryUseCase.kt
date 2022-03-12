package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.RuleQueryRepository
import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponseModel

class GetRuleQueryUseCase {
	private val repository = RuleQueryRepository()

	suspend fun getRuleQuery(id: String): RuleQueryResponseModel = repository.getRuleQuery(id)
}