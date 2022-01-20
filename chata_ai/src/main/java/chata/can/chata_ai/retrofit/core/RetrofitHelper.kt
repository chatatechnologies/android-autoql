package chata.can.chata_ai.retrofit.core

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.getMainURL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
	fun getRetrofit(): Retrofit {
		val gson = GsonBuilder().setLenient().create()

		return Retrofit.Builder()
			.baseUrl("${getMainURL()}${api1}")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
	}
}