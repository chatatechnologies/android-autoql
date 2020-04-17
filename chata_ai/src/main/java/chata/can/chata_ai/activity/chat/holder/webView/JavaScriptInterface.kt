package chata.can.chata_ai.activity.chat.holder.webView

import android.webkit.JavascriptInterface
import chata.can.chata_ai.activity.chat.ChatContract
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.request.drillDown.DrillDownPresenter

class JavaScriptInterface(
	private val queryBase: QueryBase,
	view: ChatContract.View?)
{
	private val presenter = DrillDownPresenter(queryBase, view)

	@JavascriptInterface
	fun boundMethod(content: String)
	{
		val tmp = if (content.contains("_"))
		{
			val aPositions = content.split("_")
			if (aPositions.size > 1)
			{
				aPositions[0].toIntOrNull()?.let {
					val aRows = queryBase.aRows
					aRows[it][0]
				} ?: run {""}
			}
			else ""
		}
		else
		{
			content
		}
		if (tmp.isNotEmpty())
		{
			presenter.postDrillDown(tmp)
		}
	}
}