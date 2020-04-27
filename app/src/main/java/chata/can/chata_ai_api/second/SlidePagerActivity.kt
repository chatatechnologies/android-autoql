package chata.can.chata_ai_api.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai_api.R

class SlidePagerActivity: FragmentActivity()
{
	private lateinit var viewPager: ViewPager
	private val numPages = 2

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.pager_activity)
		viewPager = findViewById(R.id.viewPager)

		val adapter = SlidePagerAdapter(supportFragmentManager)
		viewPager.adapter = adapter
	}

	override fun onBackPressed()
	{
		if (viewPager.currentItem == 0)
		{
			super.onBackPressed()
		}
		else
		{
			viewPager.currentItem = viewPager.currentItem - 1
		}
	}

	private inner class SlidePagerAdapter(fm: FragmentManager)
		: FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
	{
		override fun getCount() = numPages

		override fun getItem(position: Int): Fragment = DashboardFragment()
	}
}