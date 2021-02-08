package chata.can.chata_ai.data

import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLConfig
import chata.can.chata_ai.pojo.color.ThemeConfig
import chata.can.chata_ai.pojo.dataFormatting.DataFormatting
import chata.can.chata_ai.view.bubbleHandle.Authentication

class DataMessenger(
	var defaultTab: String,
	var authentication: Authentication,
	var placement: Int = ConstantDrawer.RIGHT_PLACEMENT
)
{
	var methodCanUse: () -> Unit = {}
	var enableExploreQueriesTab = true
	var title = "Data Messenger"
	var userDisplayName = "there"
	var introMessage = "Hi ${userDisplayName}! I'm here to help you access, search and analyze your data."
	var queryQuickStartTopics = ArrayList<String>()
	var inputPlaceholder = "Type your queries here"
	var maskClosable = true
	var onMaskClick: () -> Unit = {}
	var onVisibleChange: () -> Unit = {}
	var maxMessages = 0
	var clearOnClose = false
	var enableVoiceRecord = true
	//autoQLConfig
	var autoql = AutoQLConfig()
	var dataFormatting = DataFormatting()
	var themeConfig = ThemeConfig()
}