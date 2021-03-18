package chata.can.chata_ai_api.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import chata.can.chata_ai_api.fragment.main.MainFragment
import chata.can.chata_ai_api.fragment.dashboard.DashboardFragment

class SlidePagerAdapter(fm: FragmentManager, var numPages: Int)
	: FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
	override fun getCount() = numPages

	override fun getItem(position: Int): Fragment
	{
		return when(position)
		{
			0 -> MainFragment.newInstance()
			else -> DashboardFragment.newInstance()
//			else -> InputOutputFragment.newInstance()
		}
	}

	override fun getPageTitle(position: Int): CharSequence
	{
		return when(position)
		{
			0 -> MainFragment.nameFragment
			else -> DashboardFragment.nameFragment
//			else -> InputOutputFragment.nameFragment
		}
	}
}