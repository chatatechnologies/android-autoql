package chata.can.chata_ai.compose.model

data class ValidateQueryResponse(
	val message: String = "",
	val reference_id: String = "",
	val data: ValidateQueryData = emptyValidateQueryData()
)

data class ValidateQueryData(
	val text: String = "",
	val query: String = "",
	val replacements: List<String> = listOf()
)

fun emptyValidateQueryData() = ValidateQueryData(
	text = "",
	query = ""
)