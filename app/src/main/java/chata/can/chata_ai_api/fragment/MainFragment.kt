package chata.can.chata_ai_api.fragment

import androidx.fragment.app.Fragment
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.putArgs

class MainFragment: Fragment()
{
	companion object {
		//		const val nameClass = "DashboardFragment"
		fun newInstance() = MainFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_main)
		}
	}
}