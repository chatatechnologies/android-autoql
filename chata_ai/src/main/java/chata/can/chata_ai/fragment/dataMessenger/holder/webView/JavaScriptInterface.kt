package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import android.app.Activity
import android.content.Context
import android.view.View
import android.webkit.JavascriptInterface
import chata.can.chata_ai.dialog.manageData.ManageDataDialog
import chata.can.chata_ai.dialog.manageData.TypeColumnData
import chata.can.chata_ai.extension.toIntNotNull
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.request.drillDown.DrillDownPresenter
import org.json.JSONObject

class JavaScriptInterface(
	private val view: View,
	private val context: Context,
	private val queryBase: QueryBase,
	private val chatView: ChatContract.View?)
{
	private val presenter = DrillDownPresenter(queryBase, chatView)
	private val aType = arrayListOf("SELECTABLE", "PLAIN")

	@JavascriptInterface
	fun modalCategories(type: String, content: String)
	{
		if (type in aType)
		{
			val eType = if (type == "SELECTABLE") TypeColumnData.SELECTABLE
			else TypeColumnData.PLAIN
			ManageDataDialog(view.context, eType, content, view, queryBase).showDialog()
		}
	}

	@JavascriptInterface
	fun boundMethod(content: String)
	{
		if (SinglentonDrawer.mIsEnableDrillDown)
		{
			if (queryBase.mSourceDrill.isNotEmpty() && queryBase.showContainer != "#container")
				return
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
								postDrillDown(newContent)
							}
						}
					}
					else
					{
						val index = queryBase.aXAxis.indexOf(newContent)
						newContent = if (index != -1)
						{
							queryBase.aXDrillDown[index]
						}
						else
						{
							"undefined"
						}
						postDrillDown(newContent)
					}
				}
				3 ->
				{
					if (content.contains("_"))
					{
						val aPositions = content.split("_")
						if (aPositions.size > 1)
						{
							postDrillDown(newContent)
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
							//todo set index active for value on bottom multiples
							queryBase.getSourceDrill()?.let { mDrillDown ->
								mDrillDown[date]?.let {
									val values = it[index]

									val json = JSONObject().put("query", "")
									val newQueryBase = QueryBase(json).apply {
										for (column in queryBase.aColumn)
										{
											val tmp = column.copy()
											aColumn.add(tmp)
										}
										aRows.addAll(values)
										limitRowNum = values.size + 1
									}
									newQueryBase.queryId = queryBase.queryId
									//this line is the bug
									newQueryBase.resetData()
									(context as? Activity)?.runOnUiThread {
										chatView?.addNewChat(TypeChatView.WEB_VIEW, newQueryBase)
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private fun postDrillDown(newContent: String)
	{
		(context as? Activity)?.runOnUiThread {
			chatView?.isLoading(true)
		}
		presenter.postDrillDown(if (newContent.isEmpty()) "null" else newContent)
	}
}