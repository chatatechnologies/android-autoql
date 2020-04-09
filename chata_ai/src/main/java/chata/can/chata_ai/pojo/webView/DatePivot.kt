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
			var headTable = "<thead><tr><th>Month</th>"
			for (year in aYears)
			{
				headTable += "<th>$year</th>"
			}
			headTable += "</tr></thead>"
			//endregion

			var numRows = 1
			//region table
			var bodyTable = "<tbody>"
			for (month in aMonths)
			{
				var sRow = "<td>$month</td>"
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
					sRow += "<td>$newCell</td>"
				}

				if (isNotHasZero)
				{
					numRows++
					bodyTable += "<tr>$sRow</tr>"
				}
			}
			bodyTable += "</tbody>"
			return Pair("<table id=\"pivotTable\">$headTable$bodyTable</table>", numRows)
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
		var headTable = "<thead><tr>"
		val columnProvider = aColumn[0]
		val columnDate = aColumn[1]
		val dollarColumn = aColumn[2]
		headTable += "<th>${columnProvider.name}</th>"
		for (date in aDates)
		{
			val dateString = date.formatWithColumn(columnDate)
			headTable += "<th>$dateString</th>"
		}
		headTable += "</tr></thead>"
		//endregion

		//region table
		var bodyTable = "<tbody>"
		for (provider in aProvider)
		{
			var sRow = "<td>$provider</td>"
			for (date in aDates)
			{
				val cell = mData["${provider}_$date"]
					?.formatDecimals(2)
					?: "0"

				val newCell = cell.formatWithColumn(dollarColumn)
				sRow += "<td>$newCell</td>"
			}
			bodyTable += "<tr>$sRow</tr>"
		}

		bodyTable += "</tbody>"

		return "<table id=\"pivotTable\">$headTable$bodyTable</table>"
		//endregion
	}
}