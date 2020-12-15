package chata.can.chata_ai.model

class BubbleData(
	val customerName: String,
	val title: String,
	val introMessage: String,
	val inputPlaceholder: String,
	val maxMessage: Int,
	val clearOnClose: Boolean,
	var isDarkenBackgroundBehind: Boolean,
	var enableVoiceRecord: Boolean,
	val isDataMessenger: Boolean)