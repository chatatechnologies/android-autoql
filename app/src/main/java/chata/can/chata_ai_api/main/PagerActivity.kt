package chata.can.chata_ai_api.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.activity.dm.DMActivity
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.dm.FloatingView
import chata.can.chata_ai_api.R
import com.google.android.material.tabs.TabLayout

class PagerActivity: BaseActivity(R.layout.pager_activity)
{
	private lateinit var llParent: RelativeLayout
	private lateinit var viewPager: ViewPager
	private lateinit var tabLayout: TabLayout
	private lateinit var adapter: SlidePagerAdapter
	private lateinit var floatingView: FloatingView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		ThemeColor.parseColor(this)
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView()
	{
		llParent = findViewById(R.id.llParent)
		viewPager = findViewById(R.id.viewPager)
		tabLayout = findViewById(R.id.tabLayout)
		floatingView = findViewById(R.id.floatingView)

		tabLayout.setupWithViewPager(viewPager)

		resources?.let {
			it.displayMetrics?.let { itMetrics ->
				ScreenData.densityByDP = itMetrics.density
			}
		}

		adapter = SlidePagerAdapter(supportFragmentManager, 1)
		viewPager.adapter = adapter
		RequestBuilder.initVolleyRequest(this)

		floatingView.setEventClick {
			startActivity(
				Intent(this, DMActivity::class.java).apply {
					putExtra("PLACEMENT", floatingView.placement)
				}
			)
			val pData = when(floatingView.placement)
			{
				ConstantDrawer.LEFT_PLACEMENT -> Pair(R.anim.from_left_in, R.anim.from_right_out)
				ConstantDrawer.RIGHT_PLACEMENT -> Pair(R.anim.from_right_in, R.anim.from_left_out)
				else -> Pair(0, 0)
			}
			overridePendingTransition(pData.first, pData.second)
//			pagerOption = PagerOptions(this).apply {
//				fragmentManager = supportFragmentManager
//				setStatusGUI()
//				paintViews(floatingView.placement)
//			}
//			llParent.addView(pagerOption)
//			pagerOption?.fragmentManager = supportFragmentManager
//			supportFragmentManager
		}
	}

	override fun onBackPressed()
	{
//		if (pagerOption.isVisible)
//		{
//			BubbleHandle.isOpenChat = false
//			BubbleHandle.instance?.isVisible = true
//			pagerOption.setStatusGUI(false)
//		}
//		else
//		{
//			if (supportFragmentManager.backStackEntryCount > 0)
//				finishAffinity()
//			else
//			//minimize App
//				moveTaskToBack(true)
//		}
	}

//	fun setStatusGUI(isVisible: Boolean, bubbleData: BubbleData ?= null)
//	{
//		pagerOption.bubbleData = bubbleData
//		hideKeyboard()
//		pagerOption.setStatusGUI(isVisible)
//		pagerOption.paintViews()
//	}

//	fun clearDataMessenger(bubbleData: BubbleData ?= null)
//	{
//		pagerOption.bubbleData = bubbleData
//		pagerOption.fragmentManager?.findFragmentByTag(DataMessengerFragment.nameFragment)?.let {
//			if (it is DataMessengerFragment)
//			{
//				pagerOption.bubbleData?.let { bubble ->
//					val argument = Bundle().apply {
//						putString("CUSTOMER_NAME", bubble.customerName)
//						putString("TITLE", bubble.title)
//						putString("INTRO_MESSAGE", bubble.introMessage)
//						putString("INPUT_PLACE_HOLDER", bubble.inputPlaceholder)
//						putInt("MAX_MESSAGES", bubble.maxMessage)
//						putBoolean("CLEAR_ON_CLOSE", bubble.clearOnClose)
//						putBoolean("ENABLE_VOICE_RECORD", bubble.enableVoiceRecord)
//					}
//					it.updateData(argument)
//				}
//				it.clearQueriesAndResponses()
//			}
//		}
//	}

	var isVisibleTabLayout: Boolean = true
	set(value) {
		val visible = if (value) {
//			adapter.numPages = 3
			adapter.numPages = 2
			View.VISIBLE
		} else {
			adapter.numPages = 1
			View.GONE
		}
		adapter.notifyDataSetChanged()
		tabLayout.visibility = visible
		field = value
	}
}