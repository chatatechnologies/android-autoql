package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.SafetyNetResponse
import chata.can.chata_ai.retrofit.data.network.SafetyNetService

class SafetyNetRepository {
	private val api = SafetyNetService()

	suspend fun getSafetyNet(text: String): SafetyNetResponse {
		return api.getSafetyNet(text)
	}
}