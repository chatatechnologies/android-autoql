package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.view.extension.formatDecimals
import chata.can.chata_ai.view.extension.formatWithColumn

object DatePivot
{
	fun build(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>)
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

		//clear data for provider and dates
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
				val cell = mData["${provider}_$date"]?.let {
					it.formatDecimals(2)
				} ?: run {
					"0"
				}

				val newCell = cell.formatWithColumn(dollarColumn)
				sRow += "<td>$newCell</td>"
			}
			bodyTable += "<tr>$sRow</tr>"
		}

		bodyTable += "</tbody>"

		"<table>$headTable$bodyTable</table>"
		//endregion
	}
}