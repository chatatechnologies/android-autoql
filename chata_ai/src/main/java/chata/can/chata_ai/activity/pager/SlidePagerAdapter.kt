package chata.can.chata_ai.activity.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import chata.can.chata_ai.fragment.DataMessengerFragment
import chata.can.chata_ai.fragment.ExploreQueriesFragment

class SlidePagerAdapter(fm: FragmentManager, private val numPages: Int)
	: FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
	override fun getCount() = numPages

	override fun getItem(position: Int): Fragment
	{
		return if (position == 0)

			DataMessengerFragment.newInstance()
		else
			ExploreQueriesFragment.newInstance()
	}

	override fun getPageTitle(position: Int): CharSequence?
	{
		return when(position)
		{
			0 -> DataMessengerFragment.nameFragment
			1 -> ExploreQueriesFragment.nameFragment
			else -> ""
		}
	}
}