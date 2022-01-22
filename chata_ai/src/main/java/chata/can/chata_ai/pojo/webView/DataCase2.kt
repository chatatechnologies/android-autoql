package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn

object DataCase2 {
	fun getSource(queryBase: QueryBase, dataD3: DataD3): DataD3 {
		queryBase.run {
//			val aRows = aRows
//			val aColumn = aColumn
			val aDate = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE), 1)
			val aDollar = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DOLLAR_AMT), 1)
			val posColumnX = aDate[0]
			val posColumnY = aDollar[0]
			addIndices(posColumnX, posColumnY)
			configActions = 2

			val aIndicesIgnore = Categories.indexCategoryEmpty(aRows, posColumnX)
			val aCatX = Categories.buildCategoryByPosition(
				Category(
					aRows,
					aColumn[posColumnX],
					posColumnX,
					isFormatted = true,
					hasQuotes = true,
					allowRepeat = true,
					aIndicesIgnore = aIndicesIgnore
				)
			)
			val aCatY = if (aColumn.size > posColumnY)
			{
				val columnY = aColumn[posColumnY]
				Categories.buildCategoryByPosition(
					Category(
						aRows,
						columnY,
						posColumnY,
						isFormatted = true,
						hasQuotes = true,
						allowRepeat = true,
						aIndicesIgnore = aIndicesIgnore
					)
				)
			}
			else ArrayList()

			//region build data for D3
			val sb = StringBuilder()
			val sbFormat = StringBuilder()
			for (index in 0 until aCatX.count())
			{
				val name = aCatX[index]
				val value = aCatY[index]
				sb.append("{name: $name, value: $value},\n")

				val column = aColumn[1]
				val vFormat = value.formatWithColumn(column)
				sbFormat.append("{name: $name")
				if (vFormat != "0") sbFormat.append(", value: '$vFormat'")
				sbFormat.append("},\n")
			}
			dataD3.data = "[${sb.removeSuffix(",\n")}]"
			dataD3.dataFormatted = "[${sbFormat.removeSuffix(",\n")}]"
			//endregion

		}
		return dataD3
	}
}