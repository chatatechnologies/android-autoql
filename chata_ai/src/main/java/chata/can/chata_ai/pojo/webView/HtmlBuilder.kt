package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.dialog.manageData.FilterColumn
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn
import chata.can.chata_ai.pojo.query.SupportCase
import chata.can.chata_ai.pojo.script.hasNotValueInColumn
import chata.can.chata_ai.pojo.webView.TableTriBuilder.getData3Dimensions
import chata.can.chata_ai.pojo.script.setOrderRowByDate as orderRowDate

object HtmlBuilder
{
	private val aCount = arrayListOf(TypeDataQuery.QUANTITY, TypeDataQuery.DOLLAR_AMT/*, TypeDataQuery.DATE, TypeDataQuery.DATE_STRING*/)
	/*search countable type one by one*/
	fun hasCountableIndex(queryBase: QueryBase): Int
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

	fun build(queryBase: QueryBase): DataD3
	{
		val dataForWebView = DataForWebView()
		val dataD3 = DataD3()
		orderRowDate(queryBase)

		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		val limitRow = queryBase.limitRowNum
		//region container for webView
		val pData = TableHtmlBuilder.buildTable(aRows, aColumn, limitRow)
		dataForWebView.table = pData.first
		dataD3.table = pData.first
		dataForWebView.rowsTable = pData.second
		dataD3.rowsTable = pData.second

		var posColumnX = 0
		var posColumnY = 1
		var aDataX = arrayListOf<Int>()
		var aDataY = arrayListOf<Int>()
		var aSecondary = ArrayList<Int>()
		val isTriConfig = false
		//region define data with support Case
		/**
		 * configAllow in false is bi dimensional
		 * configAllow in true is tri dimensional
		 */
		when(queryBase.supportCase)
		{
			SupportCase.CASE_1 ->
			{
				return DataCase1.getSource(queryBase, dataD3)
			}
			SupportCase.CASE_2 ->
			{
				return DataCase2.getSource(queryBase, dataD3)
//				/*aDate*/
//				val aDate = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.DATE), 1)
//				/*aDollar*/
//				val aDollar = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.DOLLAR_AMT), 1)
//				posColumnX = aDate[0]
//				posColumnY = aDollar[0]
//				queryBase.addIndices(posColumnX, posColumnY)
//				queryBase.configActions = 2
			}
			SupportCase.CASE_3 ->
			{
				return DataCase3.getSource(queryBase, dataD3)
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

				dataForWebView.isReverseX = true
				dataForWebView.dataChartBi = Series.getDataSeries(aRows, aColumn, posColumnX, aNumber)
				val pMM = SearchColumn.getMinMaxColumns(aRows, aNumber)
				dataForWebView.max = pMM.first
				dataD3.max = pMM.first
				dataForWebView.min = pMM.second
				dataD3.min = pMM.second
			}
			SupportCase.CASE_6 ->
			{
				val aString = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.STRING), 2)
				val aNumber = SearchColumn.getNumberIndices(queryBase.aColumn, 1)
				aSecondary = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.QUANTITY))
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

				queryBase.addIndices(posColumnX, posColumnY)
				queryBase.configActions = 4
			}
			else ->
			{
				return DataCaseNone.getSource(queryBase, dataD3)
			}
		}
		if (queryBase.aRows.size == 1)
			queryBase.configActions = 0
		//endregion

		//TODO CHECK SUPPORT CASES
		Categories.run {
			val aIndicesIgnore = if (!isTriConfig) indexCategoryEmpty(aRows, posColumnX) else ArrayList()
			val aCatX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX, true,
					hasQuotes = true, allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore))
			//region xAxis
			val posTriConfig = if (isTriConfig) posColumnY else posColumnX
			queryBase.aXAxis = buildCategoryByPosition(
				Category(aRows, aColumn[posTriConfig], posTriConfig, false,
					hasQuotes = false, allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore))
			//endregion
			//var aCatYNotFormat: ArrayList<String> ?= null
			val aCatY = if (aColumn.size > posColumnY)
			{
				val maxIndex = if (isTriConfig) 2 else 1
				val aBaseTri = (0 .. maxIndex).toMutableList()
				aBaseTri.remove(posColumnX)
				aBaseTri.remove(posColumnY)
				val iForTri = if (aBaseTri.isEmpty()) posColumnY else aBaseTri[0]

				val columnY = aColumn[iForTri]
				buildCategoryByPosition(
					Category(aRows, columnY, iForTri, true, hasQuotes = true,
						allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore))
			}
			else
			{
				ArrayList()
			}

			//TODO edit for 3 cells by row
			if (!isTriConfig)
			{
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

			val aCatYS = if (aColumn.size > posColumnY)
			{
				//calculate max and min for bi dimensional
				val tmp = buildCategoryByPosition(
					Category(aRows, aColumn[posColumnY], posColumnY, true,
						hasQuotes = true, allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore))
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
				Category(aRows, aColumn[posColumnX], posColumnX, false,
					hasQuotes = false, allowRepeat = true, aIndicesIgnore = aIndicesIgnore))

			dataForWebView.drillX = buildCategoryByPosition(
				Category(aRows, aColumn[posColumnX], posColumnX, false,
					hasQuotes = true, allowRepeat = true, aIndicesIgnore = aIndicesIgnore)).toString()
			/*D3 variable*/
			dataD3.drillX = dataForWebView.drillX

			dataForWebView.drillY = if (aColumn.size > posColumnY) {
				val column = aColumn[posColumnY]
				buildCategoryByPosition(
					Category(
						aRows,
						aColumn[posColumnY],
						posColumnY,
						column.type != TypeDataQuery.DATE && column.type != TypeDataQuery.DATE_STRING,
						hasQuotes = true,
						allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore)).toString()
			} else arrayListOf<String>().toString()

			dataForWebView.drillTableY = if (aColumn.size > posColumnY) {
				buildCategoryByPosition(Category(aRows, aColumn[posColumnY], posColumnY, true,
					hasQuotes = true, allowRepeat = isTriConfig, aIndicesIgnore = aIndicesIgnore)).toString()
			} else arrayListOf<String>().toString()
			dataD3.drillTableY = dataForWebView.drillTableY

			if (dataForWebView.catX == "[]")
			{
				dataForWebView.catX = makeCategories(aCatX, !isTriConfig)
				dataD3.aCategoryX = dataForWebView.catX
			}
			dataForWebView.catY = makeCategories(aCatY, !isTriConfig)

			if (isTriConfig)
			{
				queryBase.aCategoryX = buildCategoryByPosition(
					Category(aRows, aColumn[posColumnX], posColumnX, true,
						hasQuotes = false, allowRepeat = !isTriConfig, aIndicesIgnore = aIndicesIgnore))

				for (cat in queryBase.aCategoryX)
				{
					queryBase.aCategory.add(FilterColumn(cat, true))
				}
				//region cat for D3 on tri dimensions
				dataD3.catHeatX = dataForWebView.catY
				dataD3.catHeatY = dataForWebView.catX

				val aNumber = SearchColumn.getNumberIndices(aColumn, 1)
				val aString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.STRING), 1, 1)
				val aDate = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE), 1)
				val aDateString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE_STRING), 1)

				//val aCatYTmp = aCatYNotFormat ?: aCatY
				val pCat = if (posColumnX == 1) Triple(aCatY, aCatX, true)
				else Triple(aCatX, aCatY, false)

				//get aDataTable and aMapPure
				val dataTableTri = TableTriBuilder.DataTableTri(aRows,
					aColumn[posColumnY],//countable column
					pCat.first,
					pCat.second,
					aNumber.isNotEmpty(),
					pCat.third)


				val pair = TableTriBuilder.generateDataTableTri(dataTableTri)
				dataD3.data = getData3Dimensions(pair.second, /*pair.first,*/ aCatX, aCatY)
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
								TableTriBuilder.DataPivot(
									mDataPivot,
									aColumn[aNumber.first()],
									aCatX,//newListDescending(aCatX),
									aCatY,
									nameHeader
								)
							)
							dataForWebView.datePivot = tPivot.first
							dataD3.pivot = tPivot.first
							dataForWebView.rowsPivot = tPivot.second
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
							dataD3.pivot = pPivot.first
							dataForWebView.rowsPivot = pPivot.second
							dataD3.rowsPivot = pPivot.second
						}
					}
					else
					{
						dataForWebView.datePivot = DatePivot.buildTri(aRows, aColumn)
						dataD3.pivot = dataForWebView.datePivot
						dataForWebView.rowsPivot = 180
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

				dataForWebView.catYS = ""
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
					SearchColumn.getSeriesColumn(queryBase)
					dataD3.indexData = aDataX[0]
					queryBase.indexData = aDataX[0]
					//region data currency group
					val mAllData = LinkedHashMap<String, String>()
					val mMaxData = LinkedHashMap<String, String>()
					val mDrillData = LinkedHashMap<String, String>()
					val aIndexCommon = queryBase.aCommon.map { it.first }

					queryBase.mSourceDrill.clear()
					for (index in aIndexCommon)
					{
						val multiData = MultiData.getMultiData(aDataY, aColumn, aRows, index)
						val multiData2 = MultiData.getMultiData(aSecondary, aColumn, aRows, index)
						val data1 = "[${MultiData.getTimesDataMulti(multiData.mDataOrder, aColumn[index])}]"
						val data2 = "[${MultiData.getTimesDataMulti(multiData2.mDataOrder, aColumn[index])}]"

						val drill = multiData.mDataOrder.keys.toList().joinToString(",", "[", "]") {
							"\'$it\'"
						}
						mDrillData["$index"] = drill

						val index1 = "\'${index}_1\'"
						val index2 = "\'${index}_2\'"
						//data
						mAllData[index1] = data1
						mAllData[index2] = data2
						//max
						mMaxData[index1] = "${multiData.aMax}"
						mMaxData[index2] = "${multiData2.aMax}"
						//region build mDrillDown
						val mDrillDown = LinkedHashMap<String, ArrayList< ArrayList< ArrayList<String>>>>()
						for (mChild in multiData.aGroupedData)
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
						queryBase.mSourceDrill[index] = mDrillDown
						//endregion
					}
					//build data string builder
					StringBuilder("{").apply {
						for ((key, value) in mAllData)
							append("$key: $value,\n")
						dataD3.aAllData = "${removeSuffix(", ")} }"
					}
					//build max data builder
					StringBuilder("{").apply {
						for ((key, value) in mMaxData)
							append("$key: $value,\n")
						dataD3.aMaxData = "${removeSuffix(", ")} }"
					}
					StringBuilder("{").apply {
						for ((key, value) in mDrillData)
							append("$key: $value,\n")
						dataD3.drillList = "${removeSuffix(", ")} }"
					}
					dataD3.data = "[]"

					val multiData = MultiData.getMultiData(aDataY, aColumn, aRows, aDataX[0])
					val multiData2 = MultiData.getMultiData(aSecondary, aColumn, aRows, aDataX[0])
					dataD3.categories = multiData.getCategoryMulti()
					dataD3.categories2 = multiData2.getCategoryMulti()
					//fix drillX for multi-series
					dataForWebView.drillX = multiData.mDataOrder.keys.toList().joinToString(",", "[", "]") {
						"\'$it\'"
					}
					dataD3.drillX = dataForWebView.drillX
					MultiData.getMaxMin(dataD3, multiData)
					MultiData.getMaxMin(dataD3, multiData2, true)
					val min = multiData.min
					val max = multiData.max
					dataForWebView.min = if (min < 0) min else 0
					dataForWebView.max = max
					//region order data for data:
					dataForWebView.dataChartBi = MultiData.getDataChartBiMulti(aDataY, multiData.mDataOrder, dataForWebView.isReverseX)
					//region data drillDown
					val mDrillDown = LinkedHashMap<String, ArrayList< ArrayList< ArrayList<String>>>>()
					for (mChild in multiData.aGroupedData)
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
//					queryBase.mDrillDown = mDrillDown
					queryBase.hasDrillDown = queryBase.mSourceDrill.isNotEmpty()// queryBase.mDrillDown != null
					//endregion
					if (dataForWebView.isReverseX) multiData.aCategoriesX.reverse()
					dataForWebView.catX = multiData.aCategoriesX.map {
						"\"${it.formatWithColumn(aColumn[aDataX[0]])}\""
					}.toString()

					val aCatCommon = ArrayList<String>()
					queryBase.aCommon.run {
						for(index in indices)
						{
							val column = get(index).second
							aCatCommon.add(column.displayName)
						}
					}
					dataD3.catCommon = aCatCommon.joinToString(", ", "[", "]") {
						"\"$it\""
					}
				}
				val type = aColumn[0].type
				if (type == TypeDataQuery.DATE_STRING/* && type1 != TypeDataQuery.DOLLAR_AMT*/)
				{
					DatePivot.buildDateString(aRows, aColumn).run {
						if (first != "" && second != 0)
						{
							dataForWebView.datePivot = first
							dataD3.pivot = first
							dataForWebView.rowsPivot = second
							dataD3.rowsPivot = second
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
						dataForWebView.dataChartBi = Table.generateDataTable(
							aRows, aColumn, queryBase.aIndex, aIndicesIgnore, true, hasDate)
					}
					else -> {}
				}
			}
		}

		return dataD3
	}
}