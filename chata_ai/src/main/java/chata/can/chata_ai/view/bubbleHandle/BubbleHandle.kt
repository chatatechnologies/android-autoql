package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.ChatActivity
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.BubbleData.widthDefault
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLConfig
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.color.ThemeConfig
import chata.can.chata_ai.pojo.currency.Currency
import chata.can.chata_ai.pojo.dataFormatting.DataFormatting
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager
import chata.can.chata_ai.view.circle.CircleImageView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern

class BubbleHandle(private val context: Context)
{
	private lateinit var bubblesManager: BubblesManager
	private lateinit var bubbleLayout: BubbleLayout

	private lateinit var parentCircle: RelativeLayout
	private lateinit var circleImageView: CircleImageView

	companion object {
		const val THEME_LIGHT = true
		const val THEME_DARK = false

		var isOpenChat = false
	}

	init {
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
			bubbleLayout.definePositionInScreen(tmpPlacement)
			field = value
		}

	var placement = ConstantDrawer.RIGHT_PLACEMENT
	set(value) {
		if (placement != value && placement > 0)
		{
			field = value
			bubbleLayout.definePositionInScreen(placement)
		}
	}

	var title = "Data Messenger"
	var userDisplayName = "there"
	var introMessage = "Hi %s! Let\'s dive into your data. What can I help you discover today?"
	var inputPlaceholder = "Type your queries here"
	var maxMessages = 0
	var clearOnClose = false
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

	private var lightThemeColor = "#28A8E0"
	private var darkThemeColor = "#525252"

	fun setLightThemeColor(lightThemeColor: String): Boolean
	{
		val pData = isColor(lightThemeColor)
		if (pData.second)
		{
			this.lightThemeColor = pData.first
			themeConfig.accentColor = this.lightThemeColor
		}
		return pData.second
	}

	fun setDarkThemeColor(darkThemeColor: String): Boolean
	{
		val pData = isColor(darkThemeColor)
		if (pData.second)
		{
			this.darkThemeColor = pData.first
			themeConfig.accentColor = this.darkThemeColor
		}
		return pData.second
	}

	private val aChartColors = ArrayList<String>()
	var themeConfig = ThemeConfig(
		"light", lightThemeColor, "#sans-serif", aChartColors)

	var aThemePossible = arrayListOf("light", "dark")
	var theme: String = "light"
	set(value) {
		if (theme != value && value in aThemePossible)
		{
			themeConfig.theme = value
			val themeColor = when(value)
			{
				"light" -> ThemeColor.lightColor
				"dark" -> ThemeColor.darkColor
				else -> ThemeColor.lightColor
			}
			ThemeColor.currentColor = themeColor
			updateColor()
			field = value
		}
	}


	//endregion

	private fun updateColor()
	{
		val drawable = DrawableBuilder.setOvalDrawable(
			ContextCompat.getColor(context,
				ThemeColor.currentColor.drawerColorPrimary)
		)
		parentCircle.background = drawable
		circleImageView.setCircleBackgroundColorResource(ThemeColor.currentColor.drawerBackgroundColor)
	}

	fun addChartColor(valueColor: String): Boolean
	{
		val pData = isColor(valueColor)
		if (pData.second)
		{
			aChartColors.add(pData.first)
		}
		return pData.second
	}

	private fun isColor(valueColor: String): Pair<String, Boolean>
	{
		val newColor = valueColor.toLowerCase(Locale.US)
		val colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})")

		return Pair(newColor, colorPattern.matcher(newColor).matches())
	}

	private fun initBubbleLayout()
	{
		bubbleLayout = BubbleLayout(context)
		with(bubbleLayout)
		{
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

	fun reloadData()
	{
		SinglentonDrawer.mModel.clearData()
		/*isVisible = true
		placement = ConstantDrawer.RIGHT_PLACEMENT
		title = "Data Messenger"
		userDisplayName = "there"
		introMessage = "Hi %s! Let\'s dive into your data. What can I help you discover today?"
		inputPlaceholder = "Type your queries here"
		maxMessages = 0
		clearOnClose = false
		enableVoiceRecord = true*/
	}

	/**
	 * open chat
	 */
	fun openChatActivity()
	{
		if (!isOpenChat)
		{
			isOpenChat = true
			val intent = Intent(context, ChatActivity::class.java)
			createIntentData(intent)
			context.startActivity(intent)
			(context as? AppCompatActivity)
				?.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_down)
		}
	}

	private fun createIntentData(intent: Intent)
	{
		with(intent)
		{
			putExtra("CUSTOMER_NAME", userDisplayName)
			putExtra("TITLE", title)
			putExtra("INTRO_MESSAGE", introMessage)
			putExtra("INPUT_PLACE_HOLDER", inputPlaceholder)
			putExtra("MAX_MESSAGES", maxMessages)
			putExtra("CLEAR_ON_CLOSE", clearOnClose)
			putExtra("ENABLE_VOICE_RECORD", enableVoiceRecord)
		}
	}

	private fun initChildView(): RelativeLayout
	{
		parentCircle = RelativeLayout(context)
		val lp = RelativeLayout.LayoutParams(-2, -2)
		parentCircle.layoutParams = lp
		val drawable = DrawableBuilder.setOvalDrawable(
			ContextCompat.getColor(context,
				ThemeColor.currentColor.drawerColorPrimary)
		)
		//parentCircle.setBackgroundResource(R.drawable.fake_shadow)
		parentCircle.background = drawable

		circleImageView = CircleImageView(context)
		with(circleImageView)
		{
			parentCircle.addView(this)
			layoutParams.height = heightDefault
			layoutParams.width = widthDefault
			if (layoutParams is ViewGroup.MarginLayoutParams)
			{
				(layoutParams as ViewGroup.MarginLayoutParams).setMargins(
					marginLeftDefault, marginLeftDefault, marginLeftDefault, marginLeftDefault)
			}
			setImageResource(R.drawable.ic_bubble_main)
			setCircleBackgroundColorResource(R.color.white)

		}
		return parentCircle
	}

	private fun getCurrency()
	{
		context.assets?.let {
				itAssets ->
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

	fun onDestroy()
	{
		bubblesManager.recycle()
	}
}