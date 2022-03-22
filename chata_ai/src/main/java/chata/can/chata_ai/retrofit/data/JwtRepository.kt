package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.data.network.JwtService

class JwtRepository {
	private val api = JwtService()

	suspend fun callJwt(): String {
		return api.callJwt(
			displayName = AutoQLData.userID,
			projectID = AutoQLData.projectId
		)
	}
}