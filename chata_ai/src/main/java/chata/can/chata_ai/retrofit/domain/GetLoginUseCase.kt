package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.LoginRepository

class GetLoginUseCase {
	private val repository = LoginRepository()

	suspend fun postLogin(username: String, password: String): String {
		return repository.postLogin(username, password)
	}
}