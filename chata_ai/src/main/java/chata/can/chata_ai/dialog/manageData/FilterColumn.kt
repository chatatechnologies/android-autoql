package chata.can.chata_ai.dialog.manageData

data class FilterColumn(
	val nameColumn: String,
	val isSelected: Boolean = true,
	val isOnlyText: Boolean = false,
	val allowClick: Boolean = false,
	val indexColumn: Int = -1)