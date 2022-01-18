package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.core.RetrofitHelper
import chata.can.chata_ai.retrofit.data.model.LoginModel
import chata.can.chata_ai.retrofit.data.model.loginModelEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun setLogin(): LoginModel {
		return withContext(Dispatchers.IO) {
			val response = retrofit.create(LoginApiClient::class.java).postLogin()
			response.body() ?: loginModelEmpty()
		}
	}
}