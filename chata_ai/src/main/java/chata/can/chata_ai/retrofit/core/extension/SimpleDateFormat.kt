package chata.can.chata_ai.retrofit.core.extension

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.retrofit.ColumnEntity
import java.text.SimpleDateFormat

fun getDateFormatShortMonth(): SimpleDateFormat {
	val locale = getCurrentLocale()
	return SimpleDateFormat("MMM", locale)
}

fun getFormatMonth(columnEntity: ColumnEntity): SimpleDateFormat {
	val locale = getCurrentLocale()
	val format = when {
		columnEntity.name.contains("month") -> {
			SinglentonDrawer.monthYearFormat.replace("Y", "y")
		}
		SinglentonDrawer.localLocale != null -> {
			"d 'de' MMM 'de' yyyy"
		}
		else -> {
			SinglentonDrawer.dayMonthYearFormat.
			replace("Y", "y").
			replace("DD", "d")
		}
	}
	return SimpleDateFormat(format, locale)
}