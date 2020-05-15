package chata.can.chata_ai.fragment.exploreQueries

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.drawable.EditTextDrawable
import chata.can.chata_ai.fragment.exploreQueries.adapter.ExploreQueriesAdapter
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.putArgs

class ExploreQueriesFragment: BaseFragment(), View.OnClickListener, ExploreQueriesContract
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

	private var rvPager: View ?= null
	private var tvPrevious: TextView ?= null
	private var tvPageCenter: TextView ?= null
	private var tvPageStart: TextView ?= null
	private var tvPageEnd: TextView ?= null
	private var tvNext: TextView ?= null

	private val presenter = ExploreQueriesPresenter(this)
	private val model = BaseModelList<String>()
	private lateinit var adapter: ExploreQueriesAdapter
	private var currentPage = 0

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
			rvPager = findViewById(R.id.rvPager)
			tvPrevious = findViewById(R.id.tvPrevious)
			tvPageCenter = findViewById(R.id.tvPageCenter)
			tvPageStart = findViewById(R.id.tvPageStart)
			tvPageEnd = findViewById(R.id.tvPageEnd)
			tvNext = findViewById(R.id.tvNext)
		}
	}

	override fun initListener()
	{
		tvPrevious?.setOnClickListener(this)
		tvPageCenter?.setOnClickListener(this)
		tvPageStart?.setOnClickListener(this)
		tvPageEnd?.setOnClickListener(this)
		tvNext?.setOnClickListener(this)
	}

	override fun setColors()
	{
		activity?.let {
			llQuery?.background = EditTextDrawable.getQueryDrawable(it)
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.tvPrevious -> {}
				R.id.tvPageCenter -> {}
				R.id.tvPageStart -> {}
				R.id.tvPageEnd -> {}
				R.id.tvNext -> {}
			}
		}
	}

	override fun getRelatedQueries(relatedQuery: RelatedQuery)
	{
		with(relatedQuery)
		{
			rvRelatedQueries?.visibility = View.VISIBLE
			model.clear()
			model.addAll(aItems)
			adapter.notifyDataSetChanged()
			configPager(this)
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

	private fun configPager(relatedQuery: RelatedQuery)
	{
		rvPager?.visibility = View.VISIBLE
		currentPage = relatedQuery.currentPage

		val totalPages = relatedQuery.totalPages

		tvPageStart?.text = "1"
		val lastPage = totalPages - 1
		if (lastPage != 1)
		{
			tvPageCenter?.text = "2"
		}
		tvPageEnd?.text = "$totalPages"
	}
}