package chata.can.chata_ai.pojo.dataFormatting

import java.text.SimpleDateFormat
import java.util.*

object ValidateLocale {
	fun getMonth(iMonth: Int, locale: Locale, pattern: String = "MMMM"): String
	{
		val formatter = SimpleDateFormat(pattern, locale)
		val calendar = GregorianCalendar()
		calendar.set(Calendar.DAY_OF_MONTH, 1)
		calendar.set(Calendar.MONTH, iMonth - 1)
		val month = formatter.format(calendar.time)
		return if (month[0].isLowerCase())
			month.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
		else month
	}
}