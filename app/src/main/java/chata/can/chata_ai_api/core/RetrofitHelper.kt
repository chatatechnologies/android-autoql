package chata.can.chata_ai_api.core

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.getMainURL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
	fun getRetrofit(): Retrofit {
		return Retrofit.Builder()
			.baseUrl("${getMainURL()}$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
}