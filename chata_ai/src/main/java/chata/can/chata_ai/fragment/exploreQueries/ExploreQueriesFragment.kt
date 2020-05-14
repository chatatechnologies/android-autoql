package chata.can.chata_ai.fragment.exploreQueries

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
		fun newInstance() = ExploreQueriesFragment()
			.putArgs {
			putInt("LAYOUT", R.layout.fragment_explore_queries)
		}
	}

	private var llQuery: View ?= null
	private var etQuery: EditText ?= null
	private val presenter = ExploreQueriesPresenter()

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		etQuery?.setOnEditorActionListener {
			editText, actionId, event ->
			val query = editText.text.toString() ?: ""
			presenter.validateQuery(query)
			true
		}
	}

	override fun initViews(view: View)
	{
		view.run {
			etQuery = findViewById(R.id.etQuery)
			llQuery = findViewById(R.id.llQuery)
		}
	}

	override fun initListener()
	{

	}

	override fun setColors()
	{
		activity?.let {
			llQuery?.background = EditTextDrawable.getQueryDrawable(it)
		}
	}
}