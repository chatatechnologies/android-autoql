package chata.can.chata_ai.retrofit.core

import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.SinglentonDrawer

fun getMenuStyle() = if (SinglentonDrawer.isDarkTheme())
	R.style.popupMenuStyle2
else R.style.popupMenuStyle1