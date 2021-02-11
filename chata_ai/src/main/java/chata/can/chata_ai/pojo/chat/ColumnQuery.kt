package chata.can.chata_ai.pojo.chat

data class ColumnQuery(
	val isGroupable: Boolean,
	var type: TypeDataQuery,
	val name: String,
	val displayName: String = "",
	val isVisible: Boolean)
{
	var formatDate = ""
}