package chata.can.chata_ai.pojo.date

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer.aMonthShorts
import java.text.SimpleDateFormat
import java.util.*

object ChataDateFormat
{
	fun dateFormat(date: Date): String
	{
		return SinglentonDrawer.localLocale?.let { locale ->
			val format = SinglentonDrawer.monthYearFormat
			when(format.count { it == 'M' })
			{
				3 ->
				{
					val month = SimpleDateFormat("MM", locale).format(date)
					//region
					month.toIntOrNull()?.let {
						aMonthShorts[it] + " " + SimpleDateFormat("yyyy", locale).format(date)
					} ?: run {""}
					//endregion

				}
				else -> SimpleDateFormat("MM", locale).format(date)
			}
		} ?: run { "" }
	}
}