package chata.can.chata_ai.extension

fun ArrayList<String>.toListInt(): ArrayList<Int>
{
	val aList = ArrayList<Int>()
	for (item in this)
	{
		val dItem = item.toDoubleNotNull()
		val iItem = dItem.toInt()
		aList.add(iItem)
	}
	return aList
}

fun ArrayList<Int>.nextSeries(): Int
{
	return when
	{
		this.isEmpty() -> -1
		this.size > 1 -> this[3]
		else -> this[0]
	}
}