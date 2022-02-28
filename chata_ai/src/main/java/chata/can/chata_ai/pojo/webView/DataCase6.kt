package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn
import chata.can.chata_ai.pojo.script.hasNotValueInColumn

object DataCase6 {
	fun getResource(queryBase: QueryBase, dataD3: DataD3): DataD3 {
		val isTriConfig = false
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		var posColumnX = 0
		var posColumnY = 1

		val aString = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.STRING), 2)
		val aNumber = SearchColumn.getNumberIndices(aColumn, 1)
		val aSecondary = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.QUANTITY))
		/*aDate*/
		val aDataX = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE))//Text
		/*aDollar*/
		val aDataY = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DOLLAR_AMT))//Numeric

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
		dataD3.nColumns = 4

		val aIndicesIgnore = Categories.indexCategoryEmpty(aRows, posColumnX)
		val aCatX = Categories.buildCategoryByPosition(
			Category(
				aRows = aRows,
				column = aColumn[posColumnX],
				position = posColumnX,
				isFormatted = true,
				hasQuotes = true,
				allowRepeat = !isTriConfig,
				aIndicesIgnore = aIndicesIgnore
			)
		)
		val posTriConfig = if (isTriConfig) posColumnY else posColumnX
		queryBase.aXAxis = Categories.buildCategoryByPosition(
			Category(
				aRows = aRows,
				column = aColumn[posTriConfig],
				position = posTriConfig,
				isFormatted = false,
				hasQuotes = false,
				allowRepeat = !isTriConfig,
				aIndicesIgnore = aIndicesIgnore
			)
		)

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
					aRows = aRows,
					column = columnY,
					position = iForTri,
					isFormatted = true,
					hasQuotes = true,
					allowRepeat = !isTriConfig,
					aIndicesIgnore = aIndicesIgnore
				)
			)
		}
		else arrayListOf()

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
				aRows = aRows,
				column = aColumn[posColumnX], position = posColumnX,
				isFormatted = false,
				hasQuotes = false,
				allowRepeat = true,
				aIndicesIgnore = aIndicesIgnore
			)
		)

		dataD3.drillX = Categories.buildCategoryByPosition(
			Category(
				aRows = aRows,
				column = aColumn[posColumnX],
				position = posColumnX,
				isFormatted = false,
				hasQuotes = true,
				allowRepeat = true,
				aIndicesIgnore = aIndicesIgnore
			)
		).toString()

		dataD3.drillTableY = if (aColumn.size > posColumnY) {
			Categories.buildCategoryByPosition(
				Category(
					aRows = aRows,
					column = aColumn[posColumnY],
					position = posColumnY,
					isFormatted = true,
					hasQuotes = true,
					allowRepeat = isTriConfig,
					aIndicesIgnore = aIndicesIgnore
				)
			).toString()
		} else "[]"

		if (dataD3.aCategoryX == "[]") {
			dataD3.aCategoryX = Categories.makeCategories(aCatX)
		}

		if (aDataX.isNotEmpty() || aDataY.isNotEmpty()) {
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
					"\"$it\""
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
			dataD3.drillX = multiData.mDataOrder.keys.toList().joinToString(",", "[", "]") {
				"\'$it\'"
			}
			MultiData.getMaxMin(dataD3, multiData)
			MultiData.getMaxMin(dataD3, multiData2, true)
			val min = multiData.min
			val max = multiData.max
			dataD3.min = if (min < 0) min else 0
			dataD3.max = max
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
					dataD3.pivot = first
					dataD3.rowsPivot = second
					queryBase.configActions = 1
				}
			}
		}

		return dataD3
	}
}