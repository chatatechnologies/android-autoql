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
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.Color
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.currency.Currency
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager
import chata.can.chata_ai.view.circle.CircleImageView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class BubbleHandle(private val context: Context)
{
	private lateinit var bubblesManager: BubblesManager
	private lateinit var bubbleLayout: BubbleLayout

	private lateinit var parentCircle: RelativeLayout
	private lateinit var circleImageView: CircleImageView

	companion object {
		const val THEME_LIGHT = true
		const val THEME_DARK = false

		const val TOP_PLACEMENT = 1
		const val BOTTOM_PLACEMENT = 2
		const val LEFT_PLACEMENT = 3
		const val RIGHT_PLACEMENT = 4
		const val NOT_PLACEMENT = 5

		var isOpenChat = false
	}

	private val defaultPlacement = 4
	//region API properties
	private var mPlacement = defaultPlacement
	private var customerName = ""
	//endregion

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

	fun changeColor(themeColor: Color)
	{
		ThemeColor.currentColor = themeColor
		updateColor()
	}

	private fun updateColor()
	{
		val drawable = DrawableBuilder.setOvalDrawable(
			ContextCompat.getColor(context,
				ThemeColor.currentColor.drawerColorPrimary)
		)
		parentCircle.background = drawable
		circleImageView.setCircleBackgroundColorResource(ThemeColor.currentColor.drawerBackgroundColor)
	}

	fun setPlacement(placement: Int)
	{
		if (mPlacement != placement && placement > 0)
		{
			mPlacement = placement
			bubbleLayout.definePositionInScreen(placement)
		}
	}

	fun setCurrencyCode(currencyCode: String): Boolean
	{
		val currency = Currency.mCurrency[currencyCode]
		return currency?.let {
			SinglentonDrawer.mCurrencyCode = currency
			true
		} ?: run { false }
	}

	fun setLanguageCode(languageCode: String)
	{
		SinglentonDrawer.mLanguageCode = languageCode
	}

	fun setFormatMonthYear(formatMonthYear: String)
	{
		SinglentonDrawer.mFormatMonthYear = formatMonthYear
	}

	fun setFormatDayMonthYear(formatDayMonthYear: String)
	{
		SinglentonDrawer.mFormatDayMonthYear = formatDayMonthYear
	}

	fun setDecimalsCurrency(decimalsCurrency: Int)
	{
		SinglentonDrawer.mDecimalsCurrency = decimalsCurrency
	}

	fun setDecimalsQuantity(decimalsQuantity: Int)
	{
		SinglentonDrawer.mDecimalsQuantity = decimalsQuantity
	}

	fun setCustomerName(customerName: String)
	{
		this.customerName = customerName
	}

	fun setIntroMessage(introMessage: String)
	{
		SinglentonDrawer.mIntroMessage = introMessage
	}

	fun setQueryPlaceholder(queryPlaceholder: String)
	{
		SinglentonDrawer.mQueryPlaceholder = queryPlaceholder
	}

	fun setVisible(isVisible: Boolean)
	{
		val tmpPlacement = if (isVisible)
			mPlacement
		else
			NOT_PLACEMENT
		bubbleLayout.definePositionInScreen(tmpPlacement)
	}

	fun isClearMessage(isClearMessage: Boolean)
	{
		SinglentonDrawer.mIsClearMessage = isClearMessage
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
			putExtra("CUSTOMER_NAME", customerName)
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
			setImageResource(R.drawable.ic_bubble)
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