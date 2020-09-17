package chata.can.chata_ai.activity.exploreQueries

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.activity.exploreQueries.adapter.ExploreQueriesAdapter
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.explore.ExploreQuery
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class ExploreQueriesActivity: BaseActivity(R.layout.activity_explore_queries),
	ExploreQueriesContract, View.OnClickListener
{
	private lateinit var tvToolbar: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var ivClear: ImageView

	private lateinit var llQuery: View
	private lateinit var etQuery: EditText
	private lateinit var ivSearch: ImageView
	private lateinit var rvRelatedQueries: RecyclerView

	private lateinit var llPager: View
	private lateinit var tvPrevious: TextView
	private lateinit var tvCenterPage: TextView
	private lateinit var tvFirstPage: TextView
	private lateinit var tvLastPage: TextView
	private lateinit var tvNext: TextView

	private val presenter = ExploreQueriesPresenter(this)
	private val model = BaseModelList<String>()
	private lateinit var adapter: ExploreQueriesAdapter
	private val visible = View.VISIBLE
	private val gone = View.GONE
	private var currentPage = 0

	private var exploreQueriesTile = "Explore Queries"

	override fun onCreateView()
	{
		initViews()
		initListener()
		setColors()

		if (BuildConfig.DEBUG)
		{
			etQuery.setText("Sales")
		}
	}

	private fun initViews()
	{
		tvToolbar = findViewById(R.id.tvToolbar)
		ivCancel = findViewById(R.id.ivCancel)
		ivClear = findViewById(R.id.ivClear)

		llQuery = findViewById(R.id.llQuery)
		etQuery = findViewById(R.id.etQuery)
		ivSearch = findViewById(R.id.ivSearch)
		rvRelatedQueries = findViewById(R.id.rvRelatedQueries)
		llPager = findViewById(R.id.llPager)
		tvPrevious = findViewById(R.id.tvPrevious)
		tvCenterPage = findViewById(R.id.tvCenterPage)
		tvFirstPage = findViewById(R.id.tvFirstPage)
		tvLastPage = findViewById(R.id.tvLastPage)
		tvNext = findViewById(R.id.tvNext)

		adapter = ExploreQueriesAdapter(model)
		rvRelatedQueries.layoutManager = LinearLayoutManager(this)
		rvRelatedQueries.adapter = adapter
	}

	private fun initListener()
	{
		ivCancel.setOnClickListener(this)
		ivSearch.setOnClickListener(this)

		tvPrevious.setOnClickListener(this)
		tvFirstPage.setOnClickListener(this)
		tvCenterPage.setOnClickListener(this)
		tvLastPage.setOnClickListener(this)
		tvNext.setOnClickListener(this)

		etQuery.setOnEditorActionListener { _, _, _ ->
			setRequestText()
			true
		}
	}

	private fun setColors()
	{
		ivClear.visibility = View.GONE
		tvToolbar.text = exploreQueriesTile
		etQuery.backgroundGrayWhite(64f,1)

		val color = getParsedColor(R.color.chata_drawer_accent_color)
		ivSearch.background = DrawableBuilder.setOvalDrawable(color)

		tvPrevious.text = "←"
		tvNext.text = "→"
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel -> finish()
				R.id.ivSearch -> setRequestText()
				R.id.tvPrevious -> {}
				R.id.tvFirstPage -> {}
				R.id.tvCenterPage -> {}
				R.id.tvLastPage -> {}
				R.id.tvNext -> {}
			}
		}
	}

	override fun getRelatedQueries(relatedQuery: ExploreQuery)
	{
		relatedQuery.run {
			rvRelatedQueries.visibility = View.VISIBLE
			model.clear()
			model.addAll(aItems)
			adapter.notifyDataSetChanged()
			configPager(this)
		}
	}

	override fun finish()
	{
		super.finish()
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	private fun configPager(relatedQuery: ExploreQuery)
	{
		llPager.visibility = View.VISIBLE
		currentPage = relatedQuery.currentPage
		hideItemOnPager()

		when(relatedQuery.totalPages)
		{
			1 -> tvFirstPage.visibility = visible
			2 ->
			{
				tvFirstPage.visibility = visible
			}
			3 ->
			{
				tvFirstPage.visibility = visible
				tvLastPage.visibility = visible
			}
			4 ->
			{
				tvFirstPage.visibility = visible
				tvLastPage.visibility = visible
			}
		}

//		when(currentPage)
//		{
//			1 ->
//			{
//				tvFirstPage?.visibility = visible
//			}
//			2 ->
//			{
//				tvSecondPage?.visibility = visible
//			}
//			3 ->
//			{
//				tvCenterPage?.visibility = visible
//			}
//			4 ->
//			{
//				tvCenterPage?.visibility = visible
//			}
//			5 ->
//			{
//				tvCenterPage?.visibility = visible
//			}
//			6 ->
//			{
//				tvCenterPage?.visibility = visible
//			}
//			7 ->
//			{
//				tvPenultimatePage?.visibility = visible
//			}
//			8 ->
//			{
//				tvLastPage?.visibility = visible
//			}
//		}

//
//		tvFirstPage?.text = "1"
//		val lastPage = totalPages - 1
//		if (lastPage != 1)
//		{
//			tvCenterPage?.text = "2"
//		}
//		tvLastPage?.text = "$totalPages"
	}

	private fun hideItemOnPager()
	{
		tvFirstPage.visibility = gone
		tvCenterPage.visibility = gone
		tvLastPage.visibility = gone
	}

	private fun setRequestText()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			etQuery.setText("")
			hideKeyboard()
			presenter.validateQuery(query)
		}
	}
}