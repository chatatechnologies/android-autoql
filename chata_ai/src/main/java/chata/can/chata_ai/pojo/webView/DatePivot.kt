package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.extension.formatDecimals
import chata.can.chata_ai.extension.formatWithColumn
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

object DatePivot
{
	private fun dateWithFormat(dateLong: Int): String
	{
		return try
		{
			val dateFormat = SimpleDateFormat("MMMM_yyyy", Locale.US)
			val date = Date(dateLong * 1000L)
			dateFormat.format(date)
		}
		catch (e: Exception)
		{
			"No date"
		}
	}

	fun buildBi(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>): Pair<String, Int>
	{
		val mData = LinkedHashMap<String, Double>()
		val aYears = ArrayList<String>()

		if (aRows.firstOrNull()?.size == 2)
		{
			for (row in aRows)
			{
				val date = row[0]
				val value = row[1].toDoubleOrNull() ?: 0.0

				val aTmp = date.split(".")
				aTmp.firstOrNull()?.toIntOrNull()?.let {
					val keyMonthYear = dateWithFormat(it)
					mData[keyMonthYear] = value

					val aDate = keyMonthYear.split("_")
					if (aDate.size == 2)
					{
						aYears.add(aDate[1])
					}
				}
			}
			//region clear data for dates
			val aMonths = DateFormatSymbols(Locale.US).months
			with(aYears)
			{
				val aYearDistinct = distinct()
				clear()
				addAll(aYearDistinct)
				sort()
			}
			//endregion

			val dollarColumn = aColumn[1]

			//region create table head
			val headTable = StringBuilder("<thead><tr><th>Month</th>")
			for (year in aYears)
			{
				headTable.append("<th>$year</th>")
			}
			headTable.append("</tr></thead>")
			//endregion

			var numRows = 1
			//region table
			val bodyTable = StringBuilder("<tbody>")
			for (month in aMonths)
			{
				val sRow = StringBuilder("<td>$month</td>")
				var isNotHasZero = false
				for (year in aYears)
				{
					val cell = mData["${month}_$year"]?.let {
						isNotHasZero = true
						it.formatDecimals(2)
					} ?:""

					val newCell =
						if (cell.isNotEmpty())
							cell.formatWithColumn(dollarColumn)
						else cell
					sRow.append("<td>$newCell</td>")
				}

				if (isNotHasZero)
				{
					numRows++
					bodyTable.append("<tr>$sRow</tr>")
				}
			}
			bodyTable.append("</tbody>")
			return Pair("<table id=\"idTableDataPivot\">$headTable$bodyTable</table>", numRows)
			//endregion
		}
		return Pair("", 0)
	}

	fun buildTri(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>): String
	{
		val mData = LinkedHashMap<String, Double>()
		val aProvider = ArrayList<String>()
		val aDates = ArrayList<String>()

		//row by row
		for (row in aRows)
		{
			//cell by cell
			if (row.size == 3)
			{
				val provider = row[0]
				val date = row[1]
				val value = row[2].toDoubleOrNull() ?: 0.0

				aProvider.add(provider)
				aDates.add(date)
				mData["${provider}_$date"] = value
			}
		}

		//region clear data for provider and dates
		with(aProvider)
		{
			val aProviderDistinct = distinct()
			clear()
			addAll(aProviderDistinct)
		}

		with(aDates)
		{
			val aDateDistinct = distinct()
			aDates.clear()
			aDates.addAll(aDateDistinct)
			aDates.sort()
		}
		//endregion

		//region create table head
		val headTable = StringBuilder("<thead><tr>")
		val columnProvider = aColumn[0]
		val columnDate = aColumn[1]
		val dollarColumn = aColumn[2]
		headTable.append("<th>${columnProvider.name}</th>")
		for (date in aDates)
		{
			val dateString = date.formatWithColumn(columnDate)
			headTable.append("<th>$dateString</th>")
		}
		headTable.append("</tr></thead>")
		//endregion

		//region table
		val bodyTable = StringBuilder("<tbody>")
		for (provider in aProvider)
		{
			val sRow = StringBuilder("<td>$provider</td>")
			for (date in aDates)
			{
				val cell = mData["${provider}_$date"]
					?.formatDecimals(2)
					?: "0"

				val newCell = cell.formatWithColumn(dollarColumn)
				sRow.append("<td>$newCell</td>")
			}
			bodyTable.append("<tr>$sRow</tr>")
		}

		bodyTable.append("</tbody>")

		return "<table id=\"idTableDataPivot\">$headTable$bodyTable</table>"
		//endregion
	}
}