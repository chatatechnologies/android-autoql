package chata.can.chata_ai.activity.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.R
import com.google.android.material.tabs.TabLayout

class PagerActivity: AppCompatActivity()
{
	private lateinit var viewPager: ViewPager
	private lateinit var tabLayout: TabLayout
	private val numPages = 2

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.pager_activity)
		viewPager = findViewById(R.id.viewPager)
		tabLayout = findViewById(R.id.tabLayout)

		tabLayout.setupWithViewPager(viewPager)
		initSlideAdapter()
	}

	private fun initSlideAdapter()
	{
		val adapter = SlidePagerAdapter(supportFragmentManager, numPages)
		viewPager.adapter = adapter
	}
}