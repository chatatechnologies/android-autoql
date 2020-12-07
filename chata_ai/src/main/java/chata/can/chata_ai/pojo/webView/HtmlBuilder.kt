package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn
import chata.can.chata_ai.pojo.query.SupportCase
import kotlin.collections.ArrayList

object HtmlBuilder
{
	fun build(queryBase: QueryBase): DataForWebView
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		val dataForWebView = DataForWebView()

		val pData = TableHtmlBuilder.buildTable(aRows, aColumn)
		dataForWebView.table = pData.first
		dataForWebView.rowsTable = pData.second

		var posColumnX = 0
		var posColumnY = 1
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
				posColumnX = aGroupable[1]//0
				posColumnY = aGroupable[0]//1
				queryBase.addIndices(posColumnX, posColumnY)

				isTriConfig = true
				queryBase.configActions = 5
			}
			SupportCase.CASE_5 ->
			{
				//get second string column
				val aString = SearchColumn.getTypeIndices(queryBase.aColumn, TypeDataQuery.STRING, 2)
				val aNumber = SearchColumn.getNumberIndices(queryBase.aColumn, 1)
				if (aString.isNotEmpty())
				{
					posColumnX = if (aString.size == 2)
						aString[1]
					else
						aString[0]
				}
				if (aNumber.isNotEmpty())
					posColumnY = aNumber[0]

				queryBase.addIndices(posColumnX, posColumnY)

				val hasDecimals = SearchColumn.hasDecimals(aRows, posColumnY)
				if (hasDecimals)
					queryBase.configActions = 0
				else
					queryBase.configActions = 4
			}
			SupportCase.CASE_6 ->
			{

			}
			else ->
			{
				queryBase.supportCase.toString()
			}
		}
		//endregion

		//TODO CHECK SUPPORT CASES
		with(Categories)
		{
			val aCatX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					true, hasQuotes = true, allowRepeat = !isTriConfig))
			//region xAxis
			queryBase.aXAxis = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					false, hasQuotes = false, allowRepeat = !isTriConfig))
			//endregion
			val aCatY = if (aColumn.size > posColumnY)
			{
				buildCategoryByPosition(
					Category(aRows, aColumn[posColumnY], posColumnY,
						true, hasQuotes = true, allowRepeat = !isTriConfig))
			}
			else
			{
				ArrayList()
			}
			val aCatYS = if (aColumn.size > posColumnY)
			{
				buildCategoryByPosition(
					Category(aRows, aColumn[posColumnY], posColumnY,
						true, hasQuotes = true, allowRepeat = !isTriConfig))
			}
			else ArrayList()

			queryBase.aXDrillDown = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					false, hasQuotes = false, allowRepeat = true))

			dataForWebView.drillX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					false, hasQuotes = true, allowRepeat = true)).toString()
			dataForWebView.drillY = if (aColumn.size > posColumnY) {
				buildCategoryByPosition(
					Category(aRows, aColumn[posColumnY], posColumnY,
						true, hasQuotes = true, allowRepeat = !isTriConfig)).toString()
			} else arrayListOf<String>().toString()

			dataForWebView.drillTableY = if (aColumn.size > posColumnY) {
				buildCategoryByPosition(Category(aRows, aColumn[posColumnY],
					posColumnY, true, hasQuotes = true, allowRepeat = isTriConfig)).toString()
			} else arrayListOf<String>().toString()

			dataForWebView.catX = aCatX.toString()
			dataForWebView.catY = aCatY.toString()

			if (isTriConfig)
			{
				val aNumber = SearchColumn.getNumberIndices(aColumn, 1)
				val aString = SearchColumn.getTypeIndices(aColumn, TypeDataQuery.STRING, 1, 1)
				val aDate = SearchColumn.getTypeIndices(aColumn, TypeDataQuery.DATE, 1)
				val aDateString = SearchColumn.getTypeIndices(aColumn, TypeDataQuery.DATE_STRING, 1)

				//get aDataTable and aMapPure
				val pair = TableTriBuilder.generateDataTableTri(
					aRows,
					aColumn[posColumnY],
					queryBase.aXAxis.map { "\"$it\"" },
					aCatY,
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

							val pPivot = TableTriBuilder.buildDataPivot(
								mDataPivot,
								aColumn[aNumber.first()],
								aCatX,//newListDescending(aCatX),
								aCatY,
								nameHeader)
							dataForWebView.datePivot = pPivot.first
							dataForWebView.rowsPivot = pPivot.second
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
				//TODO COMPLETE
//				pData = if (queryBase.isTypeColumn(TypeDataQuery.DATE_STRING))
//					DatePivot.buildDateString(aRows, aColumn)
//				else DatePivot.buildBi(aRows, aColumn)
//				queryBase.configActions = 1

				val type = aColumn[0].type
				val type1 = aColumn[1].type
				if (type == TypeDataQuery.DATE_STRING && type1 != TypeDataQuery.DOLLAR_AMT)
				{
					DatePivot.buildDateString(aRows, aColumn).run {
						dataForWebView.datePivot = first
						dataForWebView.rowsPivot = second
					}
					queryBase.configActions = 1
				}

				dataForWebView.catYS = aCatYS.toString()
				dataForWebView.dataChartBi = Table.generateDataTable(
					aRows, aColumn, queryBase.aIndex,true)

//				queryBase.configActions = when(aColumn.size)
//				{
//					2 -> 4
//					3 -> 5
//					else -> 0
//				}
			}
		}

		return dataForWebView
	}
}