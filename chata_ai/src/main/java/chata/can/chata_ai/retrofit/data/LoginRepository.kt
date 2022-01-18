package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.LoginModel
import chata.can.chata_ai.retrofit.data.network.LoginService

class LoginRepository {
	private val api = LoginService()

	suspend fun postLogin(): LoginModel {
		val response = api.setLogin()
		response.toString()
		return response
	}
}