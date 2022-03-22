package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JwtService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun callJwt(
		displayName: String,
		projectID: String
	): String {

		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(JwtApiClient::class.java)
					.getJWT(
						beaverToken = "Bearer ${AutoQLData.JWT}",
						displayName = displayName,
						projectID = projectID
					)
				response.body() ?: ""
			} catch (ex: Exception) {
				""
			}
		}
	}
}