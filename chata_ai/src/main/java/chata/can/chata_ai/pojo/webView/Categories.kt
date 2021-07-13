package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.isNull
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object Categories
{
	fun indexCategoryEmpty(
		aRows: ArrayList<ArrayList<String>>,
		position: Int): ArrayList<Int>
	{
		val aIndices = ArrayList<Int>()
		for (index in aRows.indices)
		{
			val aRow = aRows[index]
			val cellUsed = aRow[position]
			if (cellUsed.isNull() || cellUsed.isEmpty())
				aIndices.add(index)
		}
		return aIndices
	}

	fun buildCategoryByPosition(category: Category): ArrayList<String>
	{
		val aStacked = ArrayList<String>()
		with(category)
		{
			for (index in aRows.indices)
			{
				if (index in aIndicesIgnore) continue
				val aRow = aRows[index]
				val cellUsed = aRow[position]
				if (cellUsed.isEmpty()) continue
				var parsed = if (isFormatted)
				{
					cellUsed.formatWithColumn(
						column,
						currencySymbol = "",
					commaCharacter = "")
				}
				else cellUsed

				parsed = if (hasQuotes)
				{
					when(column.type)
					{
						TypeDataQuery.STRING, TypeDataQuery.DATE, TypeDataQuery.DATE_STRING -> "\"$parsed\""
						else -> parsed
					}
				}
				else parsed

				if (allowRepeat)
					aStacked.add(parsed)
				else
				{
					if (parsed !in aStacked)
						aStacked.add(parsed)
				}
			}
			if (column.type == TypeDataQuery.DATE || column.type == TypeDataQuery.DATE_STRING)
			{
				aStacked.reverse()
			}
		}
		return aStacked
	}

	fun makeCategories(aCat: ArrayList<String>, isBi: Boolean): String
	{
		val sb = StringBuilder("[")
		for (category in aCat)
		{
			val tmpCat = when (category)
			{
				"\"null\"" -> if (isBi) "" else "\"Untiled Category\""
				else -> category
			}
			if (tmpCat.isNotEmpty())
				sb += "$tmpCat, "
		}
		return "${sb.removeSuffix(", ")}]"
	}

	operator fun StringBuilder.plusAssign(newContent: String)
	{
		append(newContent)
	}
}