package chata.can.chata_ai.retrofit.core

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData.domainUrl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelperDynamic {
	fun getRetrofit(): Retrofit {
		val gson = GsonBuilder().setLenient().create()
		val url = domainUrl.ifEmpty { "https://chata.ai/" }

		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
	}
}