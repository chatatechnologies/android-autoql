package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.extension.formatDecimals
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.retrofit.ColumnEntity
import chata.can.chata_ai.retrofit.core.formatValue

class PivotBuilder {
	fun buildDateString(
		aRows: MutableList<List<String>>,
		columns: List<ColumnEntity>
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
			if (aYears.size == 1)
			{
				return Pair("", 0)
			}
			//endregion
			val dollarColumn = columns[1]

			//region create table head
			val headTable = StringBuilder("<thead><tr><th>Month</th>")
			for (year in aYears)
			{
				headTable.append("<th>$year</th>")
			}
			headTable.append("</tr></thead>")
			//endregion
			var numRows = 1
			//region body table
			val bodyTable = StringBuilder("<tbody>")
			val aMonths = SinglentonDrawer.aMonths
			for ((index, month) in aMonths.withIndex())
			{
				val indexS = if ((index + 1) < 10) "0${index + 1}" else "${index + 1}"
				val sRow = StringBuilder("<td>$month</td>")
				var isNotHasZero = false
				for (year in aYears)
				{
					val cell = mData["$year-$indexS"]?.let {
						isNotHasZero = true
						it.formatDecimals(2)
					} ?: "-"

					val newCell = if (cell.isNotEmpty())
						cell.formatValue(dollarColumn)
					else cell
					sRow.append("<td>$newCell<span>${"$year-$indexS"}</span></td>")
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
}
