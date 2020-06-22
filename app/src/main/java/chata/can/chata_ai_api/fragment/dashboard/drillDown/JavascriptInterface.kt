package chata.can.chata_ai_api.fragment.dashboard.drillDown

import android.webkit.JavascriptInterface
import chata.can.chata_ai.pojo.chat.QueryBase

class JavascriptInterface(private val queryBase: QueryBase)
{
	@JavascriptInterface
	fun boundMethod(content: String)
	{
		when(queryBase.displayType)
		{
			"column" ->
			{
				val indexX = queryBase.aXAxis.indexOf(content)
				if (indexX != -1)
				{

				}
			}
		}
		println("Dashboard log: $content")
	}
}