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
	private val aCount = arrayListOf(TypeDataQuery.QUANTITY, TypeDataQuery.DATE, TypeDataQuery.DATE_STRING)
	/*search countable type one by one*/
	private fun hasCountableIndex(queryBase: QueryBase): Int
	{
		for (type in aCount)
		{
			val tmp = SearchColumn.getTypeColumn(queryBase.aColumn, type)
			if (tmp != -1)
			{
				return tmp
			}
		}
		return -1
	}

	fun build(queryBase: QueryBase): Pair<DataForWebView, DataD3>
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		val limitRow = queryBase.limitRowNum
		//region container for webView
		val dataForWebView = DataForWebView()
		val dataD3 = DataD3()

		orderRowDate(queryBase)

		val pData = TableHtmlBuilder.buildTable(aRows, aColumn, limitRow)
		dataForWebView.table = pData.first
		dataD3.table = pData.first
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
				val tmp = hasCountableIndex(queryBase)
				if (tmp != -1) posColumnY = tmp
				queryBase.addIndices(posColumnX, posColumnY)
				isTriConfig = true
				queryBase.configActions = 5
			}
			SupportCase.CASE_5 ->
			{
				val aUncountable = SearchColumn.getUncountableIndices(queryBase.aColumn)
				val aNumber = SearchColumn.getNumberIndices(queryBase.aColumn)
				/*aDate*/
				aDataX = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.DATE))//Text
				/*aDollar*/
				aDataY = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.DOLLAR_AMT))//Numeric

				when
				{
					aDataX.isNotEmpty() -> posColumnX = aDataX[0]
					aUncountable.isNotEmpty() ->
					{
						posColumnX = if (aUncountable.size == 2)
							aUncountable[1]
						else
							aUncountable[0]
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
						posColumnY = if (aDataY.isNotEmpty()) aDataY[0] else aNumber[0]
					}
				}

				queryBase.addIndices(posColumnX, posColumnY)
				queryBase.configActions = 4

//				posColumnX = aUncountable.nextSeries()
//				if (aNumber.isNotEmpty())
//					posColumnY = aNumber[0]
//				posColumnX = hasDateIndex(queryBase, posColumnX)
//				queryBase.addIndices(posColumnX, posColumnY)
				dataForWebView.isReverseX = true
				dataForWebView.dataChartBi = Series.getDataSeries(aRows, aColumn, posColumnX, aNumber)
				val pMM = SearchColumn.getMinMaxColumns(aRows, aNumber)
				dataForWebView.max = pMM.first
				dataD3.max = pMM.first
				dataForWebView.min = pMM.second
				dataD3.min = pMM.second
//				val hasDecimals = SearchColumn.hasDecimals(aRows, posColumnY)
//				if (hasDecimals)
//					queryBase.configActions = 0
//				else
//					queryBase.configActions = 4
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
		if (queryBase.aRows.size == 1)
			queryBase.configActions = 0
		//endregion

		//TODO CHECK SUPPORT CASES
		Categories.run {
			val aCatX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX,
					true, hasQuotes = true, allowRepeat = !isTriConfig))
			//region xAxis
			val posTriConfig = if (isTriConfig) posColumnY else posColumnX
			queryBase.aXAxis = buildCategoryByPosition(
				Category(aRows, aColumn[posTriConfig], posTriConfig,
					false, hasQuotes = false, allowRepeat = !isTriConfig))
			//endregion
			var aCatYNotFormat: ArrayList<String> ?= null
			val aCatY = if (aColumn.size > posColumnY)
			{
				val maxIndex = if (isTriConfig) 2 else 1
				val aBaseTri = (0 .. maxIndex).toMutableList()
				aBaseTri.remove(posColumnX)
				aBaseTri.remove(posColumnY)
				val iForTri = if (aBaseTri.isEmpty()) posColumnY else aBaseTri[0]

				val column = aColumn[posColumnY]
				if (column.type.isDate() || column.type == TypeDataQuery.QUANTITY)
				{
					aCatYNotFormat = buildCategoryByPosition(
						Category(
							aRows, column, posColumnY, false, hasQuotes = true, allowRepeat = !isTriConfig))
				}
				val columnY = aColumn[iForTri]
				buildCategoryByPosition(
					Category(
						aRows, columnY, iForTri, true, hasQuotes = true, allowRepeat = !isTriConfig))
			}
			else
			{
				ArrayList()
			}

			//region build data for D3
