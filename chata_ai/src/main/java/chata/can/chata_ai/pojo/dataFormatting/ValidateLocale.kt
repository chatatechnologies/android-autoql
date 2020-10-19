package chata.can.chata_ai.pojo.dataFormatting

import java.text.SimpleDateFormat
import java.util.*

object ValidateLocale {
	fun isValid(locale: Locale): Boolean
	{
		return try {
			locale.isO3Language != null && locale.isO3Country != null
		} catch (ex: MissingResourceException) {
			false
		}
	}

	fun getMonth(iMonth: Int, locale: Locale): String
	{
		val formatter = SimpleDateFormat("MMMM", locale)
		val calendar = GregorianCalendar()
		calendar.set(Calendar.DAY_OF_MONTH, 1)
		calendar.set(Calendar.MONTH, iMonth - 1)
		return formatter.format(calendar.time)
	}
}