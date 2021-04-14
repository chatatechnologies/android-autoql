package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Table
{
	fun generateDataTable(
		aRows: ArrayList<ArrayList<String>>,
	  aColumns: ArrayList<ColumnQuery>,
		aIndex: ArrayList<Int>,
		isDataCenter: Boolean) : String
	{
		val formatter = DecimalFormat("###,###,##0.00")

		val aDataTable = ArrayList<String>()
		try
		{
			for (aRow in aRows)
			{
				var sRow = ""
				for (indexValue in aIndex)
				{
					val oColumn = aColumns[indexValue]
					//if (oColumn.isVisible) { }
					val cell = aRow[indexValue]

					val sCell = when(oColumn.type)
					{
						TypeDataQuery.QUANTITY -> {
							if (isDataCenter) cell
							else "\"$cell\""
						}
						TypeDataQuery.DOLLAR_AMT ->
						{
							val value = cell.replace("##", "")

							if (isDataCenter)formatter.format(value.toDouble()).replace(",", "")
							else "\"$" + formatter.format(value.toDouble()) + "\""
						}
						TypeDataQuery.NUMBER ->
						{
							if (isDataCenter)cell.replace("##","")
							else "\"${cell.replace("##",", ")}\""
						}
						TypeDataQuery.DATE ->
						{
							if(cell.isEmpty() || cell == "0" )
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

								val date = Date(cell.toFloat().toLong() * 1000)
								"\"${dateFormat.format(date).replace(".", ",")}\""
							}
						}
						else ->
						{
							"\"${cell.replace("##",", ")}\""
						}
					}

					sRow += ",$sCell"
				}
				sRow = "[${sRow.removePrefix(",")}]"
				aDataTable.add(sRow)
			}
		}
		catch (ex: Exception){}
		return aDataTable.toString()
	}
}