package chata.can.chata_ai.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
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
import chata.can.chata_ai.extension.whenAllNotNull
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
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf1
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf2
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf3
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignOf4
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent1
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent2
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent3
import chata.can.chata_ai.view.pagerOption.PagerOptionConst.alignParent4
import chata.can.chata_ai.view.resize.SplitViewConst
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.max
import kotlin.math.min

class PagerOptions: RelativeLayout, View.OnClickListener, View.OnTouchListener, StatusResponse
{
	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyle: Int)
		: super(context, attrs, defStyle) { init() }

	private lateinit var rlMain: View
	private lateinit var llMenu: LinearLayout
	private lateinit var vHandle: View
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

	private var mDraggingStarted = 0L
	private var mDragStartX = 0f
	private var mDragStartY = 0f
	private var mPointerOffset = 0f
	private var limitPrimary = 48f
	private var limitSecondary = 432f
	var isVisible = false

	override fun onClick(view: View?)
	{
		view?.let { _view ->
			when(_view.id)
			{
				R.id.llMenu, R.id.ivClose ->
				{
					BubbleHandle.isOpenChat = false
					BubbleHandle.instance?.isVisible = true
					if (bubbleData?.clearOnClose == true)
					{
						val model = SinglentonDrawer.mModel
						while (model.countData() > 2)
						{
							model.removeAt(model.countData() - 1)
						}
					}
					setStatusGUI(false)

					context?.getSystemService(Activity.INPUT_METHOD_SERVICE)?.let {
						(it as? InputMethodManager)?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
					}
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

	override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean
	{
		view?.let {
			if (it != vHandle) return false
		}
		when(motionEvent.action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				mDraggingStarted = SystemClock.elapsedRealtime()
				mDragStartX = motionEvent.x
				mDragStartY = motionEvent.y
				mPointerOffset = motionEvent.rawX - getPrimaryContentSize()
			}
			MotionEvent.ACTION_UP ->
			{
				if (
					mDragStartX < (motionEvent.x + SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					mDragStartX > (motionEvent.x - SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					mDragStartY < (motionEvent.y + SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					mDragStartY > (motionEvent.y - SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					((SystemClock.elapsedRealtime() - mDraggingStarted) < SplitViewConst.SINGLE_TAP_MAX_TIME)
				)
				{
					if (isPrimaryContentMaximized() || isSecondaryContentMaximized())
						setPrimaryContentSize(getPrimaryContentSize())
					else
						maximizeSecondaryContent()
				}
			}
			MotionEvent.ACTION_MOVE ->
			{
				setPrimaryContentWidth((motionEvent.rawX - mPointerOffset).toInt())
			}
		}
		return true
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
			rlMain = findViewById(R.id.rlMain)
			llMenu = findViewById(R.id.llMenu)
			rlChat = findViewById(R.id.rlChat)
			vHandle = findViewById(R.id.vHandle)
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

		ExploreQueriesFragment.dataMessengerMethod = { openChat() }

		DataMessengerFragment.exploreQueriesMethod = { openTips() }

		ThemeColor.aColorMethods["PagerOptions"] = {
			setColors()
		}

		addView(view)
	}

	fun paintViews()
	{
		when(val placement = BubbleHandle.instance?.placement)
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
				//region vHandle
				vHandle.layoutParams = (vHandle.layoutParams as? LayoutParams)?.apply {
					height = -1
					width = dpToPx(12f)
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
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
				//region vHandle
				vHandle.layoutParams = (vHandle.layoutParams as? LayoutParams)?.apply {
					height = dpToPx(12f)
					width = -1
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

	fun setStatusGUI(isVisible: Boolean)
	{
		this.isVisible = isVisible
		val iVisible = if (isVisible)
		{
			var nameFragment = ""
			bubbleData?.let { bubble ->
				if (bubble.isDataMessenger)
				{
					fragmentManager?.findFragmentByTag(DataMessengerFragment.nameFragment)?.let {
						if (it is DataMessengerFragment)
						{
							if (rlSelected?.id == R.id.rlChat)
							{
								bubbleData?.let { bubble ->
									val argument = Bundle().apply {
										putString("CUSTOMER_NAME", bubble.customerName)
										putString("TITLE", bubble.title)
										putString("INTRO_MESSAGE", bubble.introMessage)
										putString("INPUT_PLACE_HOLDER", bubble.inputPlaceholder)
										putInt("MAX_MESSAGES", bubble.maxMessage)
										putBoolean("CLEAR_ON_CLOSE", bubble.clearOnClose)
										putBoolean("ENABLE_VOICE_RECORD", bubble.enableVoiceRecord)
									}
									it.updateData(argument)
								}
							}
							else
							{
								openChat()
							}
						}
					} ?: run {
						openChat()
					}
				}
				else
				{
					openTips()
				}
			}

			context?.let {
				val animationTop = AnimationUtils.loadAnimation(it, R.anim.scale)
				startAnimation(animationTop)
			}
			updateTitle()
			context.run {
				bubbleData?.let {
					llMenu.setBackgroundColor(
						getParsedColor(
							if (it.isDarkenBackgroundBehind)
								R.color.darken_background_behind
							else
								R.color.transparent
						)
					)
				}
			}
			View.VISIBLE
		}
		else View.GONE

		bubbleData?.let {
			rlTips.visibility = if (it.visibleExploreQueries) View.VISIBLE else View.GONE
			rlNotify.visibility = if (it.visibleNotification) View.VISIBLE else View.GONE
		}

		llMenu.visibility = iVisible
		vHandle.visibility = iVisible
		rlLocal.visibility = iVisible
	}

	private fun openChat()
	{
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
		vHandle.setOnTouchListener(this)
	}

	private fun setColors()
	{
		context?.run {
			rlChat.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
			rlTips.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
			rlNotify.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
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
			rlSelected?.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
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

	private fun getPrimaryContentSize() = llMenu.measuredWidth

	private fun isPrimaryContentMaximized() =
		(rlLocal.measuredWidth < SplitViewConst.MAXIMIZED_VIEW_TOLERANCE_DIP)

	private fun isSecondaryContentMaximized() =
		(llMenu.measuredWidth < SplitViewConst.MAXIMIZED_VIEW_TOLERANCE_DIP)

	private fun setPrimaryContentSize(newSize: Int): Boolean
	{
		return setPrimaryContentWidth(newSize)
	}

	private fun maximizeSecondaryContent()
	{
		arrayListOf(rlLocal, llMenu).whenAllNotNull {
			maximizeContentPane(it[0], it[1])
		}
	}

	private fun maximizeContentPane(toMaximize: View, toUnMaximize: View)
	{
		val params = toUnMaximize.layoutParams as RelativeLayout.LayoutParams
		val secondParams = toMaximize.layoutParams as RelativeLayout.LayoutParams

		params.width = 1

		toUnMaximize.layoutParams = params
		toMaximize.layoutParams = secondParams
	}

	private fun setPrimaryContentWidth(newWidth: Int): Boolean
	{
		var newWidth1 = max(0, newWidth)
		newWidth1 = min(newWidth1, rlMain.measuredWidth - vHandle.measuredWidth)
		val params = llMenu.layoutParams as RelativeLayout.LayoutParams
		if (rlLocal.measuredWidth < 1 && newWidth1 > params.width) return false

		if (newWidth1 >= 0 && newWidth1 > dpToPx(limitPrimary) && newWidth1 < limitSecondary)
		{
			val leftMargin = newWidth1 - llMenu.measuredWidth
			params.leftMargin = if (leftMargin < 6) 0 else leftMargin
		}
		//TODO make method
		//unMinimizeSecondaryContent()
		llMenu.layoutParams = params
		return true
	}
}