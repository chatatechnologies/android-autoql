package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

class AutocompleteResponse(
	@SerializedName("reference_id")
	val referenceId: String,
	@SerializedName("message")
	val message: String,
	@SerializedName("data")
	val data: AutocompleteData
)

fun emptyAutocompleteResponse() = AutocompleteResponse(
	referenceId = "",
	message = "",
	data = emptyAutocompleteData()
)

class AutocompleteData(
	@SerializedName("matches")
	val matches: List<String>
)

fun emptyAutocompleteData() = AutocompleteData(
	matches = listOf()
)