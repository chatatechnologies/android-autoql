package chata.can.chata_ai.pojo.chat

data class ChatData(
	val typeView: Int,
	var message: String,
	val simpleQuery: SimpleQuery ?= null)