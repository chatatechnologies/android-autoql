package chata.can.chata_ai_api.fragment.dashboard.drillDown

import android.app.Activity
import android.content.Context
import android.webkit.JavascriptInterface
import chata.can.chata_ai.dialog.twiceDrill.TwiceDrillDialog
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase

class JavascriptInterface(private val context: Context, private val queryBase: QueryBase)
{
	@JavascriptInterface
	fun boundMethod(content: String)
	{
		queryBase.run {
			if (SinglentonDrawer.mIsEnableDrillDown)
			{
				when(displayType)
				{
					"line" ->
					{
						if (isTri)
						{
							drillForTri(content, queryBase)
						}
						else
						{
							drillForBi(content, queryBase)
						}
					}
					"bar", "column", "pie" ->
					{
						drillForBi(content, queryBase)
					}
					"heatmap", "bubble", "stacked_bar", "stacked_column", "stacked_line" ->
					{
						drillForTri(content, queryBase)
					}
				}
			}
		}
	}

	private fun drillForBi(content: String, queryBase: QueryBase)
	{
		queryBase.run {
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

	private fun drillForTri(content: String, queryBase: QueryBase)
	{
		queryBase.run {
			val aValues = content.split("_")
			if (aValues.isNotEmpty())
			{
				val indexX = aXAxis.indexOf(aValues[0])
				if (indexX != -1)
				{
					(context as? Activity)?.runOnUiThread {
						TwiceDrillDialog(context, this, aValues[0], aValues[1]).show()
					}
				}
			}
		}
	}
}