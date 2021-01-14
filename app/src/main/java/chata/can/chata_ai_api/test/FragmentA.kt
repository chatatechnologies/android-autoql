package chata.can.chata_ai_api.test

import android.view.View
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.R

class FragmentA: BaseFragment()
{
	companion object {
		fun newInstance() = FragmentA().putArgs {
			putInt("LAYOUT", R.layout.fragment_fragment_a)
		}
	}

	override fun setColors()
	{

	}

	override fun initViews(view: View)
	{

	}

	override fun initListener()
	{

	}
}