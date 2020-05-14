package chata.can.chata_ai.fragment

import android.view.View
import android.widget.EditText
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.drawable.EditTextDrawable
import chata.can.chata_ai.putArgs

class ExploreQueriesFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "Explore Queries"
		fun newInstance() = ExploreQueriesFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_explore_queries)
		}
	}

	private var etQuery: EditText ?= null

	override fun initViews(view: View)
	{
		view.run {
			etQuery = findViewById(R.id.etQuery)
		}
	}

	override fun initListener()
	{

	}

	override fun setColors()
	{
		activity?.let {
			etQuery?.background = EditTextDrawable.getQueryDrawable(it)
		}
	}
}