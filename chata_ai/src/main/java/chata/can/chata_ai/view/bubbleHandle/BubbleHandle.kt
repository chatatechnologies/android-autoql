package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.ChatActivity
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.BubbleData.widthDefault
import chata.can.chata_ai.pojo.color.Color
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager
import chata.can.chata_ai.view.circle.CircleImageView

class BubbleHandle(private val context: Context)
{
	private lateinit var bubblesManager: BubblesManager
	private lateinit var bubbleLayout: BubbleLayout
	private lateinit var circleImageView: CircleImageView

	companion object {
		const val TOP_PLACEMENT = 1
		const val BOTTOM_PLACEMENT = 2
		const val LEFT_PLACEMENT = 3
		const val RIGHT_PLACEMENT = 4
		const val NOT_PLACEMENT = 5

		var isOpenChat = false
	}

	private var currentColor: Color ?= null
	private val defaultPlacement = 4
	//region API properties
	private var mPlacement = defaultPlacement
	private var customerName = ""
	//endregion

	init {
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
		currentColor = themeColor
	}

	fun setPlacement(placement: Int)
	{
		if (mPlacement != placement && placement > 0)
		{
			mPlacement = placement
			bubbleLayout.definePositionInScreen(placement)
		}
	}

	fun setCustomerName(customerName: String)
	{
		this.customerName = customerName
	}

	fun setVisible(isVisible: Boolean)
	{
		val tmpPlacement = if (isVisible)
			mPlacement
		else
			NOT_PLACEMENT
		bubbleLayout.definePositionInScreen(tmpPlacement)
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
		val rl = RelativeLayout(context)
		val lp = RelativeLayout.LayoutParams(-2, -2)
		rl.layoutParams = lp
		rl.setBackgroundResource(R.drawable.fake_shadow)

		circleImageView = CircleImageView(context)
		with(circleImageView)
		{
			rl.addView(this)
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
		return rl
	}

	fun onDestroy()
	{
		bubblesManager.recycle()
	}
}