package chata.can.chata_ai.activity.exploreQueries

import android.content.Intent
import android.graphics.Color
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
import chata.can.chata_ai.listener.OnItemClickListener
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
	private lateinit var rlGif: View

	private lateinit var llPager: View
	private lateinit var tvPrevious: TextView
	private lateinit var tvCenterPage: TextView
	private lateinit var tvFirstPage: TextView
	private lateinit var tvLastPage: TextView
	private lateinit var tvNext: TextView
	private var tvSelected: TextView ?= null

	private val presenter = ExploreQueriesPresenter(this)
	private val model = BaseModelList<String>()
	private lateinit var adapter: ExploreQueriesAdapter
	private val visible = View.VISIBLE
	private val gone = View.GONE
	private var numItems = 0
	private var currentPage = 0
	private var pageSize = 0

	private var exploreQueriesTile = "Explore Queries"

	override fun onCreateView()
	{
		initViews()
		initListener()
		setColors()
		checkLastData()

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
		rlGif = findViewById(R.id.rlGif)
		llPager = findViewById(R.id.llPager)
		tvPrevious = findViewById(R.id.tvPrevious)
		tvCenterPage = findViewById(R.id.tvCenterPage)
		tvFirstPage = findViewById(R.id.tvFirstPage)
		tvLastPage = findViewById(R.id.tvLastPage)
		tvNext = findViewById(R.id.tvNext)

		adapter = ExploreQueriesAdapter(model, object : OnItemClickListener
		{
			override fun onItemClick(any: Any)
			{
				if (any is String)
				{
					setResult(RESULT_OK,
						Intent().apply {
							putExtra("query", any)
						})
					finish()
				}
			}
		})
		rvRelatedQueries.layoutManager = LinearLayoutManager(this)
		rvRelatedQueries.adapter = adapter
	}

	private fun initListener()
	{
		ivCancel.setOnClickListener(this)
		ivSearch.setOnClickListener(this)

		tvPrevious.setOnClickListener(this)
		tvFirstPage.setOnClickListener(this)
		tvLastPage.setOnClickListener(this)
		tvNext.setOnClickListener(this)

		etQuery.setOnEditorActionListener { _, _, _ ->
			setRequestText()
			true
		}
	}

	private fun setColors()
	{
		ivClear.visibility = gone
		tvToolbar.text = exploreQueriesTile
		etQuery.backgroundGrayWhite(64f,1)

		val blue = getBlue()
		ivSearch.background = DrawableBuilder.setOvalDrawable(blue)

		tvPrevious.setTextColor(blue)
		tvPrevious.text = "←"
		tvNext.setTextColor(blue)
		tvNext.text = "→"

		val black = getParsedColor(R.color.black)
		tvFirstPage.setTextColor(black)
		tvCenterPage.setTextColor(black)
		tvLastPage.setTextColor(black)

		setOval(tvFirstPage)
	}

	private fun checkLastData()
	{
		if (ExploreQueriesData.lastWord.isNotEmpty())
		{
			etQuery.setText(ExploreQueriesData.lastWord)
		}

		ExploreQueriesData.lastExploreQuery?.let {
			if (it.aItems.isNotEmpty())
			{
				rvRelatedQueries.visibility = visible
				model.clear()
				model.addAll(it.aItems)
				adapter.notifyDataSetChanged()
				configPager(it)
			}
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel -> finish()
				R.id.ivSearch -> setRequestText()
				R.id.tvPrevious ->
				{
					if (currentPage > 1)
					{
						presenter.getRelatedQueries(pageSize, currentPage - 1)
					}
				}
				R.id.tvFirstPage ->
				{
					if (currentPage != 1)
					{
						presenter.getRelatedQueries(pageSize, 1)
					}
				}
				R.id.tvLastPage ->
				{
					if (currentPage != numItems)
					{
						presenter.getRelatedQueries(pageSize, numItems)
					}
				}
				R.id.tvNext ->
				{
					if (currentPage < numItems)
					{
						presenter.getRelatedQueries(pageSize, currentPage + 1)
					}
				}
			}
		}
	}

	override fun getRelatedQueries(relatedQuery: ExploreQuery)
	{
		relatedQuery.run {
			ExploreQueriesData.lastExploreQuery = relatedQuery
			rvRelatedQueries.visibility = visible
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

	override fun showGif()
	{
		rlGif.visibility = visible
		rvRelatedQueries.visibility = gone
	}

	override fun showList()
	{
		rlGif.visibility = gone
		rvRelatedQueries.visibility = visible
	}

	override fun clearPage()
	{
		tvSelected?.let { removeOval(it) }
	}

	private fun setOval(tv: TextView)
	{
		tvSelected = tv
		tv.setTextColor(getParsedColor(R.color.white))
		tv.background = DrawableBuilder.setOvalDrawable(getBlue())
	}

	private fun removeOval(tv: TextView)
	{
		tv.setTextColor(getParsedColor(R.color.black))
		tv.setBackgroundColor(Color.TRANSPARENT)
	}

	private fun configPager(exploreQuery: ExploreQuery)
	{
		pageSize = exploreQuery.pageSize
		currentPage = exploreQuery.currentPage
		exploreQuery.run {
			llPager.visibility = visible
			tvFirstPage.text = "1"
			numItems = totalPages
			if (totalPages > currentPage)
			{
				tvLastPage.text = "$totalPages"
			}
			when(currentPage)
			{
				1, totalPages ->
				{
					tvSelected?.let { removeOval(it) }
					setOval(
						if (currentPage == 1) tvFirstPage
						else tvLastPage
					)
					tvCenterPage.text = "..."
				}
				else ->
				{
					tvSelected?.let { removeOval(it) }
					setOval(tvCenterPage)
					tvCenterPage.text = "$currentPage"
				}
			}
		}
	}

	private fun setRequestText()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			ExploreQueriesData.lastWord = query
			hideKeyboard()
			presenter.validateQuery(query)
		}
	}

	private fun getBlue() = getParsedColor(R.color.chata_drawer_accent_color)
}