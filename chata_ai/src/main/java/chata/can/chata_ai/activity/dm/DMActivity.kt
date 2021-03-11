package chata.can.chata_ai.activity.dm

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import chata.can.chata_ai.R
import chata.can.chata_ai.addFragment
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerData
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesFragment
import chata.can.chata_ai.fragment.notification.NotificationFragment
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.pagerOption.PagerOptionConst
import com.google.firebase.crashlytics.FirebaseCrashlytics

class DMActivity: AppCompatActivity(R.layout.view_pager_options), View.OnClickListener
{
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
	private lateinit var fragment: Fragment

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
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

		rlSelected = rlChat
		ivSelected = ivChat

		intent?.extras?.let {
			val placement = it.getInt("PLACEMENT", 0)
			FirebaseCrashlytics.getInstance().run {
				setCustomKey("PLACEMENT", placement)
			}
			paintViews(placement)
		}
		setColor()
		setListener()
		//Start first fragment
		openChat()
	}

	override fun onClick(view: View?)
	{
		view?.let { _view ->
			when(_view.id)
			{
				R.id.llMenu, R.id.ivClose -> closeActivity()
				R.id.rlChat, R.id.rlTips, R.id.rlNotify ->
				{
					if (rlSelected != null)
					{
						if (rlSelected!!.id != _view.id)
						{
							when(_view.id)
							{
								R.id.rlChat -> openChat()
								R.id.rlTips ->
								{
									changeColor(rlTips, ivTips)
								}
								R.id.rlNotify ->
								{
									changeColor(rlNotify, ivNotify)
								}
							}
						}
					}
				}
			}
		}
	}

	private fun setColor()
	{
		val accentColor = SinglentonDrawer.currentAccent
		val white = getParsedColor(R.color.white)

		toolbar.setBackgroundColor(accentColor)
		rlChat.setBackgroundColor(accentColor)
		rlTips.setBackgroundColor(accentColor)
		rlNotify.setBackgroundColor(accentColor)
		rlSelected?.setBackgroundColor(ThemeColor.currentColor.pDrawerColorSecondary)
		ivChat.setColorFilter(white)
		ivTips.setColorFilter(white)
		ivNotify.setColorFilter(white)
		ivSelected?.setColorFilter(ThemeColor.currentColor.pDrawerTextColorPrimary)
		tvNotification.background = DrawableBuilder.setOvalDrawable(
			getParsedColor(R.color.red_notification))

		llMenu.setBackgroundColor(getParsedColor(R.color.darken_background_behind))
		ivClose.setColorFilter(white)
		ivClear.setColorFilter(white)
	}

	private fun changeColor(rlNew: View, ivNew: ImageView)
	{
		val accentColor = SinglentonDrawer.currentAccent
		rlSelected?.setBackgroundColor(accentColor)
		ivSelected?.setColorFilter(getParsedColor(R.color.white))
		rlNew.setBackgroundColor(ThemeColor.currentColor.pDrawerColorSecondary)
		ivNew.setColorFilter(ThemeColor.currentColor.pDrawerTextColorPrimary)
		rlSelected = rlNew
		ivSelected = ivNew
	}

	private fun setListener()
	{
		llMenu.setOnClickListener(this)
		rlChat.setOnClickListener(this)
		rlTips.setOnClickListener(this)
		rlNotify.setOnClickListener(this)
		ivClose.setOnClickListener(this)
	}

	private fun openChat()
	{
		changeColor(rlChat, ivChat)
		updateTitle()
		fragment = DataMessengerFragment.newInstance()
		visibleClear(true)
		//region setDataToDataMessenger
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
		}
		//endregion
		addFragment(supportFragmentManager, fragment, DataMessengerFragment.nameFragment)
	}

	private fun openTips()
	{

	}

	private fun visibleClear(isVisible: Boolean)
	{
		ivClose.visibility = if (isVisible) View.VISIBLE else View.GONE
	}

	private fun paintViews(placement: Int)
	{
		when(placement)
		{
			ConstantDrawer.LEFT_PLACEMENT, ConstantDrawer.RIGHT_PLACEMENT ->
			{
				//region llMenu
				llMenu.layoutParams = (llMenu.layoutParams as? RelativeLayout.LayoutParams)?.apply {
					height = -1
					width = dpToPx(48f)
					llMenu.orientation = LinearLayout.VERTICAL
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
						removeRules(PagerOptionConst.alignParent1)
						addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
					}
					else
					{
						removeRules(PagerOptionConst.alignParent2)
						addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
					}
				}
				//endregion
				//region little views
				(ivChat.layoutParams as? RelativeLayout.LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				ivChat.paddingAll(left = 6f, right = 6f)
				(ivTips.layoutParams as? RelativeLayout.LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				ivTips.paddingAll(left = 6f, right = 6f)
				(ivNotify.layoutParams as? RelativeLayout.LayoutParams)?.run {
					height = dpToPx(56f)
					width = -1
				}
				ivNotify.paddingAll(left = 6f, right = 6f)
				//endregion
				//region rlLocal
				rlLocal.layoutParams = (rlLocal.layoutParams as? RelativeLayout.LayoutParams)?.apply {
					if (placement == ConstantDrawer.LEFT_PLACEMENT)
					{
						removeRules(PagerOptionConst.alignOf1)
						addRule(RelativeLayout.START_OF, R.id.llMenu)
					}
					else
					{
						removeRules(PagerOptionConst.alignOf2)
						addRule(RelativeLayout.END_OF, R.id.llMenu)
					}
				}
				//endregion
			}
			ConstantDrawer.BOTTOM_PLACEMENT, ConstantDrawer.TOP_PLACEMENT ->
			{
				//region llMenu
				llMenu.layoutParams = (llMenu.layoutParams as? RelativeLayout.LayoutParams)?.apply {
					height = dpToPx(48f)
					width = -1
					llMenu.orientation = LinearLayout.HORIZONTAL
					if (placement == ConstantDrawer.BOTTOM_PLACEMENT)
					{
						removeRules(PagerOptionConst.alignParent3)
						addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
					}
					else
					{
						removeRules(PagerOptionConst.alignParent4)
						addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
					}
				}
				//endregion
				//region little views
				(ivChat.layoutParams as? RelativeLayout.LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				ivChat.paddingAll(top = 6f, bottom = 6f)
				(ivTips.layoutParams as? RelativeLayout.LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				ivTips.paddingAll(top = 6f, bottom = 6f)
				(ivNotify.layoutParams as? RelativeLayout.LayoutParams)?.run {
					height = -1
					width = dpToPx(56f)
				}
				ivNotify.paddingAll(top = 6f, bottom = 6f)
				//endregion
				//region rlLocal
				rlLocal.layoutParams = (rlLocal.layoutParams as? RelativeLayout.LayoutParams)?.apply {
					if (placement == ConstantDrawer.BOTTOM_PLACEMENT)
					{
						removeRules(PagerOptionConst.alignOf3)
						addRule(RelativeLayout.BELOW, R.id.llMenu)
					}
					else
					{
						removeRules(PagerOptionConst.alignOf4)
						addRule(RelativeLayout.ABOVE, R.id.llMenu)
					}
				}
				//endregion
			}
		}
	}

	private fun RelativeLayout.LayoutParams.removeRules(aRules: ArrayList<Int>)
	{
		for (rule in aRules)
		{
			removeRule(rule)
		}
	}

	private fun updateTitle()
	{
		val nameDMF = DataMessengerFragment.nameFragment
		val title = ivSelected?.let {
			when(it.id)
			{
				R.id.ivChat -> nameDMF
				R.id.ivTips -> ExploreQueriesFragment.nameFragment
				R.id.ivNotify -> NotificationFragment.nameFragment
				else -> nameDMF
			}
		} ?: run { nameDMF }

		val tmp = if (nameDMF == title)
		{
			val localTitle = DataMessengerData.title
			if (localTitle.isNotEmpty()) localTitle
			else title
		}
		else title

		tvTitle.text = tmp
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out)
	}

	override fun onBackPressed() = closeActivity()

	private fun closeActivity()
	{
		if (DataMessengerData.clearOnClose)
		{
			val model = SinglentonDrawer.mModel
			while (model.countData() > 2)
			{
				model.removeAt(model.countData() - 1)
			}
		}
		finish()
	}
}