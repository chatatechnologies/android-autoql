package chata.can.chata_ai.retrofit.model

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {

	fun getClientService(): APIService {
		val retrofit = Retrofit.Builder()
			.baseUrl("${AutoQLData.domainUrl}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		return retrofit.create(APIService::class.java)
	}
}