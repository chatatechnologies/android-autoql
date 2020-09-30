package chata.can.chata_ai.fragment

import android.view.View
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.putArgs

class DataMessengerFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = DataMessengerFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_data_messenger)
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