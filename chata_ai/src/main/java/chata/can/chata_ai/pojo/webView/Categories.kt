package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object Categories
{
	fun buildCategoryByPosition(category: Category): ArrayList<String>
	{
		val aStacked = ArrayList<String>()

		with(category)
		{
			for (index in aRows.indices)
			{
				val aRow = aRows[index]//.replaceCommaSharp()
				//val aRow = ArrayList(text.split(","))

				val cellUsed = aRow[position]?: ""

				var parsed = if (isFormatted)
				{
					cellUsed.formatWithColumn(
						column,
						currencySymbol = "",
					commaCharacter = "")
				}
				else cellUsed

				parsed = when
				{
					hasQuotes && (column.type == TypeDataQuery.STRING || column.type == TypeDataQuery.DATE) ->
					{
						"\"$parsed\""
					}
					else ->
					{
						parsed
					}
				}

				if (allowRepeat)
					aStacked.add(parsed)
				else
				{
					if (parsed !in aStacked)
						aStacked.add(parsed)
				}
			}
		}

		return aStacked
	}
}