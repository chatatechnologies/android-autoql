package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.RuleQueryRepository

class GetRuleQueryUseCase {
	private val repository = RuleQueryRepository()

	suspend operator fun invoke(): String = repository.getRuleQuery()
}