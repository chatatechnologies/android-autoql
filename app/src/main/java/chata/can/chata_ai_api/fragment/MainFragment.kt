package chata.can.chata_ai_api.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?): View?
	{
		return null
	}
}