package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.dialog.manageData.FilterColumn
import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn

object DataCase3 {
	fun getSource(queryBase: QueryBase, dataD3: DataD3): DataD3 {
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		var posColumnX = 0
		var posColumnY = 1
		val aGroupable = SearchColumn.getGroupableIndices(queryBase.aColumn, 2)
		if (aGroupable.isNotEmpty())
		{
			val pos = if (aGroupable.size == 2) 0 else 1
			posColumnX = aGroupable[pos]
		}
		val tmp = HtmlBuilder.hasCountableIndex(queryBase)
		if (tmp != -1) posColumnY = tmp
		queryBase.addIndices(posColumnX, posColumnY)
		val isTriConfig = true
		dataD3.isBi = false
		queryBase.configActions = 5

		val aIndicesIgnore = if (!isTriConfig) Categories.indexCategoryEmpty(aRows, posColumnX) else ArrayList()
		val aCatX = Categories.buildCategoryByPosition(
			Category(
				aRows, aColumn[posColumnX], posColumnX, true,
				hasQuotes = true, allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore
			)
		)
		//region xAxis
		val posTriConfig = if (isTriConfig) posColumnY else posColumnX
		queryBase.aXAxis = Categories.buildCategoryByPosition(
			Category(
				aRows, aColumn[posTriConfig], posTriConfig, false,
				hasQuotes = false, allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore
			)
		)
		//endregion
		val aCatY = if (aColumn.size > posColumnY)
		{
			val maxIndex = if (isTriConfig) 2 else 1
			val aBaseTri = (0 .. maxIndex).toMutableList()
			aBaseTri.remove(posColumnX)
			aBaseTri.remove(posColumnY)
			val iForTri = if (aBaseTri.isEmpty()) posColumnY else aBaseTri[0]

			val columnY = aColumn[iForTri]
			Categories.buildCategoryByPosition(
				Category(
					aRows, columnY, iForTri, true, hasQuotes = true,
					allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore
				)
			)
		}
		else
		{
			ArrayList()
		}

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

		if (dataD3.aCategoryX == "[]")
		{
			dataD3.aCategoryX = Categories.makeCategories(aCatX, !isTriConfig)
		}
		//region isTriConfig
		for (cat in queryBase.aCategoryX)
		{
			queryBase.aCategory.add(FilterColumn(cat, true))
		}
		//region cat for D3 on tri dimensions
		dataD3.catHeatX = Categories.makeCategories(aCatY, !isTriConfig)
		dataD3.catHeatY = dataD3.aCategoryX
		//endregion

		val aNumber = SearchColumn.getNumberIndices(aColumn, 1)
		val aString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.STRING), 1, 1)
		val aDate = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE), 1)
		val aDateString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE_STRING), 1)

		val pCat = if (posColumnX == 1) Triple(aCatY, aCatX, true)
		else Triple(aCatX, aCatY, false)

		val dataTableTri = TableTriBuilder.DataTableTri(aRows,
			aColumn[posColumnY],
			pCat.first,
			pCat.second,
			aNumber.isNotEmpty(),
			pCat.third)

		val pair = TableTriBuilder.generateDataTableTri(dataTableTri)
		dataD3.data = TableTriBuilder.getData3Dimensions(pair.second, /*pair.first,*/ aCatX, aCatY)
		val aDataTable = pair.first
		val aMapPure = pair.second

		if ((aString.isNotEmpty() || aDate.isNotEmpty() || aDateString.isNotEmpty()) && aNumber.isNotEmpty())
		{
			val type = aColumn[aNumber[0]]
			val mDataPivot = TableTriBuilder.getMapDataTable(aDataTable)

			if (type.type == TypeDataQuery.QUANTITY || type.type == TypeDataQuery.DOLLAR_AMT)
			{
				val mRepeat = HashMap<String, Int>()
				for (row in aRows)
				{
					val row1 = row[0]
					mRepeat[row1]?.let {
						mRepeat[row1] = it + 1
					} ?: run {
						mRepeat[row1] = 1
					}
				}

				val hasRepeat = mRepeat.filter { it.value > 1 }.isNotEmpty()
				if (hasRepeat)
				{
					val tmpDate = when
					{
						aDate.isNotEmpty() -> aDate
						aDateString.isNotEmpty() -> aDateString
						aString.isNotEmpty() -> aString
						else -> aDate
					}
//					val nameHeader = aColumn[tmpDate[0]].displayName
					val nameHeader = aColumn[0].displayName

					val tPivot = TableTriBuilder.buildDataPivot(
						mDataPivot,
						aColumn[aNumber.first()],
						aCatX,//newListDescending(aCatX),
						aCatY,
						nameHeader)
					dataD3.pivot = tPivot.first
					dataD3.rowsPivot = tPivot.second
					tPivot.run {
						if (third.isNotEmpty())
						{
							val first = third[0]
							var next = first
							if (third.size > 1)
							{
								val second = third[1]
								while (next == second -1)
								{
									next = second
								}
							}
						}
					}
				}
				else
				{
					val nameHeader = aColumn[0].displayName
					val pPivot = TableTriBuilder.lineDataPivot(
						mDataPivot, aColumn[aNumber.first()],aCatY, nameHeader)
					dataD3.pivot = pPivot.first
					dataD3.rowsPivot = pPivot.second
				}
			}
			else
			{
				dataD3.pivot = DatePivot.buildTri(aRows, aColumn)
				dataD3.rowsPivot = 180
			}
		}

		val pChartLine = LineBuilder.generateDataChartLine(aMapPure, aCatX, aCatY)
		val pStacked1 = pChartLine.first
		if (pStacked1.first is String)
		{
			dataD3.dataStacked = pStacked1.first as? String ?: ""
		}
		if (pStacked1.second is Int)
		{
			dataD3.max = pStacked1.second as? Int ?: 0
		}
		val pStacked2 = pChartLine.second
		if (pStacked2.first is String)
		{
			dataD3.dataStacked2 = pStacked2.first as? String ?: ""
		}
		if (pStacked2.second is Int)
		{
			dataD3.max2 = pStacked2.second as? Int ?: 0
		}

		queryBase.isTri = true

		val isDate = aColumn[0].type == TypeDataQuery.STRING
		val isDateString = aColumn[1].type == TypeDataQuery.DATE_STRING
		val isDollar = aColumn[2].type == TypeDataQuery.DOLLAR_AMT
		if (isDate && isDateString && isDollar)
		{
			queryBase.isTriInBi = true
		}

		//region index dollar
		var indexDollar = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.DOLLAR_AMT)
		if (indexDollar == -1)
			indexDollar = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.QUANTITY)
		//endregion
		//region index date
		var indexDate = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.DATE_STRING)
		if (indexDate == -1)
			indexDate = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.DATE)
		if (indexDate == -1)
			indexDate = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.STRING)
		//endregion
		val aIndices = arrayListOf(0, 1 ,2)
		aIndices.remove(indexDate)
		aIndices.remove(indexDollar)

		val secondIndex = aIndices[0]
		val aFirstKey = ArrayList<String>()
		val aSecondKey = ArrayList<String>()
		val mData = LinkedHashMap<String, Double>()

		if (indexDate == -1 || indexDollar == -1)
		{
			println()
		}

		for (row in aRows)
		{
			val firstKey = row[indexDate]
			val secondKey = row[secondIndex]
			if (firstKey !in aFirstKey) aFirstKey.add(firstKey)
			if (secondKey !in aSecondKey) aSecondKey.add(secondKey)

			val key = "${row[indexDate]}__${secondKey}"//main key and second key
			val value = row[indexDollar].toDoubleNotNull()
			mData[key] = value
		}

		val aData = ArrayList< ArrayList <Double> >()
		for (second in aSecondKey)
		{
			val aValues = ArrayList<Double>()
			for (first in aFirstKey)
			{
				mData["${first}__${second}"]?.let {
					aValues.add(it)
				} ?: run {
					aValues.add(0.0)
				}
			}
			aData.add(aValues)
		}
		if (aData.isNotEmpty())
		{
			queryBase.configActions = 6
		}

		return dataD3
	}
}