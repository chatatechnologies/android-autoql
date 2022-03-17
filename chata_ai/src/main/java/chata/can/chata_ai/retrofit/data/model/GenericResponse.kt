package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class GenericResponse(
	@SerializedName("message")
	val message: String,
	@SerializedName("reference_id")
	val reference_id: String
)