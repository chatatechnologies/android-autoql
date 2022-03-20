package chata.can.chata_ai.retrofit.ui.view.exploreQuery

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dm.DMActivity
import chata.can.chata_ai.databinding.FragmentExploreQueriesBinding
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccent
import chata.can.chata_ai.pojo.SinglentonDrawer.currentAccentDisable
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.data.model.ExploreQueriesProvider
import chata.can.chata_ai.retrofit.ui.viewModel.ExploreQueriesViewModel
import kotlin.math.abs
import kotlin.math.log10

class ExploreQueriesFragment: Fragment() {
	companion object {
		const val nameFragment = "Explore Queries"
	}

	private var _numItems = 0
	private var _currentPage = 0
	private var _pageSize = 0

	var mText: CharSequence = ""
	val mDelay = 50L
	var mIndex = 0
	private val mHandler = Handler(Looper.getMainLooper())

	private val characterAdder = object: Runnable {
		override fun run()
		{
			fragmentExploreQueryBinding?.etQuery?.setText(mText.subSequence(0, mIndex++))
			if (mIndex <= mText.length)
			{
				mHandler.postDelayed(this, mDelay)
			}
			else
			{
				mHandler.postDelayed({
					fragmentExploreQueryBinding?.ivSearch?.performClick()
				}, mDelay)
			}
		}
	}

	private lateinit var adapter: ExploreQueriesAdapter
	private var exploreQueriesViewModel: ExploreQueriesViewModel ?= null
	private var fragmentExploreQueryBinding: FragmentExploreQueriesBinding ?= null

