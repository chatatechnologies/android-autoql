package chata.can.chata_ai.pojo.chat

data class ColumnQuery(
	val isGroupable: Boolean,
	val type: String,
	val name: String,
	val isActive: Boolean)