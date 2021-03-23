package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.graphics.Color
import android.widget.RelativeLayout
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.R
import chata.can.chata_ai.data.DataMessenger
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.BubbleData.widthDefault
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors
import chata.can.chata_ai.pojo.autoQL.AutoQLConfig
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.activity.dm.Currency
import chata.can.chata_ai.pojo.dataFormatting.DataFormatting
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager
import chata.can.chata_ai.view.circle.CircleImageView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class BubbleHandle(
	private val context: Context,
	dataMessenger: DataMessenger,
	private val methodCanUse: () -> Unit
)
{
	private lateinit var bubblesManager: BubblesManager
	private lateinit var bubbleLayout: BubbleLayout

	private lateinit var parentCircle: RelativeLayout
	private lateinit var circleImageView: CircleImageView

	companion object {
		var instance: BubbleHandle ?= null

		const val THEME_LIGHT = true
		const val THEME_DARK = false

		var isOpenChat = false
	}

	init {
		instance = this
		DataMessengerRoot.dataMessenger = dataMessenger
		getCurrency()
		BubblesManager.Builder(context)
			.setInitializationCallback { initBubbleLayout() }
			.build()?.let {
				bubblesManager = it
			}
		if (::bubblesManager.isInitialized)
		{
			bubblesManager.initialize()
		}
	}

	//region properties like docs react data messenger
	var isVisible: Boolean = true
		set(value) {
			val tmpPlacement = if (value)
				placement
			else
				ConstantDrawer.NOT_PLACEMENT
			if (::bubbleLayout.isInitialized)
			{
				bubbleLayout.definePositionInScreen(value, tmpPlacement)
			}
			field = value
		}

	var placement = ConstantDrawer.RIGHT_PLACEMENT
		set(value) {
			if (placement != value && placement > 0)
			{
				field = value
				bubbleLayout.definePositionInScreen(isVisible, placement)
			}
		}

	var title = "Data Messenger"
	var userDisplayName = "there"
	var introMessage = "Hi %s! Let\'s dive into your data. What can I help you discover today?"
	var inputPlaceholder = "Type your queries here"
	var maxMessages = 2
		set(value) {
			field = if (value > 1) value else 2
		}

	var clearOnClose = false
	var isDarkenBackgroundBehind = true
	var visibleExploreQueries = true
	var visibleNotification = true
	var enableVoiceRecord = true

	var autoQLConfig = AutoQLConfig(
		_enableAutocomplete = true,
		_enableQueryValidation = true,
		_enableQuerySuggestions = true,
		_enableDrilldowns = true,
		enableColumnVisibilityManager = true,
		debug = true)

	var dataFormatting = DataFormatting(
		"USD",
		"en-US",
		2,
		1,
		"MMM YYYY",
		"MMM DD, YYYY")

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

	private var aThemePossible = arrayListOf("light", "dark")
	var theme: String = "dark"
		set(value) {
			if (theme != value && value in aThemePossible)
			{
				val themeColor = when(value)
				{
					"light" -> ThemeColor.lightColor
					"dark" -> ThemeColor.darkColor
					else -> ThemeColor.lightColor
				}
				ThemeColor.currentColor = themeColor
				SinglentonDrawer.themeColor = value
				field = value
			}
		}
	//endregion

	fun changeColor(indexColor: Int, valueColor: String)
	{
		if (aChartColors.size > indexColor)
			aChartColors[indexColor] = valueColor
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

	private fun initBubbleLayout()
	{
		bubbleLayout = BubbleLayout(context)
		bubbleLayout.run {
			clipChildren = false
			clipToPadding = false

			addView(initChildView())
			setOnBubbleClickListener {
				openChatActivity()
			}

			setShouldStickToWall(true)

			bubblesManager.addBubble(bubbleLayout, 0,0)
		}
	}

	/**
	 * open chat
	 */
	fun openChatActivity()
	{
//		if (isNecessaryLogin && DataMessenger.token.isEmpty() && DataMessenger.JWT.isEmpty())
//		{
//			AlertDialog.Builder(context)
//				.setCancelable(false)
//				.setMessage("Enter your authentication data.")
//				.setNeutralButton("Ok", null)
//				.show()
//		}
//		else
//		{
//			if (!isOpenChat && loginIsComplete)
//			{
//				isOpenChat = true
//				isVisible = false
//				methodCanUse()
//			}
//		}
		isOpenChat = true
		isVisible = false
		methodCanUse()
	}

	private fun initChildView(): RelativeLayout
	{
		parentCircle = RelativeLayout(context)
		val lp = RelativeLayout.LayoutParams(-2, -2)
		parentCircle.layoutParams = lp
		val alphaColor = ColorUtils.setAlphaComponent(
			ThemeColor.lightColor.pDrawerTextColorPrimary, (0.25f * 255).toInt())
		val drawable = DrawableBuilder.setOvalDrawable(alphaColor)

		parentCircle.background = drawable

		circleImageView = CircleImageView(context)
		with(circleImageView)
		{
			parentCircle.addView(this)
			layoutParams.height = heightDefault
			layoutParams.width = widthDefault
			margin(marginLeftDefault, marginLeftDefault, marginLeftDefault, marginLeftDefault
			)
			setImageResource(R.drawable.ic_bubble_chata)
			setCircleBackgroundColorResource(R.color.blue_chata_circle)
		}
		return parentCircle
	}

	fun setImageResource(intRest: Int)
	{
		circleImageView.setImageResource(intRest)
	}

	fun setBackgroundColor(intColor: Int)
	{
		circleImageView.setCircleBackgroundColorResource(intColor)
	}

	private fun getCurrency()
	{
		context.assets?.let { itAssets ->
			try {
				val inputStream: InputStream = itAssets.open("currency_symbols.json")
				val inputStreamReader = InputStreamReader(inputStream)
				val sb = StringBuilder()
				var line: String?
				val br = BufferedReader(inputStreamReader)
				line = br.readLine()
				while (line != null)
				{
					sb.append(line)
					line = br.readLine()
				}
				br.close()
				getCurrencySymbol(sb.toString())
			}
			catch (ex: Exception) { }
		}
	}

	private fun getCurrencySymbol(currencySymbol: String)
	{
		with(JSONObject(currencySymbol))
		{
			for (key in keys())
			{
				if (!isNull(key))
				{
					val value = optString(key) ?: ""
					if (value.isNotEmpty())
					{
						Currency.mCurrency[key] = value
					}
				}
			}
		}
	}
//	fun onDestroy()
//	{
//		bubblesManager.recycle()
//	}
}