package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase

class DataMessengerModel: BaseModelList<ChatData>()
{
	override fun countData(): Int
	{
		var countSize = 0
		for (data in getData())
		{
			data.simpleQuery?.let {
//				countSize += 2
			} ?: run {
//				countSize++
			}

			countSize++
		}
		return countSize
	}

	fun restartData()
	{
		for (index in 0..countData())
		{
			get(index)?.let {
				it.simpleQuery?.let { simpleQuery ->
					if (simpleQuery is QueryBase)
					{
						simpleQuery.resetData()
					}
				}
			}
		}
	}
}