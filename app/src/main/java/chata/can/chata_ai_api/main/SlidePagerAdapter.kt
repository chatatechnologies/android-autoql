package chata.can.chata_ai_api.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import chata.can.chata_ai_api.fragment.main.MainFragment
import chata.can.chata_ai_api.fragment.dashboard.DashboardFragment

import androidx.viewpager2.adapter.FragmentStateAdapter

class SlidePagerAdapter(fa: FragmentActivity, var numPages: Int)
	: FragmentStateAdapter(fa)
{
	override fun getItemCount() = numPages

	override fun createFragment(position: Int): Fragment {
		return when(position)
		{
			0 -> MainFragment.newInstance()
			else -> DashboardFragment.newInstance()
		}
	}
}