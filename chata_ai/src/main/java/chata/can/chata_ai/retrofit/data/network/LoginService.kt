package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun setLogin(username: String, password: String): String {

		val requestBody: RequestBody = MultipartBody.Builder()
			.setType(MultipartBody.FORM)
			.addFormDataPart("username", username)
			.addFormDataPart("password", password)
			.build()

		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(LoginApiClient::class.java)
					.postLogin(requestBody)
				response.body() ?: ""
			} catch (ex: Exception) {
				""
			}
		}
	}
}