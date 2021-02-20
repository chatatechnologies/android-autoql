package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn
import chata.can.chata_ai.pojo.query.SupportCase
import chata.can.chata_ai.pojo.script.hasNotValueInColumn
import chata.can.chata_ai.pojo.script.setOrderRowByDate as orderRowDate

object HtmlBuilder
{
	private fun hasDateIndex(queryBase: QueryBase, posColumnX: Int): Int
	{
		var newIndex = posColumnX
		if (!queryBase.aColumn[posColumnX].type.isDate())
		{
			val iDate = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.DATE)
			if (iDate != -1)
			{
				newIndex = iDate
			}
			else
			{
				val iDateString = SearchColumn.getTypeColumn(queryBase.aColumn, TypeDataQuery.DATE_STRING)
				if (iDateString != -1)
				{
					newIndex = iDateString
				}
			}
		}
		return newIndex
	}

	fun build(queryBase: QueryBase): DataForWebView
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		val limitRow = queryBase.limitRowNum
		val dataForWebView = DataForWebView()

		orderRowDate(queryBase)

		val pData = TableHtmlBuilder.buildTable(aRows, aColumn, limitRow)
		dataForWebView.table = pData.first
		dataForWebView.rowsTable = pData.second

		var posColumnX = 0
		var posColumnY = 1
		var aDataX = ArrayList<Int>()
		var aDataY = ArrayList<Int>()
		var isTriConfig = false
		//region define data with support Case
		/**
		 * configAllow in false is bi dimensional
		 * configAllow in true is tri dimensional
		 */
		when(queryBase.supportCase)
		{
			SupportCase.CASE_1 ->
			{
				val aGroupable = SearchColumn.getGroupableIndices(queryBase.aColumn, 1)
				val aNumber = SearchColumn.getNumberIndices(queryBase.aColumn, 1)

				posColumnX = aGroupable[0]
				posColumnY = aNumber[0]
				queryBase.addIndices(posColumnX, posColumnY)
//				val hasDecimals = SearchColumn.hasDecimals(aRows, posColumnY)
//				if (hasDecimals)
//					queryBase.configActions = 0
//				else
					queryBase.configActions = 4
			}
			SupportCase.CASE_3 ->
			{
				val aGroupable = SearchColumn.getGroupableIndices(queryBase.aColumn, 2)
				posColumnX = aGroupable[0]//[1]
//				posColumnY = aGroupable[0]
//				posColumnX = hasDateIndex(queryBase, posColumnX)
				posColumnY = hasDateIndex(queryBase, posColumnX)
				queryBase.addIndices(posColumnX, posColumnY)

				isTriConfig = true
				queryBase.configActions = 5
			}
			SupportCase.CASE_5 ->
			{
				val aUncountable = SearchColumn.getUncountableIndices(queryBase.aColumn)
				val aNumber = SearchColumn.getNumberIndices(queryBase.aColumn)
				posColumnX = aUncountable.nextSeries()
				if (aNumber.isNotEmpty())
					posColumnY = aNumber[0]
				posColumnX = hasDateIndex(queryBase, posColumnX)
				queryBase.addIndices(posColumnX, posColumnY)

				dataForWebView.catX = Categories.buildCategoryByPosition(
					Category(
						aRows, aColumn[posColumnX], posColumnX,
						true, hasQuotes = true, allowRepeat = false
					)
				).toString()
				dataForWebView.dataChartBi = Series.getDataSeries(aRows, aColumn, posColumnX, aNumber)
				val pMM = SearchColumn.getMinMaxColumns(aRows, aNumber)
				dataForWebView.max = pMM.first
				dataForWebView.min = pMM.second
//				val hasDecimals = SearchColumn.hasDecimals(aRows, posColumnY)
//				if (hasDecimals)
//					queryBase.configActions = 0
//				else
					queryBase.configActions = 4
			}
			SupportCase.CASE_6 ->
			{
				val aString = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.STRING), 2)
				val aNumber = SearchColumn.getNumberIndices(queryBase.aColumn, 1)
				/*aDate*/
				aDataX = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.DATE))//Text
				/*aDollar*/
				aDataY = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.DOLLAR_AMT))//Numeric

				when
				{
					aDataX.isNotEmpty() -> posColumnX = aDataX[0]
					aString.isNotEmpty() ->
					{
						posColumnX = if (aString.size == 2)
							aString[1]
						else
							aString[0]
					}
				}
				when
				{
					aDataY.isNotEmpty() ->
					{
						val tmp = hasNotValueInColumn(aRows, aDataY, 0f)
						posColumnY = if (tmp == -1) aDataY[0] else tmp
					}
					aNumber.isNotEmpty() ->
					{
						posColumnY = aDataY[0]
					}
				}

				//posColumnX = hasDateIndex(queryBase, posColumnX)
				queryBase.addIndices(posColumnX, posColumnY)
				queryBase.configActions = 4
			}
			else ->
			{
				queryBase.supportCase.toString()
			}
		}
		//endregion

		//TODO CHECK SUPPORT CASES
		Categories.run {
			val aCatX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					true, hasQuotes = true, allowRepeat = !isTriConfig))
			//region xAxis
			queryBase.aXAxis = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					false, hasQuotes = false, allowRepeat = !isTriConfig))
			//endregion
			var aCatYNotFormat: ArrayList<String> ?= null
			val aCatY = if (aColumn.size > posColumnY)
			{
				val column = aColumn[posColumnY]
				if (column.type == TypeDataQuery.DATE || column.type == TypeDataQuery.DATE_STRING)
				{
					aCatYNotFormat = buildCategoryByPosition(
						Category(
							aRows, column, posColumnY, false, hasQuotes = true, allowRepeat = !isTriConfig))
				}
				buildCategoryByPosition(
					Category(
						aRows, column, posColumnY, true, hasQuotes = true, allowRepeat = !isTriConfig))
			}
			else
			{
				ArrayList()
			}
			val aCatYS = if (aColumn.size > posColumnY)
			{
				//calculate max and min for bi dimensional
				val tmp = buildCategoryByPosition(
					Category(aRows, aColumn[posColumnY], posColumnY,
						true, hasQuotes = true, allowRepeat = !isTriConfig))
				val aInt = tmp.toListInt()
				if (dataForWebView.max == -1 && dataForWebView.min == -1)
				{
					val tmpMin = (aInt.minOrNull() ?: 0)
					dataForWebView.min = if (tmpMin < 0) tmpMin else 0
					dataForWebView.max = (aInt.maxOrNull() ?: 0)
				}
				tmp
			}
			else ArrayList()

			queryBase.aXDrillDown = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					false, hasQuotes = false, allowRepeat = true))

			dataForWebView.drillX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					false, hasQuotes = true, allowRepeat = true)).toString()
			dataForWebView.drillY = if (aColumn.size > posColumnY) {
				val column = aColumn[posColumnY]
				buildCategoryByPosition(
					Category(
						aRows,
						aColumn[posColumnY],
						posColumnY,
						column.type != TypeDataQuery.DATE && column.type != TypeDataQuery.DATE_STRING,
						hasQuotes = true,
						allowRepeat = !isTriConfig)).toString()
			} else arrayListOf<String>().toString()

			dataForWebView.drillTableY = if (aColumn.size > posColumnY) {
				buildCategoryByPosition(Category(aRows, aColumn[posColumnY],
					posColumnY, true, hasQuotes = true, allowRepeat = isTriConfig)).toString()
			} else arrayListOf<String>().toString()

			if (dataForWebView.catX == "[]") dataForWebView.catX = aCatX.toString()
			dataForWebView.catY = aCatY.toString()

			if (isTriConfig)
			{
				val aNumber = SearchColumn.getNumberIndices(aColumn, 1)
				val aString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.STRING), 1, 1)
				val aDate = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE), 1)
				val aDateString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE_STRING), 1)

				//val mXAxis = queryBase.aXAxis.map { "\"$it\"" }
				val aCatYTmp = aCatYNotFormat ?: aCatY
				val mTri = if (posColumnX == 0) Pair(aCatYTmp, aCatX) else Pair(aCatYTmp, aCatY)

				//get aDataTable and aMapPure
				val pair = TableTriBuilder.generateDataTableTri(
					aRows,
					aColumn[posColumnY],
					mTri.first,
					mTri.second,
					aNumber.isNotEmpty())
				val aDataTable = pair.first
				val aMapPure = pair.second
				dataForWebView.dataChartBi = aDataTable.toString()

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
//						val aCheck = (0..2).toMutableList()
							val tmpDate = when
							{
								aDate.isNotEmpty() -> aDate
								aDateString.isNotEmpty() -> aDateString
								aString.isNotEmpty() -> aString
								else -> aDate
							}
