package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
	@SerializedName("nameService")
	val nameService: String,
	@SerializedName("RESPONSE")
	val response: String
)