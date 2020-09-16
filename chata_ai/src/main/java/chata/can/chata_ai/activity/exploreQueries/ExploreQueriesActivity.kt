package chata.can.chata_ai.activity.exploreQueries

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.activity.exploreQueries.adapter.ExploreQueriesAdapter
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.base.BaseActivity
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

	private lateinit var rvPager: View
	private lateinit var tvPrevious: TextView
	private lateinit var tvCenterPage: TextView
	private lateinit var tvFirstPage: TextView
	private lateinit var tvSecondPage: TextView
	private lateinit var tvThirdPage: TextView
	private lateinit var tvBreakPage1: TextView
	private lateinit var tvBreakPage2: TextView
	private lateinit var tvFifthPage: TextView
	private lateinit var tvPenultimatePage: TextView
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
		rvPager = findViewById(R.id.rvPager)
		tvPrevious = findViewById(R.id.tvPrevious)
		tvCenterPage = findViewById(R.id.tvCenterPage)
		tvFirstPage = findViewById(R.id.tvFirstPage)
		tvSecondPage = findViewById(R.id.tvSecondPage)
		tvThirdPage = findViewById(R.id.tvThirdPage)
		tvBreakPage1 = findViewById(R.id.tvBreakPage1)
		tvBreakPage2 = findViewById(R.id.tvBreakPage2)
		tvFifthPage = findViewById(R.id.tvFifthPage)
		tvPenultimatePage = findViewById(R.id.tvPenultimatePage)
		tvLastPage = findViewById(R.id.tvLastPage)
		tvNext = findViewById(R.id.tvNext)
	}

	private fun initListener()
	{
		ivCancel.setOnClickListener(this)

		tvPrevious.setOnClickListener(this)
		tvFirstPage.setOnClickListener(this)
		tvSecondPage.setOnClickListener(this)
		tvThirdPage.setOnClickListener(this)
		tvBreakPage1.setOnClickListener(this)
		tvBreakPage2.setOnClickListener(this)
		tvFifthPage.setOnClickListener(this)
		tvCenterPage.setOnClickListener(this)
		tvPenultimatePage.setOnClickListener(this)
		tvLastPage.setOnClickListener(this)
		tvNext.setOnClickListener(this)

		etQuery.setOnEditorActionListener { _, _, _ ->
			etQuery.run {
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

	private fun setColors()
	{
		ivClear.visibility = View.GONE
		tvToolbar.text = exploreQueriesTile
		etQuery.backgroundGrayWhite(64f,1)

		val color = getParsedColor(R.color.chata_drawer_accent_color)
		ivSearch.background = DrawableBuilder.setOvalDrawable(color)
//		llQuery.backgroundGrayWhite(64f,1)
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel -> { finish() }
				R.id.tvPrevious -> {}
				R.id.tvFirstPage -> {}
				R.id.tvSecondPage -> {}
				R.id.tvThirdPage -> {}
				R.id.tvCenterPage -> {}
				R.id.tvFirstPage -> {}
				R.id.tvPenultimatePage -> {}
				R.id.tvLastPage -> {}
				R.id.tvNext -> {}
			}
		}
	}

	override fun getRelatedQueries(relatedQuery: RelatedQuery)
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

	private fun configPager(relatedQuery: RelatedQuery)
	{
		rvPager.visibility = View.VISIBLE
		currentPage = relatedQuery.currentPage
		hideItemOnPager()

		when(relatedQuery.totalPages)
		{
			1 -> tvFirstPage.visibility = visible
			2 ->
			{
				tvFirstPage.visibility = visible
				tvSecondPage.visibility = visible
			}
			3 ->
			{
				tvFirstPage.visibility = visible
				tvSecondPage.visibility = visible
				tvLastPage.visibility = visible
			}
			4 ->
			{
				tvFirstPage.visibility = visible
				tvSecondPage.visibility = visible
				tvPenultimatePage.visibility = visible
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
		tvSecondPage.visibility = gone
		tvCenterPage.visibility = gone
		tvPenultimatePage.visibility = gone
		tvLastPage.visibility = gone
	}
}