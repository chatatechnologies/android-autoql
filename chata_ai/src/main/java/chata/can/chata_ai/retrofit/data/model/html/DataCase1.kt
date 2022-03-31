package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.webView.Categories
import chata.can.chata_ai.pojo.webView.Category
import chata.can.chata_ai.pojo.webView.DataD3
import chata.can.chata_ai.pojo.webView.DatePivot
import chata.can.chata_ai.retrofit.data.model.query.QueryEntity

class DataCase1() {
	fun getSource(queryEntity: QueryEntity, dataD3: DataD3): DataD3 {
		val searchColumn = SearchColumn()
		queryEntity.run {
			val aGroup = searchColumn.getGroupableIndices(columnsEntity, 1)
			val aNumber = searchColumn.getNumberIndices(columnsEntity, 1)

			val posColumnX = aGroup[0]
			val posColumnY = aNumber[0]
			addIndices(posColumnX, posColumnY)
			configActions = 4

			val aIndicesIgnore = Categories.indexCategoryEmpty(rows, posColumnX)
			val aCatX = Categories.buildCategoryByPosition(
				Category(
					rows, columnsEntity[posColumnX], posColumnX,
					isFormatted = true,
					hasQuotes = true,
					allowRepeat = true,
					aIndicesIgnore = aIndicesIgnore
				)
			)
			queryBase.aXAxis = Categories.buildCategoryByPosition(
				Category(
					aRows, aColumn[posColumnX], posColumnX, false,
					hasQuotes = false, allowRepeat = true, aIndicesIgnore = aIndicesIgnore
				)
			)
			val aCatY = if (aColumn.size > posColumnY)
			{
				val maxIndex = 1
				val aBaseTri = (0 .. maxIndex).toMutableList()
				aBaseTri.remove(posColumnX)
				aBaseTri.remove(posColumnY)
				val iForTri = if (aBaseTri.isEmpty()) posColumnY else aBaseTri[0]

				val columnY = aColumn[iForTri]
				Categories.buildCategoryByPosition(
					Category(
						aRows, columnY, iForTri, true, hasQuotes = true,
						allowRepeat = true, aIndicesIgnore = aIndicesIgnore
					)
				)
			}
			else
			{
				ArrayList()
			}

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

			queryBase.aXDrillDown = Categories.buildCategoryByPosition(
				Category(
					aRows, aColumn[posColumnX], posColumnX, false,
					hasQuotes = false, allowRepeat = true, aIndicesIgnore = aIndicesIgnore
				)
			)
			dataD3.drillX = Categories.buildCategoryByPosition(
				Category(
					aRows, aColumn[posColumnX], posColumnX, false,
					hasQuotes = true, allowRepeat = true, aIndicesIgnore = aIndicesIgnore
				)
			).toString()
			dataD3.drillTableY = if (aColumn.size > posColumnY) {
				Categories.buildCategoryByPosition(
					Category(
						aRows, aColumn[posColumnY], posColumnY, true,
						hasQuotes = true, allowRepeat = false, aIndicesIgnore = aIndicesIgnore
					)
				).toString()
			} else arrayListOf<String>().toString()

			if (dataD3.aCategoryX == "[]")
			{
				dataD3.aCategoryX = Categories.makeCategories(aCatX)
			}

			val type = aColumn[0].type
			if (type == TypeDataQuery.DATE_STRING/* && type1 != TypeDataQuery.DOLLAR_AMT*/)
			{
				DatePivot.buildDateString(aRows, aColumn).run {
					if (first != "" && second != 0)
					{
						dataD3.pivot = first
						queryBase.configActions = 1
						dataD3.rowsPivot = second
					}
				}
			}
		}

		dataD3.nColumns = 2
		return dataD3
	}
}