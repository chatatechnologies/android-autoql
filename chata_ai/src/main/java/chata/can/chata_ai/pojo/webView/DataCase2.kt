package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.query.SearchColumn

object DataCase2 {
	fun getSource(queryBase: QueryBase, dataD3: DataD3): DataD3 {
		queryBase.run {
			//aDataX
			val aDate = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DATE))
			//aDataY
			val aDollar = SearchColumn.getCountIndices(aColumn, arrayListOf(TypeDataQuery.DOLLAR_AMT))

			val aCommon = SearchColumn.getCountIndices(queryBase.aColumn, arrayListOf(TypeDataQuery.QUANTITY))
			val posColumnX = aDate[0]
			val posColumnY = aDollar[0]
			addIndices(posColumnX, posColumnY)
			configActions = 2

			if (!aDate.isNullOrEmpty() && !aDollar.isNullOrEmpty()) {
				SearchColumn.getSeriesColumn(queryBase)
				dataD3.indexData = aDate[0]
				queryBase.indexData = aDate[0]
				//region data currency group
				val mAllData = LinkedHashMap<String, String>()
				val mMaxData = LinkedHashMap<String, String>()
				val mDrillData = LinkedHashMap<String, String>()
				val aIndexCommon = queryBase.aCommon.map { it.first }

				queryBase.mSourceDrill.clear()
				for (index in aIndexCommon)
				{
					val multiData = MultiData.getMultiData(aDollar, aColumn, aRows, index)
					val multiData2 = MultiData.getMultiData(aCommon, aColumn, aRows, index)
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

				val multiData = MultiData.getMultiData(aDollar, aColumn, aRows, aDate[0])
				val multiData2 = MultiData.getMultiData(aCommon, aColumn, aRows, aDate[0])
				dataD3.categories = multiData.getCategoryMulti()
				dataD3.categories2 = multiData2.getCategoryMulti()
				//fix drillX for multi-series
				dataD3.drillX = multiData.mDataOrder.keys.toList().joinToString(",", "[", "]") {
					"\'$it\'"
				}

				MultiData.getMaxMin(dataD3, multiData)
				MultiData.getMaxMin(dataD3, multiData2, true)
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
				queryBase.hasDrillDown = queryBase.mSourceDrill.isNotEmpty()

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
		}
		return dataD3
	}
}