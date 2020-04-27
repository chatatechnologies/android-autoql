package chata.can.chata_ai_api.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import chata.can.chata_ai_api.fragment.main.MainFragment
import chata.can.chata_ai_api.fragment.DashboardFragment

class SlidePagerAdapter (fm: FragmentManager, private val numPages: Int)
	: FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
	override fun getCount() = numPages

	override fun getItem(position: Int): Fragment
	{
		return if (position == 0)
			MainFragment.newInstance()
		else
			DashboardFragment.newInstance()
	}
}