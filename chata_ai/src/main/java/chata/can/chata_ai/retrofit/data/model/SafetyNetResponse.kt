package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

class SafetyNetResponse(
	@SerializedName("reference_id")
	val referenceId: String,
	@SerializedName("message")
	val message: String,
	@SerializedName("data")
	val data: SafetyNextData
)

fun emptySafetyNetResponse() = SafetyNetResponse(
	referenceId = "",
	message = "",
	data = emptySafetyData()
)

class SafetyNextData(
	@SerializedName("text")
	val text: String,
	@SerializedName("replacements")
	val replacements: List<String> = listOf(),
	@SerializedName("query")
	val query: String
)

fun emptySafetyData() = SafetyNextData(
	text = "",
	replacements = listOf(),
	query = ""
)