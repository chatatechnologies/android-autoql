package chata.can.chata_ai_api.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai_api.R

class PagerActivity: FragmentActivity()
{
	private lateinit var viewPager: ViewPager
	private val numPages = 2

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.pager_activity)
		viewPager = findViewById(R.id.viewPager)

		val adapter = SlidePagerAdapter(supportFragmentManager, numPages)
		viewPager.adapter = adapter
	}
}