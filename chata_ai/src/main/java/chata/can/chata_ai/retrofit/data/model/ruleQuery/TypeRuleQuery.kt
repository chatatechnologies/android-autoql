package chata.can.chata_ai.retrofit.data.model.ruleQuery

enum class TypeEnum { TEXT, WEB }

class TypeRuleQuery(
	val typeEnum: TypeEnum,
	val contentResponse: String
) {
	fun isNotEmpty() = contentResponse.isNotEmpty()
}

val emptyTypeRuleQuery = TypeRuleQuery(TypeEnum.TEXT, "")

fun textTypeRuleQuery(contentResponse: String) = TypeRuleQuery(TypeEnum.TEXT, contentResponse)

fun webTypeRuleQuery(contentResponse: String) = TypeRuleQuery(TypeEnum.WEB, contentResponse)