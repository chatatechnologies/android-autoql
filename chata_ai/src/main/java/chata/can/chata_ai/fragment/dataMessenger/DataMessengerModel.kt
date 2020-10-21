package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase

class DataMessengerModel: BaseModelList<ChatData>()
{
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