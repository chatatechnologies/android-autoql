package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.pojo.webView.DataD3
import chata.can.chata_ai.retrofit.core.formatValue
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn
import chata.can.chata_ai.retrofit.data.model.query.QueryEntity

class DataCase1() {
	fun getSource(queryEntity: QueryEntity, dataD3: DataD3): DataD3 {
		val searchColumn = SearchColumn()
		val categories = Categories()
		val pivotBuilder = PivotBuilder()

		queryEntity.run {
			val aGroup = searchColumn.getGroupableIndices(columnsEntity, 1)
			val aNumber = searchColumn.getNumberIndices(columnsEntity, 1)

			val posColumnX = aGroup[0]
			val posColumnY = aNumber[0]
			addIndices(posColumnX, posColumnY)
			configActions = 4

			val indicesIgnore = categories.indexCategoryEmpty(rows, posColumnX)
			val aCatX = categories.buildCategoryByPosition(
				Category(
					rows = rows,
					column = columnsEntity[posColumnX],
					position = posColumnX,
					isFormatted = true,
					hasQuotes = true,
					allowRepeat = true,
					indicesIgnore = indicesIgnore
				)
			)
			queryEntity.aXAxis = categories.buildCategoryByPosition(
				Category(
					rows = rows,
					column = columnsEntity[posColumnX],
					position = posColumnX,
					isFormatted = false,
					hasQuotes = false,
					allowRepeat = true,
					indicesIgnore = indicesIgnore
				)
			)
			val aCatY = if (columnsEntity.size > posColumnY) {
				val maxIndex = 1
				val aBaseTri = (0 .. maxIndex).toMutableList()
				aBaseTri.remove(posColumnX)
				aBaseTri.remove(posColumnY)
				val iForTri = if (aBaseTri.isEmpty()) posColumnY else aBaseTri[0]

				val columnY = columnsEntity[iForTri]
				categories.buildCategoryByPosition(
					Category(
						rows = rows,
						column = columnY, position = iForTri, isFormatted = true, hasQuotes = true,
						allowRepeat = true, indicesIgnore = indicesIgnore
					)
				)
			}
			else {
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

				val column = columnsEntity[1]
				val vFormat = value.formatValue(column)
				sbFormat.append("{name: $name")
				if (vFormat != "0") sbFormat.append(", value: '$vFormat'")
				sbFormat.append("},\n")
			}
			dataD3.data = "[${sb.removeSuffix(",\n")}]"
			dataD3.dataFormatted = "[${sbFormat.removeSuffix(",\n")}]"
			//endregion

			queryEntity.aXDrillDown = categories.buildCategoryByPosition(
				Category(
					rows = rows,
					column = columnsEntity[posColumnX],
					position = posColumnX,
					isFormatted = false,
					hasQuotes = false,
					allowRepeat = true,
					indicesIgnore = indicesIgnore
				)
			)
			dataD3.drillX = categories.buildCategoryByPosition(
				Category(
					rows = rows,
					column = columnsEntity[posColumnX],
					position = posColumnX,
					isFormatted = false,
					hasQuotes = true,
					allowRepeat = true,
					indicesIgnore = indicesIgnore
				)
			).toString()
			dataD3.drillTableY = if (columnsEntity.size > posColumnY) {
				categories.buildCategoryByPosition(
					Category(
						rows = rows,
						column = columnsEntity[posColumnY],
						position = posColumnY,
						isFormatted = true,
						hasQuotes = true,
						allowRepeat = false,
						indicesIgnore = indicesIgnore
					)
				).toString()
			} else arrayListOf<String>().toString()

			if (dataD3.aCategoryX == "[]") {
				dataD3.aCategoryX = categories.makeCategories(aCatX)
			}

			val type = columnsEntity[0].typeColumn
			if (type == TypeColumn.DATE_STRING)
			{
				pivotBuilder.buildDateString(rows, columnsEntity).run {
					if (first != "" && second != 0) {
						dataD3.pivot = first
						queryEntity.configActions = 1
						dataD3.rowsPivot = second
					}
				}
			}
		}

		dataD3.nColumns = 2
		return dataD3
	}
}