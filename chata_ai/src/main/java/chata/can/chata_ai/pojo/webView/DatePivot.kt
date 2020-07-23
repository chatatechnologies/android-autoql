package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.extension.formatDecimals
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toDateMonthYear
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
			if (bodyTable.toString() == "<tbody></tbody>")
			{
				Pair("", 0)
			}
			else
				Pair("<table id=\"idTableDataPivot\">$headTable$bodyTable</table>", numRows)
			//endregion
		}
		return Pair("", 0)
	}

	fun buildDateString(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>
	): Pair<String, Int>
	{
		val mData = LinkedHashMap<String, Double>()
		val aYears = ArrayList<String>()

		if (aRows.firstOrNull()?.size == 2)
		{
			for (row in aRows)
			{
				val date = row[0]
				val value = row[1].toDoubleOrNull() ?: 0.0
				//mapping data
				mData[row[0]] = value

				//region save year
				val aDates = date.split("-")
				if (aDates.size == 2)
				{
					aYears.add(aDates[0])
				}
				//endregion
			}

			//region distinct on aYears
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
			val aMonths = DateFormatSymbols(Locale.US).months
			//region body table
			val bodyTable = StringBuilder("<tbody>")
			for (index in aMonths.indices)
			{
				val month = aMonths[index]
				val indexS = if ((index + 1) < 10) "0${index + 1}" else "${index + 1}"
				val sRow = StringBuilder("<td>$month</td>")
				var isNotHasZero = false
				for (year in aYears)
				{
					val cell = mData["$year-$indexS"]?.let {
						isNotHasZero = true
						it.formatDecimals(2)
					} ?: ""

					val newCell = if (cell.isNotEmpty())
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
			//endregion
			return Pair("<table id=\"idTableDataPivot\">$headTable$bodyTable</table>", numRows)
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
		val columnDate = aColumn[1]
		val dollarColumn = aColumn[2]

		val headTable1 = StringBuilder("<thead><tr>")
		headTable1.append("<th>${columnDate.displayName}</th>")
		for (provider in aProvider)
		{
			headTable1.append("<th>$provider</th>")
		}
		headTable1.append("</tr></thead>")

		val bodyTable = StringBuilder("<tbody>")
		val parseDate = SimpleDateFormat("yyyy-MM", Locale.US)
		val finalDate = SimpleDateFormat("MMM yyyy", Locale.US)
		for (date in aDates)
		{
			val sDate = if (date.contains("-"))
			{
				try {
					parseDate.parse(date)?.let {
						finalDate.format(it)
					}
				}
				catch (ex: Exception){ "" }
			}
			else
			{
				date.toDateMonthYear("MMM yyyy")
			}
			val sRow = StringBuilder("<td>$sDate</td>")
			for (provider in aProvider)
			{
				val cell = mData["${provider}_$date"]
					?.formatDecimals(2)
					?: 0.0.formatDecimals(2)

				val newCell = cell.formatWithColumn(dollarColumn)
				sRow.append("<td>$newCell</td>")
			}
			bodyTable.append("<tr>$sRow</tr>")
		}
		bodyTable.append("</tbody>")

		return "<table id=\"idTableDataPivot\">$headTable1$bodyTable</table>"
		//endregion
	}
}