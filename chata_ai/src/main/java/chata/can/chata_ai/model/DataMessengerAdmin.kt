package chata.can.chata_ai.model

import android.graphics.Color
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors

object DataMessengerAdmin
{
	fun setLightThemeColor(lightThemeColor: String): Boolean
	{
		lightThemeColor.isColor().run {
			if (second)
			{
				SinglentonDrawer.lightThemeColor = first
				SinglentonDrawer.pLightThemeColor = Color.parseColor(first)
			}
			return second
		}
	}

	fun setDarkThemeColor(darkThemeColor: String): Boolean
	{
		darkThemeColor.isColor().run {
			if (second)
			{
				SinglentonDrawer.darkThemeColor = first
				SinglentonDrawer.pDarkThemeColor = Color.parseColor(first)
			}
			return second
		}
	}

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

	fun changeColor(indexColor: Int, valueColor: String)
	{
		if (aChartColors.size > indexColor)
			aChartColors[indexColor] = valueColor
	}
}