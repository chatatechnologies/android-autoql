package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.LoginModel
import retrofit2.Response
import retrofit2.http.POST

interface LoginApiClient {
	@POST("/login")
	suspend fun postLogin(): Response<LoginModel>
}