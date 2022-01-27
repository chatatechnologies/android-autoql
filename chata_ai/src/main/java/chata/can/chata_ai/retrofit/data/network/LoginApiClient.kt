package chata.can.chata_ai.retrofit.data.network

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LoginApiClient {
	@POST("login")
	suspend fun postLogin(
		@Body params: RequestBody
	): Response<String>
}