package chata.can.chata_ai.pojo.date

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer.aMonthShorts
import chata.can.chata_ai.pojo.SinglentonDrawer.mMonthShort
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
					val monthIndex = SimpleDateFormat("MM", locale).format(date)
					var month = ""
					monthIndex.toIntOrNull()?.let { index ->
						val aLocale = locale.toString().split("_")
						if (aLocale.size > 1)
						{
							mMonthShort[aLocale[0]]?.let {
								month = it[index - 1]
							}
						}
						month + " " + SimpleDateFormat("yyyy", locale).format(date)
					} ?: run {""}
				}
				else -> SimpleDateFormat("MM", locale).format(date)
			}
		} ?: run { "" }
	}
}