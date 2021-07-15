package chata.can.chata_ai_api.main

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import chata.can.chata_ai.activity.dm.DMActivity
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setOnTabSelectedListener
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.dm.AutoQL
import chata.can.chata_ai_api.R
import com.google.android.material.tabs.TabLayout

class PagerActivity: BaseActivity(R.layout.pager_activity)
{
	private lateinit var llParent: RelativeLayout
	private lateinit var viewPager: ViewPager2
//	private lateinit var tabLayout: TabLayout
	private lateinit var adapter: SlidePagerAdapter
	private lateinit var floatingView: AutoQL
	private val aDrawable = arrayListOf(R.drawable.ic_tab_data, R.drawable.ic_tab_dashboard)

	override fun onCreate(savedInstanceState: Bundle?)
	{
		ThemeColor.parseColor(this)
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView()
	{
		llParent = findViewById(R.id.llParent)
		viewPager = findViewById(R.id.viewPager)
//		tabLayout = findViewById(R.id.tabLayout)
		floatingView = AutoQL(this).apply {
			id = R.id.floatingView
			layoutParams = FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
		}
		floatingView.setDataMessenger(
		"API_KEY",
		"DOMAIN_URL",
		"TOKEN",
		"PROJECT_ID")
		llParent.addView(floatingView)
//		tabLayout.setupWithViewPager(viewPager)

		resources?.let {
			it.displayMetrics?.let { itMetrics ->
				ScreenData.densityByDP = itMetrics.density
			}
		}

		adapter = SlidePagerAdapter(this, 1)
		viewPager.adapter = adapter
		RequestBuilder.initVolleyRequest(this)

		floatingView.setEventClick {
			startActivity(
				Intent(this, DMActivity::class.java).apply {
					putExtra("PLACEMENT", floatingView.placement)
				}
			)
		}
	}

	override fun onBackPressed()
	{
		finishAffinity()
	}

	@Suppress("deprecation")
	private fun Drawable.setColorFilter(color: Int)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
		{
			colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
		}
		else
		{
			setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
		}
	}

	private fun addTabLayoutEvent()
	{
//		tabLayout.run {
//			setSelectedTabIndicatorColor(getParsedColor(R.color.colorButton))
//			setTabTextColors(
//				getParsedColor(R.color.black), getParsedColor(R.color.colorButton))
//		}
//		for (index in 0 until adapter.numPages)
//		{
//			tabLayout.getTabAt(index)?.setIcon(aDrawable[index])
//			val color = if (index == 0) R.color.colorButton
//			else R.color.black
//			tabLayout.getTabAt(index)?.icon?.setColorFilter(getParsedColor(color))
//		}
//		tabLayout.setOnTabSelectedListener({ tab ->
//			tab?.icon?.setColorFilter(getParsedColor(R.color.colorButton))
//		}, { tab ->
//			tab?.icon?.setColorFilter(getParsedColor(R.color.black))
//		})
//		(tabLayout.getChildAt(0) as? LinearLayout)?.let { tabStrip ->
//			for (index in 0 until tabStrip.childCount)
//			{
//				tabStrip.getChildAt(index).setOnLongClickListener {
//					true
//				}
//			}
//		}
	}

	var isVisibleTabLayout: Boolean = true
	set(value) {
		val visible = if (value) {
			adapter.numPages = 2
			View.VISIBLE
		} else {
			adapter.numPages = 1
			View.GONE
		}
		adapter.notifyDataSetChanged()
		//tabLayout.visibility = visible
		Handler(Looper.getMainLooper()).postDelayed({
			addTabLayoutEvent()
		}, 500)
		field = value
	}
}
