package chata.can.chata_ai.retrofit.core

import chata.can.chata_ai.extension.clearDecimals
import chata.can.chata_ai.extension.formatSymbolDecimals
import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.date.ChataDateFormat
import chata.can.chata_ai.retrofit.ColumnEntity
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn
import java.text.SimpleDateFormat
import java.util.*

fun String.isEmptyOrZero() = isEmpty() || this == "0"

fun String.formatValue(
	column: ColumnEntity,
	currencySymbol: String = SinglentonDrawer.currencyCode,
	separateComma: String = ",",
	dateFormatShortMonth: SimpleDateFormat ?= null,
	dateFormat: SimpleDateFormat ?= null
): String {
	return when(column.typeColumn) {
		TypeColumn.DATE -> {
			if (isEmptyOrZero()) ""
			else {
				//TODO make unique for no repeat row by row
				//region locale
				val locale = SinglentonDrawer.localLocale?.let {
					Locale("es", "MX")
				} ?: run { Locale.US }
				//endregion
				//region date format
				val format = when {
					column.name.contains("month") -> {
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
				//endregion

				val aTmp = split(".")
				aTmp.firstOrNull()?.toIntOrNull()?.let {
					val date = Date(it * 1000L)
					dateFormatShortMonth?.let { dateFormatShortMonth ->
						val monthSp = dateFormatShortMonth.format(date)
						val index = SinglentonDrawer.aMonthsSp1.indexOf(monthSp)
						dateFormat?.let { dateFormat ->
							val out = dateFormat.format(date)

							if (index != -1)
								out.replace(SinglentonDrawer.aMonthsSp1[index], SinglentonDrawer.aMonthsSp[index])
							else out
						}
					}
				} ?: run { "" }
			}
		}
		TypeColumn.DATE_STRING -> {
			return if (isEmptyOrZero()) ""
			else {
				if (contains(ignoreCase = true, other = "w")) this
				else {
					val aData = split("-")
					if (aData.size > 1) {
						SinglentonDrawer.localLocale?.let { locale ->
							val dateFormat = SimpleDateFormat("yyyy-MM", locale)
							try {
								dateFormat.parse(this)?.let { date ->
									ChataDateFormat.dateFormat(date)
								}
							} catch (ex: Exception) {""}
						} ?: run { "" }
					}
					else ""
				}
			}
		}
		TypeColumn.DOLLAR_AMT -> {
			toDoubleNotNull()
				.formatSymbolDecimals(
					prefix = currencySymbol,
					commaCharacter = separateComma
				)
		}
		TypeColumn.PERCENT -> {
			val double = toDoubleNotNull() * 100
			val classCSS = when {
				double > 0 -> "green"
				double < 0 -> "red"
				else -> ""
			}
			val value = double.formatSymbolDecimals(suffix = "%")
			"<span class='$classCSS'>$value</span>"
		}
		TypeColumn.QUANTITY -> clearDecimals()
		else -> this
	}
}