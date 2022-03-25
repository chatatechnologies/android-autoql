package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.SafetyNetRepository
import chata.can.chata_ai.retrofit.data.model.SafetyNetResponse

class GetSafetyNetUseCase {
	private val repository = SafetyNetRepository()

	suspend fun getSafetyNet(text: String): SafetyNetResponse {
		return repository.getSafetyNet(text)
	}
}