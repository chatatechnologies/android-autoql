package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.network.LoginService

class LoginRepository {
	private val api = LoginService()

	suspend fun postLogin(username: String, password: String): String {
		val response = api.setLogin(username, password)
		return response
	}
}