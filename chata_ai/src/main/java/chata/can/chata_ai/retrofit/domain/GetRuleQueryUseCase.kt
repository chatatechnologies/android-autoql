package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.RuleQueryRepository
import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponseModel

class GetRuleQueryUseCase {
	private val repository = RuleQueryRepository()

	suspend operator fun invoke(): RuleQueryResponseModel = repository.getRuleQuery()
}