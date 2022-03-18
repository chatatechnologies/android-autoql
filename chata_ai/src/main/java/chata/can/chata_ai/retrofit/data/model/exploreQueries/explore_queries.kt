package chata.can.chata_ai.retrofit.data.model.exploreQueries

import com.google.gson.annotations.SerializedName

class ValidateQueryModel(
	@SerializedName("data")
	val data: ValidateQueryData?
)

fun emptyValidateQueryModel() = ValidateQueryModel(
	data = emptyValidateQueryData()
)

class ValidateQueryData(
	@SerializedName("query")
	val query: String,
	@SerializedName("replacements")
	val replacements: List<Replacement>,
	@SerializedName("text")
	val text: String
)

fun emptyValidateQueryData() = ValidateQueryData(
	query = "",
	replacements = listOf(),
	text = ""
)

class Replacement(
	@SerializedName("end")
	val end: Int,
	@SerializedName("start")
	val start: Int
)