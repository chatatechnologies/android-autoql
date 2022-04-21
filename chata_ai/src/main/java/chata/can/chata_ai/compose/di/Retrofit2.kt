package chata.can.chata_ai.compose.di

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit2 {
	fun getRetrofit(): Retrofit {
		val gson = GsonBuilder().setLenient().create()

		val url = AutoQLData.domainUrl//.ifEmpty { "https://chata.ai/" }
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
	}
}