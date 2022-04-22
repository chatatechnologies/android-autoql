package chata.can.chata_ai.retrofit.core

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelperDynamic {
	fun getRetrofit(): Retrofit {
		val gson = GsonBuilder().setLenient().create()

		val url = AutoQLData.domainUrl
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
	}
}