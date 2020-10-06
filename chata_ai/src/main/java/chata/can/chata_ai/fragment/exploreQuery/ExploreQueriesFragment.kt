package chata.can.chata_ai.fragment.exploreQuery

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.exploreQueries.ExploreQueriesContract
import chata.can.chata_ai.activity.exploreQueries.ExploreQueriesData
import chata.can.chata_ai.activity.exploreQueries.ExploreQueriesPresenter
import chata.can.chata_ai.activity.exploreQueries.adapter.ExploreQueriesAdapter
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.explore.ExploreQuery
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs

class ExploreQueriesFragment: BaseFragment(), ExploreQueriesContract, View.OnClickListener
{
	companion object {
		const val nameFragment = "Explore Queries"
		fun newInstance() = ExploreQueriesFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_explore_queries)
		}
		var dataMessengerMethod: (() -> Unit)? = null
	}

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
	private var tvSelected: TextView?= null

	private lateinit var adapter: ExploreQueriesAdapter
	private val presenter = ExploreQueriesPresenter(this)
	private val model = BaseModelList<String>()
	private val visible = View.VISIBLE
	private val gone = View.GONE
	private var numItems = 0
	private var currentPage = 0
	private var pageSize = 0

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		checkLastData()
	}

	override fun initListener()
	{
		ivSearch.setOnClickListener(this)

		tvPrevious.setOnClickListener(this)
		tvFirstPage.setOnClickListener(this)
		tvLastPage.setOnClickListener(this)
		tvNext.setOnClickListener(this)

		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					presenter.currentQuery = string
				}
			}
		})

		etQuery.setOnEditorActionListener { _, _, _ ->
			setRequestText()
			true
		}
	}

	override fun initViews(view: View)
	{
		with(view)
		{
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
						DataMessengerFragment.queryToTyping = any
						dataMessengerMethod?.let { it() }
					}
				}
			})
			activity?.let { rvRelatedQueries.layoutManager = LinearLayoutManager(it) }
			rvRelatedQueries.adapter = adapter
		}
	}

	override fun setColors()
	{
		etQuery.backgroundGrayWhite(64f,1)

		val blue = getBlue()
		ivSearch.background = DrawableBuilder.setOvalDrawable(blue)

		tvPrevious.setTextColor(blue)
		tvPrevious.text = "←"
		tvNext.setTextColor(blue)
		tvNext.text = "→"

		val black = getColor(R.color.black)
		tvFirstPage.setTextColor(black)
		tvCenterPage.setTextColor(black)
		tvLastPage.setTextColor(black)

		setOval(tvFirstPage)
	}

	override fun clearPage()
	{
		tvSelected?.let { removeOval(it) }
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

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
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

	private fun setOval(tv: TextView)
	{
		tvSelected = tv
		tv.setTextColor(getColor(R.color.white))
		tv.background = DrawableBuilder.setOvalDrawable(getBlue())
	}

	private fun removeOval(tv: TextView)
	{
		tv.setTextColor(getColor(R.color.black))
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
			if (totalPages >= currentPage)
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

	private fun getColor(intRes: Int): Int
	{
		return activity?.getParsedColor(intRes) ?: 0
	}

	private fun getBlue() = getColor(R.color.chata_drawer_accent_color)
}