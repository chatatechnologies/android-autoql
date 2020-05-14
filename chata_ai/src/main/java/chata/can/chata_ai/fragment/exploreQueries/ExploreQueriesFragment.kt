package chata.can.chata_ai.fragment.exploreQueries

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.drawable.EditTextDrawable
import chata.can.chata_ai.fragment.exploreQueries.adapter.ExploreQueriesAdapter
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.putArgs

class ExploreQueriesFragment: BaseFragment(), ExploreQueriesContract
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
	private var rvRelatedQueries: RecyclerView ?= null
	private val presenter = ExploreQueriesPresenter(this)
	private val model = BaseModelList<String>()
	private lateinit var adapter: ExploreQueriesAdapter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		initList()
		etQuery?.setOnEditorActionListener { _, _, _ ->
			etQuery?.run {
				val query = text.toString()
				setText("")
				hideKeyboard()
				presenter.validateQuery(query)
			}
			true
		}
	}

	override fun initViews(view: View)
	{
		view.run {
			etQuery = findViewById(R.id.etQuery)
			llQuery = findViewById(R.id.llQuery)
			rvRelatedQueries = findViewById(R.id.rvRelatedQueries)
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

	override fun getRelatedQueries(relatedQuery: RelatedQuery)
	{
		with(relatedQuery)
		{
			model.clear()
			model.addAll(aItems)
			adapter.notifyDataSetChanged()
		}
	}

	private fun initList()
	{
		activity?.let {
			adapter = ExploreQueriesAdapter(model)
			rvRelatedQueries?.layoutManager = LinearLayoutManager(it)
			rvRelatedQueries?.adapter = adapter
		}
	}
}