	private var viewSelected: View ?= null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		fragmentExploreQueryBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_explore_queries,
			container,
			false
		)
		exploreQueriesViewModel = ViewModelProvider(this).get(ExploreQueriesViewModel::class.java)
		fragmentExploreQueryBinding?.model = exploreQueriesViewModel
		return fragmentExploreQueryBinding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		fragmentExploreQueryBinding?.run {
			if (BuildConfig.DEBUG) {
				val queryTest = "revenue"
//				val queryTest = "hi"
				etQuery.setText(queryTest)
			}
		}
		//region animated text
		val lastQuery = ExploreQueriesProvider.lastQuery
		if (lastQuery.isNotEmpty()) {
			if (ExploreQueriesProvider.textToSearchAnimated) {
				ExploreQueriesProvider.textToSearchAnimated = false

				mText = lastQuery
				mIndex = 0
				mHandler.removeCallbacks(characterAdder)
				mHandler.postDelayed(characterAdder, mDelay)
			} else {
				fragmentExploreQueryBinding?.etQuery?.setText(lastQuery)
			}
		}
		//endregion
		initColors()
		initListener()
		initRecycler()
		initObserve()

		exploreQueriesViewModel?.getItemsPersistent()
	}

	private fun initObserve() {
		exploreQueriesViewModel?.run {
			itemList.observe(viewLifecycleOwner) { listItems ->
				if (listItems.isNotEmpty()) {
					adapter.notifyItemRangeChanged(0, listItems.size)
				} else {
					// show message
					fragmentExploreQueryBinding?. run {
						tvMsg1.setText(R.string.empty_data_explore_queries)
					}
				}
			}

			isVisibleList.observe(viewLifecycleOwner) { isVisible ->
				fragmentExploreQueryBinding?.run {
					rvRelatedQueries.visibility = if (isVisible) View.VISIBLE else View.GONE
				}
			}

			relatedQueryPagination.observe(viewLifecycleOwner) { relatedQueryPagination ->
				ExploreQueriesProvider.pagination = relatedQueryPagination

				relatedQueryPagination.run {
					fragmentExploreQueryBinding?.run {
						llPager.visibility = View.VISIBLE

						_pageSize = pageSize
						_currentPage = currentPage
						_numItems = totalPages

						if (totalPages >= currentPage) {
							tvLastPage.text = "$totalPages"
						}

						when(currentPage) {
							1, totalPages -> {
								viewSelected?.setOvalBackground(false)
								viewSelected = if (currentPage == 1) {
									tvPrevious.setTextColor(currentAccentDisable())
									tvNext.setTextColor(currentAccent)
									tvFirstPage
								} else {
									tvPrevious.setTextColor(currentAccent)
									tvNext.setTextColor(currentAccentDisable())
									tvLastPage
								}
								viewSelected?.setOvalBackground(true, currentPage)
								tvCenterPage.text = "..."
							}
							else -> {
								tvPrevious.setTextColor(currentAccent)
								tvNext.setTextColor(currentAccent)

								viewSelected?.setOvalBackground(false)
								viewSelected = tvCenterPage
								tvCenterPage.setOvalBackground(true, currentPage)
								tvCenterPage.text = "$currentPage"
							}
						}
					}
				}
			}

			isVisibleGif.observe(viewLifecycleOwner) { isVisible ->
				fragmentExploreQueryBinding?. run {
					rlGif.visibility = if (isVisible) View.VISIBLE else View.GONE
				}
			}

			isVisibleMsg1.observe(viewLifecycleOwner) { isVisible ->
				fragmentExploreQueryBinding?. run {
					tvMsg1.visibility = if (isVisible) View.VISIBLE else View.GONE
				}
			}

			isVisibleMsg2.observe(viewLifecycleOwner) { isVisible ->
				fragmentExploreQueryBinding?. run {
					tvMsg2.visibility = if (isVisible) View.VISIBLE else View.GONE
				}
			}
		}
	}

	private fun initRecycler() {
		adapter = ExploreQueriesAdapter(ExploreQueriesProvider.itemList) { item ->
			//call service
			DataMessengerFragment.queryToTyping = item
			(requireActivity() as? DMActivity)?.openChat()
		}
		fragmentExploreQueryBinding?.run {
			val linearLayoutManager = LinearLayoutManager(requireActivity())

			rvRelatedQueries.layoutManager = linearLayoutManager
			rvRelatedQueries.adapter = adapter

			val decoration = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
			decoration.setDrawable(ColorDrawable(requireActivity().getParsedColor(android.R.color.darker_gray)))
			rvRelatedQueries.addItemDecoration(decoration)
			rvRelatedQueries.itemAnimator = null
		}
	}

	private fun initListener() {
		fragmentExploreQueryBinding?.run {
			ivSearch.setOnClickListener {
				if (AutoQLData.wasLoginIn) {
					val query = etQuery.text.toString()
					exploreQueriesViewModel?.validateQuery(query)
				}
			}

			tvPrevious.setOnClickListener {
				if (_currentPage > 1) {
					exploreQueriesViewModel?.relatedQuery(
						query = ExploreQueriesProvider.lastQuery,
						pageSize = _pageSize,
						page = _currentPage - 1
					)
				}
			}

			tvFirstPage.setOnClickListener {
				if (_currentPage != 1) {
					exploreQueriesViewModel?.relatedQuery(
						query = ExploreQueriesProvider.lastQuery,
						pageSize = _pageSize,
						page = 1
					)
				}
			}

			tvLastPage.setOnClickListener {
				if (_currentPage != _numItems) {
					exploreQueriesViewModel?.relatedQuery(
						query = ExploreQueriesProvider.lastQuery,
						pageSize = _pageSize,
						page = _numItems
					)
				}
			}

			tvNext.setOnClickListener {
				if (_currentPage < _numItems) {
					exploreQueriesViewModel?.relatedQuery(
						query = ExploreQueriesProvider.lastQuery,
						pageSize = _pageSize,
						page = _currentPage + 1
					)
				}
			}
		}
	}

	private fun initColors() {
		fragmentExploreQueryBinding?.run {
			ivSearch.setOvalBackground(true)
			tvFirstPage.setOvalBackground(true)
			viewSelected = tvFirstPage
		}
	}

	//region oval background
	private fun Int.length() = when(this) {
		0 -> 1
		else -> log10(abs(toDouble())).toInt() + 1
	}

	private fun View.setOvalBackground(applyBackground: Boolean, count: Int = 1) {
		if (applyBackground) {
			val gradientDrawable = DrawableBuilder.setOvalDrawable(SinglentonDrawer.currentAccent)
			val height = dpToPx(30f)
			val width = dpToPx(25f + (count.length() * 5))
			gradientDrawable.setSize(width, height)
			background = gradientDrawable
		} else {
			setBackgroundColor(Color.TRANSPARENT)
		}
	}
	//endregion
}