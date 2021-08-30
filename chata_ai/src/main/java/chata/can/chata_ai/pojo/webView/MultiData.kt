package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.webView.multiData.MultiDataModel

object MultiData
{
	fun getMultiData(aIndices: ArrayList<Int>, aColumn: ArrayList<ColumnQuery>,
		aRows: ArrayList<ArrayList<String>>, indexX: Int): MultiDataModel
	{
		val aCategoryMulti2 = ArrayList<String>()
		val aCategoriesX = ArrayList<String>()
		val aData = ArrayList< LinkedHashMap<String, Double>>()
		val aGroupedData = ArrayList<LinkedHashMap<String, ArrayList< ArrayList<String>/*might transform to array list*/>>>()

		for (iItem in aIndices)
		{
			aCategoryMulti2.add(aColumn[iItem].displayName)
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
		return MultiDataModel(aCategoryMulti2, aCategoriesX, mDataOrder, aGroupedData, aData, min, max)
	}

	fun getTimesDataMulti(
		mDataOrder: LinkedHashMap<String, ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>,
		aDataX: ArrayList<Int>): String
	{
		val sbMultiSeries = StringBuilder()
		val mDateParsed = LinkedHashMap<String, Int>()
		for ((key, aValue) in mDataOrder)
		{
			val columnDate = aColumn[aDataX[0]]
			val formattedKey = key.formatWithColumn(columnDate)
			val parsedKey =
				mDateParsed[formattedKey]?.let {
					mDateParsed[formattedKey] = it + 1
					"${formattedKey}_${it + 1}"
				} ?: run {
					mDateParsed[formattedKey] = 1
					"${formattedKey}_1"
				}

			var sValues = ""
			for ((index, value) in aValue.withIndex())
			{
				sValues += ", time_$index: $value"
			}
			sbMultiSeries.append("{name: \'$parsedKey\'$sValues},\n")
		}
		return "${sbMultiSeries.removeSuffix(",\n")}"
	}

	fun getDataChartBiMulti(
		aDataY: ArrayList<Int>,
		mDataOrder: LinkedHashMap<String, ArrayList<String>>,
		isReverse: Boolean): String
	{
		val aDataOrder = ArrayList<ArrayList<String>>()
		for (index in 0 until aDataY.size)
		{
			val aItem = ArrayList<String>()
			for ((_, value) in mDataOrder)
			{
				val vString = value[index]
				aItem.add(vString)
			}
			if (isReverse)/*if (dataForWebView.isReverseX)*/ aItem.reverse()
			aDataOrder.add(aItem)
		}
		//endregion
		/*dataForWebView.dataChartBi = */return aDataOrder.joinToString(",\n", "[", "]") {
			it.joinToString(prefix = "{data: [", postfix = "]}")
		}
	}
}