package chata.can.chata_ai.fragment

import android.view.View
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.putArgs

class ExploreQueriesFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "Explore Queries"
		fun newInstance() = ExploreQueriesFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_explore_queries)
		}
	}

	override fun initListener()
	{

	}

	override fun initViews(view: View)
	{

	}

	override fun setColors()
	{

	}
}