package chata.can.chata_ai.fragment

import android.view.View
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.putArgs

class NotificationFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "Notifications"
		fun newInstance() = NotificationFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_notifications)
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