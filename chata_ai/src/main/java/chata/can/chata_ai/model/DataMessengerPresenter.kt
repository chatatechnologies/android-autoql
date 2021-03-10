package chata.can.chata_ai.model

import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors

object DataMessengerPresenter
{
	fun addChartColor(valueColor: String): Boolean
	{
		valueColor.isColor().run {
			if (second)
			{
				aChartColors.add(first)
			}
			return second
		}
	}
}