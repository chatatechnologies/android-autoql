package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.LoginRepository

class PostLoginUseCase {
	private val repository = LoginRepository()

	suspend operator fun invoke(username: String, password: String) = repository.postLogin(username, password)
}