package chata.can.chata_ai.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.pager.PagerData
import chata.can.chata_ai.addFragment
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesFragment
import chata.can.chata_ai.fragment.notification.NotificationFragment
import chata.can.chata_ai.model.BubbleData
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.request.Poll
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import org.json.JSONArray
import org.json.JSONObject

class PagerOptions: RelativeLayout, View.OnClickListener, StatusResponse
{
	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyle: Int)
		: super(context, attrs, defStyle) { init() }

	private lateinit var llMenu: LinearLayout
	private lateinit var rlChat: View
	private lateinit var ivChat: ImageView
	private lateinit var rlTips: View
	private lateinit var ivTips: ImageView
	private lateinit var rlNotify: View
	private lateinit var ivNotify: ImageView
	private lateinit var tvNotification: TextView
	private lateinit var rlLocal: View
	private lateinit var ivClose: ImageView
	private lateinit var tvTitle: TextView
	private lateinit var ivClear: ImageView

	private var rlSelected: View ?= null
	private var ivSelected: ImageView ?= null
	var fragmentManager: FragmentManager ?= null
	var bubbleData: BubbleData?= null
	private var fragment: Fragment = DataMessengerFragment.newInstance()
	var isVisible = false

	override fun onClick(view: View?)
	{
		view?.let { _view ->
			when(_view.id)
			{
				R.id.llMenu, R.id.ivClose ->
				{
					BubbleHandle.isOpenChat = false
					BubbleHandle.instance.isVisible = true
					if (PagerData.clearOnClose)
						SinglentonDrawer.mModel.clear()
					setStatusGUI(false)
				}
				R.id.rlChat ->
				{
					updateTitle(DataMessengerFragment.nameFragment)
					changeColor(rlChat, ivChat)
					setVisibleDelete(true)
					fragment = DataMessengerFragment.newInstance()
					setDataToDataMessenger()
					fragmentManager?.let { addFragment(it, fragment) }
				}
				R.id.rlTips ->
				{
					updateTitle(ExploreQueriesFragment.nameFragment)
					changeColor(rlTips, ivTips)
					setVisibleDelete(false)
					fragment = ExploreQueriesFragment.newInstance()
					fragmentManager?.let { addFragment(it, fragment) }
				}
				R.id.rlNotify ->
				{
					updateTitle(NotificationFragment.nameFragment)
					changeColor(rlNotify, ivNotify)
					setVisibleDelete(false)
					showNotification()
					fragment = NotificationFragment.newInstance()
					fragmentManager?.let { addFragment(it, fragment) }
				}
				else -> {}
			}
		}
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.let {}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			val message = it.optString("message") ?: ""
			showNotify(message != "ok")
		}
	}

	fun init()
	{
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = inflater.inflate(R.layout.view_pager_options, nullParent)

		view.run {
			llMenu = findViewById(R.id.llMenu)
			rlChat = findViewById(R.id.rlChat)
			ivChat = findViewById(R.id.ivChat)
			rlTips = findViewById(R.id.rlTips)
			ivTips = findViewById(R.id.ivTips)
			rlNotify = findViewById(R.id.rlNotify)
			ivNotify = findViewById(R.id.ivNotify)
			tvNotification = findViewById(R.id.tvNotification)
			rlLocal = findViewById(R.id.rlLocal)
			ivClose = findViewById(R.id.ivClose)
			tvTitle = findViewById(R.id.tvTitle)
			ivClear = findViewById(R.id.ivClear)
		}

		rlSelected = rlChat
		ivSelected = ivChat
		setListener()
		setColors()

		addView(view)
	}

	fun paintViews()
	{
		when(val placement = BubbleHandle.instance.placement)
		{
			ConstantDrawer.LEFT_PLACEMENT, ConstantDrawer.RIGHT_PLACEMENT ->
			{
				//region llMenu
				llMenu.layoutParams = (llMenu.layoutParams as? LayoutParams)?.apply {
					height = -1
					width = dpToPx(36f)
					llMenu.orientation = LinearLayout.VERTICAL
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
						removeRules(arrayListOf(ALIGN_PARENT_TOP, ALIGN_PARENT_START, ALIGN_PARENT_BOTTOM))
						addRule(ALIGN_PARENT_END, TRUE)
					}
					else
					{
						removeRules(arrayListOf(ALIGN_PARENT_TOP, ALIGN_PARENT_END, ALIGN_PARENT_BOTTOM))
						addRule(ALIGN_PARENT_START, TRUE)
					}
				}
				//endregion
				//region little views
				(ivChat.layoutParams as? LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				(ivTips.layoutParams as? LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				(ivNotify.layoutParams as? LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				//endregion
				//region rlLocal
				rlLocal.layoutParams = (rlLocal.layoutParams as? LayoutParams)?.apply {
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
						removeRules(arrayListOf(ABOVE, BELOW, END_OF))
						addRule(START_OF, R.id.llMenu)
					}
					else
					{
						removeRules(arrayListOf(ABOVE, BELOW, START_OF))
						addRule(END_OF, R.id.llMenu)
					}
				}
				//endregion
			}
			ConstantDrawer.BOTTOM_PLACEMENT, ConstantDrawer.TOP_PLACEMENT ->
			{
				//region llMenu
				llMenu.layoutParams = (llMenu.layoutParams as? LayoutParams)?.apply {
					height = dpToPx(36f)
					width = -1
					llMenu.orientation = LinearLayout.HORIZONTAL
					if (placement == ConstantDrawer.BOTTOM_PLACEMENT)
					{
						removeRules(arrayListOf(ALIGN_PARENT_START, ALIGN_PARENT_END, ALIGN_PARENT_BOTTOM))
						addRule(ALIGN_PARENT_TOP, TRUE)
					}
					else
					{
						removeRules(arrayListOf(ALIGN_PARENT_START, ALIGN_PARENT_END, ALIGN_PARENT_TOP))
						addRule(ALIGN_PARENT_BOTTOM, TRUE)
					}
				}
				//endregion
				//region little views
				(ivChat.layoutParams as? LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				(ivTips.layoutParams as? LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				(ivNotify.layoutParams as? LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				//endregion
				//region rlLocal
				rlLocal.layoutParams = (rlLocal.layoutParams as? LayoutParams)?.apply {
					if (placement == ConstantDrawer.BOTTOM_PLACEMENT)
					{
						removeRules(arrayListOf(END_OF, START_OF, ABOVE))
						addRule(BELOW, R.id.llMenu)
					}
					else
					{
						removeRules(arrayListOf(END_OF, START_OF, BELOW))
						addRule(ABOVE, R.id.llMenu)
					}
				}
				//endregion
			}
		}
	}

	fun setStatusGUI(isVisible: Boolean)
	{
		this.isVisible = isVisible
		val iVisible = if (isVisible)
		{
			if (fragment is DataMessengerFragment)
				setDataToDataMessenger()
			fragmentManager?.let { addFragment(it, fragment) }
			context?.let {
				val animationTop = AnimationUtils.loadAnimation(it, R.anim.scale)
				startAnimation(animationTop)
			}
			View.VISIBLE
		}
		else View.GONE
		llMenu.visibility = iVisible
		rlLocal.visibility = iVisible
		updateTitle(DataMessengerFragment.nameFragment)
	}

	private fun setVisibleDelete(bVisible: Boolean)
	{
		ivClear.visibility = if (bVisible) View.VISIBLE else View.GONE
	}

	fun showNotify(bVisible: Boolean)
	{
		tvNotification.visibility = if (bVisible) View.VISIBLE else View.GONE
	}

	private fun showNotification()
	{
		if (tvNotification.visibility == View.VISIBLE)
		{
			Poll.callShowNotification(this)
		}
	}

	private fun updateTitle(title: String)
	{
		val tmp = if (DataMessengerFragment.nameFragment == title)
		{
			val localTitle = bubbleData?.title ?: ""
			if (localTitle.isNotEmpty())
				localTitle
			else context?.getString(R.string.data_messenger) ?: ""
		}
		else title
		tvTitle.text = tmp
	}

	private fun setListener()
	{
		llMenu.setOnClickListener(this)
		rlChat.setOnClickListener(this)
		rlTips.setOnClickListener(this)
		rlNotify.setOnClickListener(this)
		ivClose.setOnClickListener(this)
	}

	private fun setColors()
	{
		context?.run {
			ivChat.setColorFilter(getParsedColor(R.color.black))
			ivTips.setColorFilter(getParsedColor(R.color.white))
			ivNotify.setColorFilter(getParsedColor(R.color.white))
			llMenu.setBackgroundColor(getParsedColor(R.color.gray_modal))
			tvNotification.background = DrawableBuilder.setOvalDrawable(
				getParsedColor(R.color.red_notification))
			ivClear.setColorFilter(getParsedColor(R.color.white))
		}
	}

	private fun changeColor(rlNew: View, ivNew: ImageView)
	{
		context?.run {
			if (rlSelected != rlNew && ivSelected != ivNew)
			{
				rlSelected?.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
				ivSelected?.setColorFilter(getParsedColor(R.color.white))
				rlNew.setBackgroundColor(getParsedColor(R.color.white))
				ivNew.setColorFilter(getParsedColor(R.color.black))
				rlSelected = rlNew
				ivSelected = ivNew
			}
		}
	}

	private fun LayoutParams.removeRules(aRules: ArrayList<Int>)
	{
		for (rule in aRules)
		{
			removeRule(rule)
		}
	}

	private fun setDataToDataMessenger()
	{
		fragment.arguments?.let {
			bubbleData?.let { bubble ->
				it.putString("CUSTOMER_NAME", bubble.customerName)
				it.putString("TITLE", bubble.title)
				it.putString("INTRO_MESSAGE", bubble.introMessage)
				it.putString("INPUT_PLACE_HOLDER", bubble.inputPlaceholder)
				it.putInt("MAX_MESSAGES", bubble.maxMessage)
				it.putBoolean("CLEAR_ON_CLOSE", bubble.clearOnClose)
				it.putBoolean("ENABLE_VOICE_RECORD", bubble.enableVoiceRecord)
			}
		}
	}
}