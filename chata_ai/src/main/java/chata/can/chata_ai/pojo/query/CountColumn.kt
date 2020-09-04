package chata.can.chata_ai.pojo.query

class CountColumn
{
	var countDollarAMT = 0
	var countQuantity = 0
	var countPercent = 0
	var countNumber = 0
	var countDate = 0
	var countDateString = 0
	var countString = 0
	var countGroupable = 0

	fun clearData()
	{
		countDollarAMT = 0
		countQuantity = 0
		countPercent = 0
		countNumber = 0
		countDate = 0
		countDateString = 0
		countString = 0
		countGroupable = 0
	}

	fun numberColumns() = countDollarAMT + countQuantity + countPercent + countNumber

	fun isUseOnlyNumber(): Boolean
	{
		val isDollar = countDollarAMT > 0 && countQuantity == 0 && countPercent == 0
		val isQuality = countDollarAMT == 0 && countQuantity > 0 && countPercent == 0
		val isPercent = countDollarAMT == 0 && countQuantity == 0 && countPercent > 0
		return isDollar ||isQuality || isPercent
	}
}