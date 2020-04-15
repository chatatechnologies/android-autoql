package chata.can.chata_ai.activity.chat.holder.webView

import android.webkit.JavascriptInterface
import chata.can.chata_ai.activity.chat.ChatContract
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.request.drillDown.DrillDownPresenter

class JavaScriptInterface(
	queryBase: QueryBase,
	view: ChatContract.View?)
{
	private val presenter = DrillDownPresenter(queryBase, view)

	@JavascriptInterface
	fun boundMethod(content:String)
	{
		presenter.postDrillDown(content)
	}
}