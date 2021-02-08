package chata.can.chata_ai.data

import chata.can.chata_ai.pojo.ConstantDrawer.TOP_PLACEMENT
import chata.can.chata_ai.pojo.ConstantDrawer.BOTTOM_PLACEMENT
import chata.can.chata_ai.pojo.ConstantDrawer.LEFT_PLACEMENT
import chata.can.chata_ai.pojo.ConstantDrawer.RIGHT_PLACEMENT
import chata.can.chata_ai.pojo.ConstantDrawer.NOT_PLACEMENT
import chata.can.chata_ai.view.bubbleHandle.Authentication

class DataMessenger(
	var defaultTab: String,
	var authentication: Authentication,
	private val _placement: String
)
{
	var placement = when(_placement)
	{
		"top" -> TOP_PLACEMENT
		"bottom" -> BOTTOM_PLACEMENT
		"left" -> LEFT_PLACEMENT
		"right" -> RIGHT_PLACEMENT
		else -> NOT_PLACEMENT
	}
}