//						aCheck.remove(tmpDate[0])
//						aCheck.remove(aNumber[0])
							val nameHeader = aColumn[tmpDate[0]].displayName

							val tPivot = TableTriBuilder.buildDataPivot(
								mDataPivot,
								aColumn[aNumber.first()],
								aCatX,//newListDescending(aCatX),
								aCatY,
								nameHeader)
							dataForWebView.datePivot = tPivot.first
							dataForWebView.rowsPivot = tPivot.second
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
										if (first != next)
										{
											dataForWebView.stackedFrom = first
											dataForWebView.stackedTo = next
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
							dataForWebView.datePivot = pPivot.first
							dataForWebView.rowsPivot = pPivot.second
						}
					}
					else
					{
						dataForWebView.datePivot = DatePivot.buildTri(aRows, aColumn)
						dataForWebView.rowsPivot = 180
					}
				}

				dataForWebView.catYS = LineBuilder.generateDataChartLine(aMapPure, aCatX, aCatY).toString()
				queryBase.isTri = true
				dataForWebView.isBi = false

				val isDate = aColumn[0].type == TypeDataQuery.STRING
				val isDateString = aColumn[1].type == TypeDataQuery.DATE_STRING
				val isDollar = aColumn[2].type == TypeDataQuery.DOLLAR_AMT
				if (isDate && isDateString && isDollar)
				{
					//TODO COMPLETE
//						dataForWebView.dataChartBiWithTri = Table.generateDataTable(
//							aRows, aColumn,queryBase.mIndexColumn,true)
					queryBase.isTriInBi = true
//						queryBase.configActions = 6
				}

