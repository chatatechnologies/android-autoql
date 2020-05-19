package chata.can.chata_ai.activity.pager

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.fragment.exploreQueries.ExploreQueriesFragment

class SlidePagerAdapter(fm: FragmentManager, private val numPages: Int)
	: FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
	private val registeredFragment = SparseArray<BaseFragment>()

	override fun getCount() = numPages

	override fun getItem(position: Int): Fragment
	{
		return if (position == 0)
			DataMessengerFragment.newInstance()
		else
			ExploreQueriesFragment.newInstance()
	}

	override fun instantiateItem(container: ViewGroup, position: Int): Any
	{
		val fragment = super.instantiateItem(container, position) as BaseFragment
		registeredFragment.put(position, fragment)
		return fragment
	}

	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
	{
		registeredFragment.remove(position)
		super.destroyItem(container, position, `object`)
	}

	fun getRegisteredFragment(position: Int) =
		if (registeredFragment.size() < position) null
		else registeredFragment[position]
}