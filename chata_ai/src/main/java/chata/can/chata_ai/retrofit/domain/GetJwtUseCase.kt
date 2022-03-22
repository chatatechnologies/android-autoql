package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.JwtRepository

class GetJwtUseCase {
	private val repository = JwtRepository()

	suspend fun callJwt() : String {
		return repository.callJwt()
	}
}