//			val sb = StringBuilder()
//			for (index in 0 until aCatX.count())
//			{
//				val name = aCatX[index]
//				val value = aCatY[index]
//				sb.append("{name: $name, value: $value},\n")
//			}
//			dataD3.data = "[${sb.removeSuffix(",\n")}]"
			//endregion

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
					dataD3.min = if (tmpMin < 0) tmpMin else 0
					dataForWebView.max = (aInt.maxOrNull() ?: 0)
					dataD3.max = (aInt.maxOrNull() ?: 0)
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

			if (dataForWebView.catX == "[]") dataForWebView.catX = makeCategories(aCatX, !isTriConfig)
			dataForWebView.catY = makeCategories(aCatY, !isTriConfig)

			if (isTriConfig)
			{
				val aNumber = SearchColumn.getNumberIndices(aColumn, 1)
				val aString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.STRING), 1, 1)
				val aDate = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE), 1)
				val aDateString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE_STRING), 1)

				//val aCatYTmp = aCatYNotFormat ?: aCatY
				//focus her
				val tmpCat = if (posColumnX == 0) aCatX else aCatY

				//get aDataTable and aMapPure
				val pair = TableTriBuilder.generateDataTableTri(
					aRows,
					aColumn[posColumnY],//countable column
					aCatY,
					tmpCat,
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
				//var maxLength = 1
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
					dataForWebView.dataChartBiOnTri = aData.joinToString(",\n", "[", "]") {
						it.joinToString(prefix = "{data: [", postfix = "]}")
					}
				}
			}
			else
			{
				if (aDataX.isNotEmpty() || aDataY.isNotEmpty())
				{
					val aCategoriesX = ArrayList<String>()//Remember that data is not formatted
					val indexX = aDataX[0]
					val aData = ArrayList< LinkedHashMap<String, Double>>()
					val aGroupedData = ArrayList<LinkedHashMap<String, ArrayList< ArrayList<String>/*might transform to array list*/>>>()
					for (iItem in aDataY)
					{
						val mRow = LinkedHashMap<String, Double>()
						val mGroupedRow = LinkedHashMap<String, ArrayList< ArrayList<String>>>()
						for (row in aRows)
						{
							val key = row[indexX]
							if (key !in aCategoriesX) aCategoriesX.add(key)
							val value = row[iItem].toDoubleNotNull()
							mRow[key]?.run {
								mGroupedRow[key]?.add(row)
								mRow[key] = this + value
							} ?: run {
								mGroupedRow[key] = arrayListOf(row)
								mRow[key] = value
							}
						}
						aGroupedData.add(mGroupedRow)
						aData.add(mRow)
					}
					//Map for data
					val mDataOrder = LinkedHashMap<String, ArrayList<String>>()
					var max = 0
					var min = 0
					for (mChild in aData)
					{
						val tmpMax = (mChild.maxByOrNull { it.value })?.value?.toInt() ?: 0
						if (tmpMax > max) max = tmpMax
						val tmpMin = (mChild.minByOrNull { it.value })?.value?.toInt() ?: 0
						if (tmpMin < min) min = tmpMin
						for ((key, value) in mChild)
						{
							val sValue = value.toString()
							mDataOrder[key]?.run {
								this.add(sValue)
							} ?: run {
								mDataOrder[key] = arrayListOf(sValue)
							}
						}
					}
					//fix drillX for multi-series
					dataForWebView.drillX = mDataOrder.keys.toList().joinToString(",", "[", "]") {
						"\"$it\""
					}
					dataForWebView.min = if (min < 0) min else 0
					dataD3.min = if (min < 0) min else 0
					dataForWebView.max = max
					dataD3.max = min
					//region order data for data:
					val aDataOrder = ArrayList<ArrayList<String>>()
					for (index in 0 until aDataY.size)
					{
						val aItem = ArrayList<String>()
						for ((_, value) in mDataOrder)
						{
							val vString = value[index]
							aItem.add(vString)
						}
						if (dataForWebView.isReverseX) aItem.reverse()
						aDataOrder.add(aItem)
					}
					//endregion
					dataForWebView.dataChartBi = aDataOrder.joinToString(",\n", "[", "]") {
						it.joinToString(prefix = "{data: [", postfix = "]}")
					}
					//region data drillDown
					val mDrillDown = LinkedHashMap<String, ArrayList< ArrayList< ArrayList<String>>>>()
					for (mChild in aGroupedData)
					{
						for ((key, value) in mChild)
						{
							mDrillDown[key]?.run {
								this.add(value)
							} ?: run {
								mDrillDown[key] = arrayListOf(value)
							}
						}
					}
					queryBase.mDrillDown = mDrillDown
					queryBase.hasDrillDown = queryBase.mDrillDown != null
					//endregion
					if (dataForWebView.isReverseX) aCategoriesX.reverse()
					dataForWebView.catX = aCategoriesX.map {
						"\"${it.formatWithColumn(aColumn[posColumnX])}\""
					}.toString()
				}
				val type = aColumn[0].type
				if (type == TypeDataQuery.DATE_STRING/* && type1 != TypeDataQuery.DOLLAR_AMT*/)
				{
					DatePivot.buildDateString(aRows, aColumn).run {
						if (first != "" && second != 0)
						{
							dataForWebView.datePivot = first
							dataForWebView.rowsPivot = second
							queryBase.configActions = 1
						}
					}
				}

				dataForWebView.catYS = aCatYS.toString()
				when (dataForWebView.dataChartBi)
				{
					"[]" -> {
						val hasDate = aColumn[posColumnX].type == TypeDataQuery.DATE ||
							aColumn[posColumnX].type == TypeDataQuery.DATE_STRING
						val aRemove = indexCategoryEmpty(aRows, posColumnX)
						dataForWebView.dataChartBi = Table.generateDataTable(
							aRows, aColumn, queryBase.aIndex, aRemove, true, hasDate)
					}
					else -> {}
				}
			}
		}

		return Pair(dataForWebView, dataD3)
	}
}