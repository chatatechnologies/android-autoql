package chata.can.chata_ai.fragment.exploreQuery

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dm.DMActivity
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.fragment.exploreQuery.adapter.ExploreQueriesAdapter
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.explore.ExploreQuery
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import kotlin.math.abs
import kotlin.math.log10

class ExploreQueriesFragment: BaseFragment(), ExploreQueriesContract, View.OnClickListener
{
	companion object {
		const val nameFragment = "Explore Queries"
	}

	private lateinit var llParent: LinearLayout
	private lateinit var llQuery: View
	private lateinit var etQuery: EditText
	private lateinit var ivSearch: ImageView
	private lateinit var rvRelatedQueries: RecyclerView
	private lateinit var rlGif: View
	private lateinit var tvMsg1: TextView
	private lateinit var tvMsg2: TextView
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

	override fun setView(inflater: LayoutInflater, container: ViewGroup?): View
	{
		val view = ExploreQueries.getDesign(requireActivity())
		onRenderViews(view)
		return view
	}

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		checkLastData()
		ThemeColor.aColorMethods[nameFragment] = {
			setColors()
		}
		if (BuildConfig.DEBUG)
		{
			val query = "revenue"
			etQuery.setText(query)
		}
	}

	override fun initListener()
	{
		ivSearch.setOnClickListener(this)

		tvPrevious.setOnClickListener(this)
		tvFirstPage.setOnClickListener(this)
		tvLastPage.setOnClickListener(this)
		tvNext.setOnClickListener(this)

		etQuery.setOnTextChanged { string ->
			if (string.isNotEmpty())
			{
				presenter.currentQuery = string
			}
		}

		etQuery.setOnEditorActionListener { _, _, _ ->
			setRequestText()
			true
		}
	}

	override fun initViews(view: View)
	{
		view.apply {
			llParent = findViewById(R.id.llParent)
			llQuery = findViewById(R.id.llQuery)
			etQuery = findViewById(R.id.etQuery)
			ivSearch = findViewById(R.id.ivSearch)
			rvRelatedQueries = findViewById(R.id.rvRelatedQueries)
			rlGif = findViewById(R.id.rlGif)
			tvMsg1 = findViewById(R.id.tvMsg1)
			tvMsg2 = findViewById(R.id.tvMsg2)
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
						(activity as? DMActivity)?.run {
							openChat()
						}
					}
				}
			})
			activity?.let { rvRelatedQueries.layoutManager = LinearLayoutManager(it) }
			rvRelatedQueries.adapter = adapter
			rvRelatedQueries.itemAnimator = null
		}
	}

	override fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			llParent.setBackgroundColor(getColor(drawerColorSecondary))
			etQuery.setHintTextColor(getColor(R.color.place_holder))

			val fill = getColor(drawerBackgroundColor)
			val border = getColor(drawerBorderColor)
			etQuery.background = DrawableBuilder.setGradientDrawable(fill,64f, 1, border)

			etQuery.setTextColor(getColor(drawerTextColorPrimary))

			tvMsg1.setTextColor(getColor(R.color.text_explore_queries))
			tvMsg2.setTextColor(getColor(R.color.text_explore_queries))

			val primary = getColor(drawerTextColorPrimary)
			tvFirstPage.setTextColor(primary)
			tvCenterPage.setTextColor(primary)
			tvLastPage.setTextColor(primary)
		}

		val blue = getBlue()
		ivSearch.background = DrawableBuilder.setOvalDrawable(blue)

		tvPrevious.setTextColor(blue)
		tvPrevious.text = "←"
		tvNext.setTextColor(blue)
		tvNext.text = "→"

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
			val iView = if (aItems.size > 0) View.GONE
			else View.VISIBLE
			tvMsg1.visibility = iView
			tvMsg2.visibility = iView
			adapter.notifyItemRangeChanged(0, model.countData())
			configPager(this)
		}
	}

	override fun showMessage()
	{
		hideGif()
		tvMsg1.setText(R.string.empty_data_explore_queries)
		tvMsg1.visibility = View.VISIBLE
		tvMsg2.visibility = View.GONE
		llPager.visibility = gone
	}

	override fun showGif()
	{
		rlGif.visibility = visible
		rvRelatedQueries.visibility = gone
	}

	override fun showList()
	{
		hideGif()
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

	override fun onDestroy()
	{
		super.onDestroy()
		ThemeColor.aColorMethods.remove(nameFragment)
	}

	private fun hideGif()
	{
		rlGif.visibility = gone
	}

	var mText: CharSequence = ""
	val mDelay = 50L
	var mIndex = 0
	private val mHandler = Handler(Looper.getMainLooper())

	private val characterAdder = object: Runnable {
		override fun run()
		{
			etQuery.setText(mText.subSequence(0, mIndex++))
			if (mIndex <= mText.length)
			{
				mHandler.postDelayed(this, mDelay)
			}
			else
			{
				mHandler.postDelayed({
					ivSearch.performClick()
				}, mDelay)
			}
		}
	}

	private fun checkLastData()
	{
		val lastWord = ExploreQueriesData.lastWord
		if (lastWord.isNotEmpty())
		{
			if (ExploreQueriesData.animated)
			{
				ExploreQueriesData.animated = false
				mText = lastWord
				mIndex = 0
				mHandler.removeCallbacks(characterAdder)
				mHandler.postDelayed(characterAdder, mDelay)
			}
			else
				etQuery.setText(lastWord)
		}

		ExploreQueriesData.lastExploreQuery?.let {
			if (it.aItems.isNotEmpty())
			{
				rvRelatedQueries.visibility = visible
				model.clear()
				model.addAll(it.aItems)
				adapter.notifyItemRangeChanged(0, model.countData())
				configPager(it)
			}
		}
	}

	private fun setOval(tv: TextView, count: Int = 1)
	{
		tvSelected = tv
		val gb = DrawableBuilder.setOvalDrawable(getBlue())
		val height = tv.dpToPx(30f)
		val width = tv.dpToPx(25f + (count.length() * 5))
		gb.setSize(width, height)
		tv.background = gb
	}

	private fun removeOval(tv: TextView)
	{
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
						else tvLastPage,
						currentPage
					)
					tvCenterPage.text = "..."
				}
				else ->
				{
					tvSelected?.let { removeOval(it) }
					setOval(tvCenterPage, currentPage)
					tvCenterPage.text = "$currentPage"
				}
			}
		}
	}

	private fun setRequestText()
	{
		if (AutoQLData.wasLoginIn)
		{
			val query = etQuery.text.toString()
			if (query.isNotEmpty())
			{
				ExploreQueriesData.lastWord = query
				hideKeyboard()
				presenter.validateQuery(query)
			}
		}
	}

	private fun getColor(intRes: Int): Int
	{
		return activity?.getParsedColor(intRes) ?: 0
	}

	private fun getBlue() = SinglentonDrawer.currentAccent

	private fun Int.length() = when(this) {
		0 -> 1
		else -> log10(abs(toDouble())).toInt() + 1
	}
}