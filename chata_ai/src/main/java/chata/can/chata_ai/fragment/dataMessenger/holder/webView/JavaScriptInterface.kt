package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import android.app.Activity
import android.content.Context
import android.webkit.JavascriptInterface
import chata.can.chata_ai.extension.toIntNotNull
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
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
			if (queryBase.mDrillDown != null)
			{
				if (queryBase.showContainer != "#container") return
			}
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
				else ->
				{
					content.split("_").run {
						if (this.size > 1)
						{
							val date = this[0]
							val index = this[1].toIntNotNull()
							queryBase.mDrillDown?.let { mDrillDown ->
								mDrillDown[date]?.let {
									val values = it[index]
									queryBase.json
									val copyQueryBase = queryBase.copy().apply {
										aRows.clear()
										aRows.addAll(values)
										resetData()
									}
									chatView?.addNewChat(TypeChatView.WEB_VIEW, copyQueryBase)
									newContent = ""
								}
							}
						}
					}
				}
			}

			if (newContent.isEmpty())
			{
				newContent = "null"
			}
			(context as? Activity)?.runOnUiThread {
				chatView?.isLoading(true)
			}
			presenter.postDrillDown(newContent)
		}
	}
}