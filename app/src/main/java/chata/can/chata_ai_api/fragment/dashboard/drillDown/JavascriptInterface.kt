package chata.can.chata_ai_api.fragment.dashboard.drillDown

import android.app.Activity
import android.content.Context
import android.webkit.JavascriptInterface
import chata.can.chata_ai.dialog.twiceDrill.TwiceDrillDialog
import chata.can.chata_ai.pojo.chat.QueryBase

class JavascriptInterface(private val context: Context, private val queryBase: QueryBase)
{
	@JavascriptInterface
	fun boundMethod(content: String)
	{
		queryBase.run {
			when(displayType)
			{
				"bar", "column", "line", "pie" ->
				{
					val indexX = aXAxis.indexOf(content)
					if (indexX != -1)
					{
						val value = aXDrillDown[indexX]
						(context as? Activity)?.runOnUiThread {
							TwiceDrillDialog(context, queryBase, value).show()
						}
					}
				}
			}
		}
	}
}