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
	private var tvCenterPage: TextView ?= null
	private var tvFirstPage: TextView ?= null
	private var tvSecondPage: TextView ?= null
	private var tvPenultimatePage: TextView ?= null
	private var tvLastPage: TextView ?= null
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
				if (query.isNotEmpty())
				{
					setText("")
					hideKeyboard()
					presenter.validateQuery(query)
				}
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
			tvCenterPage = findViewById(R.id.tvCenterPage)
			tvFirstPage = findViewById(R.id.tvFirstPage)
			tvSecondPage = findViewById(R.id.tvSecondPage)
			tvPenultimatePage = findViewById(R.id.tvPenultimatePage)
			tvLastPage = findViewById(R.id.tvLastPage)
			tvNext = findViewById(R.id.tvNext)
		}
	}

	override fun initListener()
	{
		tvPrevious?.setOnClickListener(this)
		tvFirstPage?.setOnClickListener(this)
		tvSecondPage?.setOnClickListener(this)
		tvCenterPage?.setOnClickListener(this)
		tvPenultimatePage?.setOnClickListener(this)
		tvLastPage?.setOnClickListener(this)
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
				R.id.tvFirstPage -> {}
				R.id.tvSecondPage -> {}
				R.id.tvCenterPage -> {}
				R.id.tvPenultimatePage -> {}
				R.id.tvLastPage -> {}
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

		tvFirstPage?.text = "1"
		val lastPage = totalPages - 1
		if (lastPage != 1)
		{
			tvCenterPage?.text = "2"
		}
		tvLastPage?.text = "$totalPages"
	}
}