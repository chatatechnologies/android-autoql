package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.QueryResultEntity
import chata.can.chata_ai.retrofit.data.RuleQueryRepository

class GetRuleQueryUseCase {
	private val repository = RuleQueryRepository()

	suspend fun getRuleQuery(id: String): QueryResultEntity = repository.getRuleQuery(id)
}