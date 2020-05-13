package chata.can.chata_ai.activity.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

class PagerActivity: AppCompatActivity()
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
//		tabLayout.visibility = View.VISIBLE
	}

	override fun finish()
	{
		super.finish()
		BubbleHandle.isOpenChat = false
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	override fun onDestroy()
	{
		super.onDestroy()
		BubbleHandle.instance.isVisible = true
//		if (clearOnClose)
//		{
//			SinglentonDrawer.mModel.clear()
//		}
	}
}