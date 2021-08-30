package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.webView.multiData.MultiDataModel

object MultiData
{
	fun getMultiData(aSecondary: ArrayList<Int>, aColumn: ArrayList<ColumnQuery>,
		aRows: ArrayList<ArrayList<String>>, indexX: Int): MultiDataModel
	{
		val aCategoryMulti2 = ArrayList<String>()
		val aCategoriesX = ArrayList<String>()
		val aData = ArrayList< LinkedHashMap<String, Double>>()
		val aGroupedData = ArrayList<LinkedHashMap<String, ArrayList< ArrayList<String>/*might transform to array list*/>>>()

		for (iItem in aSecondary)
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
		for (mChild in aData)
		{
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
		return MultiDataModel(aCategoryMulti2, aCategoriesX, mDataOrder, aGroupedData, aData)
	}
}