package chata.can.chata_ai.pojo.chat

data class ChatData(
	val typeView: Int,
	val message: String,
	val simpleQuery: SimpleQuery ?= null)