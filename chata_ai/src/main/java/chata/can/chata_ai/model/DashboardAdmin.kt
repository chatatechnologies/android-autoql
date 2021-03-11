package chata.can.chata_ai.model

import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.pojo.SinglentonDashboard

object DashboardAdmin
{
	fun setDashboardColor(dashboardColor: String): Boolean
	{
		dashboardColor.isColor().run {
			if (second)
			{
				SinglentonDashboard.dashboardColor = first
			}
			return second
		}
	}
}