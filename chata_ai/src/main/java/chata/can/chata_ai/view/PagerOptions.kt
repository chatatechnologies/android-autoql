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
import chata.can.chata_ai.addFragment
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerData
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesFragment
import chata.can.chata_ai.fragment.notification.NotificationFragment
import chata.can.chata_ai.model.BubbleData
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.request.Poll
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf1
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf2
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf3
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf4
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent1
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent2
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent3
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent4
import org.json.JSONArray
import org.json.JSONObject

class PagerOptions: RelativeLayout, View.OnClickListener, StatusResponse//, View.OnTouchListener
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
	private lateinit var toolbar: View
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
					parent?.let {
						if (it is RelativeLayout)
						{
							it.removeView(this)
							if (DataMessengerData.clearOnClose)
							{
								val model = SinglentonDrawer.mModel
								while (model.countData() > 2)
								{
									model.removeAt(model.countData() - 1)
								}
							}
						}
					}
//					BubbleHandle.isOpenChat = false
//					BubbleHandle.instance?.isVisible = true
//					if (bubbleData?.clearOnClose == true)
//					{
//						val model = SinglentonDrawer.mModel
//						while (model.countData() > 2)
//						{
//							model.removeAt(model.countData() - 1)
//						}
//					}
//					setStatusGUI()
				}
				R.id.rlChat, R.id.rlTips, R.id.rlNotify ->
				{
					rlSelected?.let {
						if (it.id != _view.id)
						{
							when(_view.id)
							{
								R.id.rlChat -> openChat()
								R.id.rlTips -> openTips()
								R.id.rlNotify ->
								{
									changeColor(rlNotify, ivNotify)
									updateTitle()
									setVisibleDelete(false)
									showNotification()
									fragment = NotificationFragment.newInstance()
									fragmentManager?.let { fragmentManager -> addFragment(fragmentManager, fragment) }
								}
								else -> {}
							}
						}
					}
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
			toolbar = findViewById(R.id.toolbar)
			ivClose = findViewById(R.id.ivClose)
			tvTitle = findViewById(R.id.tvTitle)
			ivClear = findViewById(R.id.ivClear)
		}

		rlSelected = rlChat
		ivSelected = ivChat
		setListener()
		setColors()

		ExploreQueriesFragment.dataMessengerMethod = { openChat() }

		DataMessengerFragment.exploreQueriesMethod = { openTips() }

		ThemeColor.aColorMethods["PagerOptions"] = {
			setColors()
		}

		addView(view)
	}

	fun paintViews(placement: Int)
	{
//		when(val placement = BubbleHandle.instance?.placement)
		when(placement)
		{
			ConstantDrawer.LEFT_PLACEMENT, ConstantDrawer.RIGHT_PLACEMENT ->
			{
				//region llMenu
				llMenu.layoutParams = (llMenu.layoutParams as? LayoutParams)?.apply {
					height = -1
					width = dpToPx(48f)
					llMenu.orientation = LinearLayout.VERTICAL
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
						removeRules(alignParent1)
						addRule(ALIGN_PARENT_END, TRUE)
					}
					else
					{
						removeRules(alignParent2)
						addRule(ALIGN_PARENT_START, TRUE)
					}
				}
				//endregion
				//region little views
				(ivChat.layoutParams as? LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				ivChat.paddingAll(left = 6f, right = 6f)
				(ivTips.layoutParams as? LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				ivTips.paddingAll(left = 6f, right = 6f)
				(ivNotify.layoutParams as? LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				ivNotify.paddingAll(left = 6f, right = 6f)
				//endregion
				//region rlLocal
				rlLocal.layoutParams = (rlLocal.layoutParams as? LayoutParams)?.apply {
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
						removeRules(alignOf1)
						addRule(START_OF, R.id.llMenu)
					}
					else
					{
						removeRules(alignOf2)
						addRule(END_OF, R.id.llMenu)
					}
				}
				//endregion
			}
			ConstantDrawer.BOTTOM_PLACEMENT, ConstantDrawer.TOP_PLACEMENT ->
			{
				//region llMenu
				llMenu.layoutParams = (llMenu.layoutParams as? LayoutParams)?.apply {
					height = dpToPx(48f)
					width = -1
					llMenu.orientation = LinearLayout.HORIZONTAL
					if (placement == ConstantDrawer.BOTTOM_PLACEMENT)
					{
						removeRules(alignParent3)
						addRule(ALIGN_PARENT_TOP, TRUE)
					}
					else
					{
						removeRules(alignParent4)
						addRule(ALIGN_PARENT_BOTTOM, TRUE)
					}
				}
				//endregion
				//region little views
				(ivChat.layoutParams as? LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				ivChat.paddingAll(top = 6f, bottom = 6f)
				(ivTips.layoutParams as? LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				ivTips.paddingAll(top = 6f, bottom = 6f)
				(ivNotify.layoutParams as? LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				ivNotify.paddingAll(top = 6f, bottom = 6f)
				//endregion
				//region rlLocal
				rlLocal.layoutParams = (rlLocal.layoutParams as? LayoutParams)?.apply {
					if (placement == ConstantDrawer.BOTTOM_PLACEMENT)
					{
						removeRules(alignOf3)
						addRule(BELOW, R.id.llMenu)
					}
					else
					{
						removeRules(alignOf4)
						addRule(ABOVE, R.id.llMenu)
					}
				}
				//endregion
			}
		}
	}

	fun setStatusGUI()
	{
		DataMessengerData.run {
			if (isDataMessenger)
				openChat()
			else openTips()
		}
//		bubbleData?.let { bubble ->
//			if (bubble.isDataMessenger)
//			{
//				fragmentManager?.findFragmentByTag(DataMessengerFragment.nameFragment)?.let {
//					if (it is DataMessengerFragment)
//					{
//						if (rlSelected?.id == R.id.rlChat)
//						{
//							bubbleData?.let { bubble ->
//								val argument = Bundle().apply {
//									putString("CUSTOMER_NAME", bubble.customerName)
//									putString("TITLE", bubble.title)
//									putString("INTRO_MESSAGE", bubble.introMessage)
//									putString("INPUT_PLACE_HOLDER", bubble.inputPlaceholder)
//									putInt("MAX_MESSAGES", bubble.maxMessages)
//									putBoolean("CLEAR_ON_CLOSE", bubble.clearOnClose)
//									putBoolean("ENABLE_VOICE_RECORD", bubble.enableVoiceRecord)
//								}
//								setColors()
//								it.updateData(argument)
//							}
//						}
//						else
//						{
//							openChat()
//						}
//					}
//				} ?: run {
//					openChat()
//				}
//				openChat()
//			}
//			else
//			{
//				openTips()
//			}
//		}

		//region animation
		context?.let {
			val animationTop = AnimationUtils.loadAnimation(it, R.anim.scale)
			startAnimation(animationTop)
		}
		//endregion
		updateTitle()

//		bubbleData?.let {
//			rlTips.visibility = if (it.visibleExploreQueries) View.VISIBLE else View.GONE
//			rlNotify.visibility = if (it.visibleNotification) View.VISIBLE else View.GONE
//		}
	}

	private fun openChat()
	{
		setColors()
		changeColor(rlChat, ivChat)
		updateTitle()
		setVisibleDelete(true)
		fragment = DataMessengerFragment.newInstance()
		setDataToDataMessenger()
		fragmentManager?.let { addFragment(it, fragment, DataMessengerFragment.nameFragment) }
	}

	private fun openTips()
	{
		changeColor(rlTips, ivTips)
		updateTitle()
		setVisibleDelete(false)
		fragment = ExploreQueriesFragment.newInstance()
		fragmentManager?.let { addFragment(it, fragment) }
	}

	private fun setVisibleDelete(bVisible: Boolean)
	{
		ivClear.visibility = if (bVisible) View.VISIBLE else View.GONE
	}

	fun showNotify(bVisible: Boolean)
	{
		tvNotification.visibility = if (bVisible) View.VISIBLE else View.GONE
	}

	fun onDestroy()
	{
		ThemeColor.aColorMethods.remove("PagerOptions")
	}

	private fun showNotification()
	{
		if (tvNotification.visibility == View.VISIBLE)
		{
			Poll.callShowNotification(this)
		}
	}

	private fun updateTitle()
	{
		val title = ivSelected?.let {
			when(it.id)
			{
				R.id.ivChat -> DataMessengerFragment.nameFragment
				R.id.ivTips -> ExploreQueriesFragment.nameFragment
				R.id.ivNotify -> NotificationFragment.nameFragment
				else -> DataMessengerFragment.nameFragment
			}
		} ?: run { DataMessengerFragment.nameFragment }

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
		//vHandle.setOnTouchListener(this)
	}

	private fun setColors()
	{
		context?.run {
			val accentColor = SinglentonDrawer.currentAccent
			toolbar.setBackgroundColor(accentColor)
			rlChat.setBackgroundColor(accentColor)
			rlTips.setBackgroundColor(accentColor)
			rlNotify.setBackgroundColor(accentColor)
			rlSelected?.setBackgroundColor(ThemeColor.currentColor.pDrawerColorSecondary)

			ivChat.setColorFilter(getParsedColor(R.color.white))
			ivTips.setColorFilter(getParsedColor(R.color.white))
			ivNotify.setColorFilter(getParsedColor(R.color.white))
			ivSelected?.setColorFilter(ThemeColor.currentColor.pDrawerTextColorPrimary)
			tvNotification.background = DrawableBuilder.setOvalDrawable(
				getParsedColor(R.color.red_notification))
			ivClear.setColorFilter(getParsedColor(R.color.white))
		}
	}

	private fun changeColor(rlNew: View, ivNew: ImageView)
	{
		context?.run {
			val accentColor = SinglentonDrawer.currentAccent
			rlSelected?.setBackgroundColor(accentColor)
			ivSelected?.setColorFilter(getParsedColor(R.color.white))
			rlNew.setBackgroundColor(ThemeColor.currentColor.pDrawerColorSecondary)
			ivNew.setColorFilter(ThemeColor.currentColor.pDrawerTextColorPrimary)
			rlSelected = rlNew
			ivSelected = ivNew
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
			DataMessengerData.run {
				it.putString("CUSTOMER_NAME", customerName)
				it.putString("TITLE", title)
				it.putString("INTRO_MESSAGE", introMessage)
				it.putString("INPUT_PLACE_HOLDER", inputPlaceholder)
				it.putInt("MAX_MESSAGES", maxMessages)
				it.putBoolean("CLEAR_ON_CLOSE", clearOnClose)
				it.putBoolean("ENABLE_VOICE_RECORD", enableVoiceRecord)
			}
			//TODO clear after
			//bubbleData?.let {  }
		}
	}
}