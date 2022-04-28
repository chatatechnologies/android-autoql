package chata.can.chata_ai.compose.model

data class AutocompleteApiResponse(
	val reference_id: String = "",
	val message: String = "",
	val data: AutocompleteApiData = emptyAutocompleteApiData()
)

fun emptyAutocompleteApiResponse() = AutocompleteApiResponse(
	reference_id = "",
	message = ""
)

data class AutocompleteApiData(
	val matches: List<String> = listOf()
)

fun emptyAutocompleteApiData() = AutocompleteApiData(
	matches = listOf()
)