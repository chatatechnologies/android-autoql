package chata.can.chata_ai.pojo.base

import androidx.viewpager.widget.ViewPager

interface PageSelectedListener: ViewPager.OnPageChangeListener
{
	fun onSelected(position: Int)

	override fun onPageScrollStateChanged(state: Int) {}

	override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

	override fun onPageSelected(position: Int)
	{
		onSelected(position)
	}
}