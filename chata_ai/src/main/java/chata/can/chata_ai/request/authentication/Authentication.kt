package chata.can.chata_ai.request.authentication

import chata.can.chata_ai.pojo.autoQL.AutoQLData

object Authentication
{
	fun bearerToken(): String = "Bearer ${AutoQLData.JWT}"
	fun getAuthorizationJWT() = hashMapOf("Authorization" to bearerToken())
}