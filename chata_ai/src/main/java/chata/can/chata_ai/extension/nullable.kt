package chata.can.chata_ai.extension

fun <T: Any, R: Any> Collection<T?>.whenAllNotNull(block: (List<T>)->R) {
	if (this.all { it != null })
	{
		block(this.filterNotNull())
	}
}