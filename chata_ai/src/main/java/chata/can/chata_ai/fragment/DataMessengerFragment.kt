package chata.can.chata_ai.fragment

import android.view.View
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.putArgs

class DataMessengerFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = ExploreQueriesFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_date_messenger)
		}
	}

	override fun initViews(view: View)
	{

	}

	override fun initListener()
	{

	}

	override fun setColors()
	{

	}
}