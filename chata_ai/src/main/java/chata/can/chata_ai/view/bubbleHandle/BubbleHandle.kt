package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.BubbleData.widthDefault
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer.aChartColors
import chata.can.chata_ai.pojo.autoQL.AutoQLConfig
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.currency.Currency
import chata.can.chata_ai.pojo.dataFormatting.DataFormatting
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.loginIsComplete
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager
import chata.can.chata_ai.view.circle.CircleImageView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class BubbleHandle(
	private val context: Context,
	private val methodCanUse: () -> Unit)
{
	private lateinit var bubblesManager: BubblesManager
	private lateinit var bubbleLayout: BubbleLayout

	private lateinit var parentCircle: RelativeLayout
	private lateinit var circleImageView: CircleImageView

	companion object {
		lateinit var instance: BubbleHandle
		fun isInitialize(): Boolean
		{
			return this::instance.isInitialized
		}

		const val THEME_LIGHT = true
		const val THEME_DARK = false

		var isOpenChat = false
	}

	init {
		instance = this
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

	private var isNecessaryLogin = true
		set(value) {
			DataMessenger.isNecessaryLogin = value
			field = value
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
				bubbleLayout.definePositionInScreen(tmpPlacement)
			}
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
	var maxMessages = 2
		set(value) {
			field = if (value > 1) value else 2
		}

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

	fun setLightThemeColor(lightThemeColor: String): Boolean
	{
		val pData = lightThemeColor.isColor()
		if (pData.second)
		{
			SinglentonDrawer.lightThemeColor = pData.first
		}
		return pData.second
	}

	fun setDarkThemeColor(darkThemeColor: String): Boolean
	{
		val pData = darkThemeColor.isColor()
		if (pData.second)
		{
			SinglentonDrawer.darkThemeColor = pData.first
		}
		return pData.second
	}

	private var aThemePossible = arrayListOf("light", "dark")
	var theme: String = "dark"
		set(value) {
			if (theme != value && value in aThemePossible)
			{
				SinglentonDrawer.themeColor = value
				val themeColor = when(value)
				{
					"light" -> ThemeColor.lightColor
					"dark" -> ThemeColor.darkColor
					else -> ThemeColor.lightColor
				}
				ThemeColor.currentColor = themeColor
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
		val pData = valueColor.isColor()
		if (pData.second)
		{
			aChartColors.add(pData.first)
		}
		return pData.second
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

	/**
	 * open chat
	 */
	fun openChatActivity()
	{
		if (isNecessaryLogin && DataMessenger.token.isEmpty() && DataMessenger.JWT.isEmpty())
		{
			AlertDialog.Builder(context)
				.setCancelable(false)
				.setMessage("Enter your authentication data.")
				.setNeutralButton("Ok", null)
				.show()
		}
		else
		{
			if (!isOpenChat && loginIsComplete)
			{
				isOpenChat = true
				isVisible = false
				methodCanUse()
//				val intent = Intent(context, DataMessengerActivity::class.java)
//				createIntentData(intent)
//				context.startActivity(intent)
//				(context as? AppCompatActivity)
//					?.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_down)
			}
		}
	}

	private fun initChildView(): RelativeLayout
	{
		parentCircle = RelativeLayout(context)
		val lp = RelativeLayout.LayoutParams(-2, -2)
		parentCircle.layoutParams = lp
//		val color = context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
		val color = context.getParsedColor(ThemeColor.lightColor.drawerTextColorPrimary)
		val alphaColor = ColorUtils.setAlphaComponent(color, (0.25f * 255).toInt())
		val drawable = DrawableBuilder.setOvalDrawable(alphaColor)

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