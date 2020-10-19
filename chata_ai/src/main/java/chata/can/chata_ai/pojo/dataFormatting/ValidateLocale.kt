package chata.can.chata_ai.pojo.dataFormatting

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
}