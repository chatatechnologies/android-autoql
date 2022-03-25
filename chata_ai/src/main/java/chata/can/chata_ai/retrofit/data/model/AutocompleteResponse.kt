package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

class AutocompleteResponse(
	@SerializedName("reference_id")
	val referenceId: String,
	@SerializedName("message")
	val message: String,
//	@SerializedName("data")
//	val data: Any
)

fun emptyAutocompleteResponse() = AutocompleteResponse(
	referenceId = "",
	message = ""
)