package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase

object DataCaseNone {
	fun getSource(queryBase: QueryBase, dataD3: DataD3): DataD3 {
		return dataD3.apply {
			val aRows = queryBase.aRows
			val aColumn = queryBase.aColumn
			val posColumnX = 0

			val aIndicesIgnore = Categories.indexCategoryEmpty(aRows, posColumnX)
			val aCatX = Categories.buildCategoryByPosition(
				Category(
					aRows, aColumn[posColumnX], posColumnX, true,
					hasQuotes = true, allowRepeat = true, aIndicesIgnore = aIndicesIgnore
				)
			)
			if (aCategoryX == "[]")
			{
				aCategoryX = Categories.makeCategories(aCatX)
			}
			drillX = Categories.buildCategoryByPosition(
				Category(
					aRows, aColumn[posColumnX], posColumnX, false,
					hasQuotes = true, allowRepeat = true, aIndicesIgnore = aIndicesIgnore
				)
			).toString()
		}
	}
}