//				if (queryBase.configActions == 2)
//				{
//					val aDataXAxis = ArrayList<String>()
//					val aDataYAxis = ArrayList<String>()
//
//					for (row in aRows)
//					{
//						if (row.size == 3)
//							aDataXAxis.add(row[1])
//						aDataYAxis.add(row[2])
//					}
//
//					val title1 = aColumn[0].name
//					val title2 = aColumn[1].name
//					dataForWebView.catYS = "[{name:\"${title1.toCapitalColumn()}\", data:$aDataXAxis}," +
//						"{name:\"${title2.toCapitalColumn()}\", data:$aDataYAxis}]"
//					queryBase.isContrast = true
//					queryBase.isTri = true
//					dataForWebView.isBi = false
//				}
			}
			else
			{
				if (aDataX.isNotEmpty() || aDataY.isNotEmpty())
				{
					val aCategoriesX = ArrayList<String>()//Remember that data is not formatted
					val indexX = aDataX[0]
					val mData = ArrayList< LinkedHashMap<String, Double>>()
					for (iItem in aDataY)
					{
						val mRow = LinkedHashMap<String, Double>()

						for (row in aRows)
						{
							val key = row[indexX]
							if (key !in aCategoriesX) aCategoriesX.add(key)
							val value = row[iItem].toDoubleNotNull()
							mRow[key]?.run {
								mRow[key] = this + value
							} ?: run {
								mRow[key] = value
							}
						}
						mData.add(mRow)
					}
					//aCategoriesX.map { it.formatWithColumn(aColumn[posColumnX]) }
					aCategoriesX.toString()
					mData.toString()
				}
				//TODO COMPLETE
//				pData = if (queryBase.isTypeColumn(TypeDataQuery.DATE_STRING))
//					DatePivot.buildDateString(aRows, aColumn)
//				else DatePivot.buildBi(aRows, aColumn)
//				queryBase.configActions = 1

				val type = aColumn[0].type
//				val type1 = aColumn[1].type
				if (type == TypeDataQuery.DATE_STRING
					//&& type1 != TypeDataQuery.DOLLAR_AMT
				)
				{
					DatePivot.buildDateString(aRows, aColumn).run {
						dataForWebView.datePivot = first
						dataForWebView.rowsPivot = second
					}
					queryBase.configActions = 1
				}

				dataForWebView.catYS = aCatYS.toString()
				if (dataForWebView.dataChartBi == "[]")
				{
					dataForWebView.dataChartBi = Table.generateDataTable(
						aRows, aColumn, queryBase.aIndex,true)
				}
			}
		}

		return dataForWebView
	}
}