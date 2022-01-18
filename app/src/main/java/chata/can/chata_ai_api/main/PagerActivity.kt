package chata.can.chata_ai_api.main

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.activity.dm.DMActivity
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setOnTabSelectedListener
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.dm.AutoQL
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.databinding.PagerActivityBinding
import com.google.android.material.tabs.TabLayoutMediator

class PagerActivity: AppCompatActivity()
{
	private lateinit var adapter: SlidePagerAdapter
	private lateinit var floatingView: AutoQL
	private val aDrawable = arrayListOf(
		Triple(R.drawable.ic_tab_data, "Data Messenger", R.color.colorButton),
		Triple(R.drawable.ic_tab_dashboard, "Dashboard", R.color.black))

	private lateinit var binding: PagerActivityBinding

	override fun onCreate(savedInstanceState: Bundle?)
	{
		ThemeColor.parseColor(this)
		super.onCreate(savedInstanceState)

		binding = PagerActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)

		floatingView = AutoQL(this).apply {
			id = R.id.floatingView
			layoutParams = FrameLayout.LayoutParams(-1, -1)
		}
		floatingView.setDataMessenger(
			"API_KEY",
			"DOMAIN_URL",
			"TOKEN",
			"PROJECT_ID")
		binding.run {
			llParent.addView(floatingView)

			resources?.let {
				it.displayMetrics?.let { itMetrics ->
					ScreenData.densityByDP = itMetrics.density
				}
			}

			adapter = SlidePagerAdapter(this@PagerActivity, 1)
			viewPager.adapter = adapter

			TabLayoutMediator(tabLayout, viewPager) { tab, position ->
				val pData = aDrawable[position]
				tab.setIcon(pData.first)
				tab.text = pData.second
				tab.icon?.setColorFilter(getParsedColor(pData.third))
			}.attach()

			tabLayout.run {
				setSelectedTabIndicatorColor(getParsedColor(R.color.colorButton))
				setTabTextColors(
					getParsedColor(R.color.black), getParsedColor(R.color.colorButton))

				setOnTabSelectedListener({ tab ->
					tab?.icon?.setColorFilter(getParsedColor(R.color.colorButton))
				}, { tab ->
					tab?.icon?.setColorFilter(getParsedColor(R.color.black))
				})
				//region remove long click for each item
				(getChildAt(0) as? LinearLayout)?.let { tabStrip ->
					for (index in 0 until tabStrip.childCount)
					{
						tabStrip.getChildAt(index).setOnLongClickListener {
							true
						}
					}
				}
				//endregion
			}
		}

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

	var isVisibleTabLayout: Boolean = true
	set(value) {
		val visible = if (value) {
			adapter.numPages = 2
			View.VISIBLE
		} else {
			adapter.numPages = 1
			View.GONE
		}
		adapter.notifyItemRangeChanged(0, adapter.numPages)
		binding.tabLayout.visibility = visible
		field = value
	}
}
