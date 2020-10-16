package chata.can.chata_ai.activity.dataMessenger.holder.webView

import android.app.Activity
import android.content.Context
import android.webkit.JavascriptInterface
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.request.drillDown.DrillDownPresenter

class JavaScriptInterface(
	private val context: Context,
	private val queryBase: QueryBase,
	private val chatView: ChatContract.View?)
{
	private val presenter = DrillDownPresenter(queryBase, chatView)

	@JavascriptInterface
	fun boundMethod(content: String)
	{
		if (SinglentonDrawer.mIsEnableDrillDown)
		{
			val sizeColumn = queryBase.aColumn.size
			var newContent = content
			when(sizeColumn)
			{
				2 ->
				{
					if (content.contains("_"))
					{
						val aPositions = content.split("_")
						if (aPositions.size > 1)
						{
							aPositions[0].toIntOrNull()?.let {
								val aRows = queryBase.aRows
								newContent = aRows[it][0]
							}
						}
					}
					else
					{
						val index = queryBase.aXAxis.indexOf(newContent)
						if (index != -1)
						{
							newContent = queryBase.aXDrillDown[index]
						}
					}
				}
				3 ->
				{
					if (content.contains("_"))
					{
						val aPositions = content.split("_")
						if (aPositions.size > 1)
						{
							aPositions[0].toIntOrNull()?.let { itPosition ->
								val buildContent = StringBuilder("")
								val aRows = queryBase.aRows[itPosition]

								for (index in queryBase.aColumn.indices)
								{
									val column = queryBase.aColumn[index]
									if (column.isGroupable)
									{
										buildContent.append("${aRows[index]}_")
									}
								}
								newContent = buildContent.toString().removeSuffix("_")
							}
						}
					}
				}
			}

			if (newContent.isNotEmpty())
			{
				(context as? Activity)?.runOnUiThread {
					chatView?.isLoading(true)
				}
				presenter.postDrillDown(newContent)
			}
		}
	}
}