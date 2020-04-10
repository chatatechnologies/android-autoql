package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Table
{
	fun generateDataTable(aRows: ArrayList<ArrayList<String>>, aColumns: ArrayList<ColumnQuery>, isDataCenter: Boolean) : String
	{
		val formatter = DecimalFormat("###,###,##0.00")

		val aDataTable = ArrayList<String>()
		try
		{
			for (aRow in aRows)
			{
				//val text = element.replace(", ", "##")
				//val aRow = ArrayList(text.split(","))
				var iteration = 0
				val sRow = aRow.joinTo(StringBuilder("["), postfix = "]")
				{
					val oColumn = aColumns[iteration++]

					when(oColumn.type)
					{
						TypeDataQuery.QUANTITY -> {
							if (isDataCenter) it
							else "\"$it\""
						}
						TypeDataQuery.DOLLAR_AMT ->
						{
							val value = it.replace("##", "")

							if (isDataCenter)formatter.format(value.toDouble()).replace(",", "")
							else "\"$" + formatter.format(value.toDouble()) + "\""
						}
						TypeDataQuery.NUMBER ->
						{
							if (isDataCenter)it.replace("##","")
							else "\"${it.replace("##",", ")}\""
						}
						TypeDataQuery.DATE ->
						{
							if(it.isEmpty() || it == "0" )
								"\" \""
							else
							{
								val sName = oColumn.name

								val hasYear = sName.contains("year")
								val hasMonth = sName.contains("month")
								val hasDay = sName.contains("day")

								val dateFormat = SimpleDateFormat(
									if(hasYear)
									{
										"yyyy"
									}
									else if(hasMonth && !hasDay)
									{
										"MMM yyyy"
									}
									else
									{
										"MMM dd, yyyy"
									}, Locale.US)
								dateFormat.timeZone = TimeZone.getTimeZone("GMT")

								val date = Date(it.toFloat().toLong() * 1000)
								"\"${dateFormat.format(date).replace(".", ",")}\""
							}
						}
						else ->
						{
							"\"${it.replace("##",", ")}\""
						}
					}
				}.toString()
				aDataTable.add(sRow)
			}
		}
		catch (ex: Exception){}
		return aDataTable.toString()
	}
}