package chata.can.chata_ai.retrofit.ui.view.exploreQuery

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import chata.can.chata_ai.databinding.FragmentExploreQueriesBinding
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.data.model.ExploreQueriesProvider
import chata.can.chata_ai.retrofit.ui.viewModel.ExploreQueriesViewModel

class ExploreQueriesFragment: Fragment() {
	companion object {
		const val nameFragment = "Explore Queries"
	}

	private lateinit var adapter: ExploreQueriesAdapter
	private var exploreQueriesViewModel: ExploreQueriesViewModel ?= null
	private var fragmentExploreQueryBinding: FragmentExploreQueriesBinding ?= null

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
				etQuery.setText("revenue")
//				etQuery.setText("hi")
			}
		}
		initListener()
		initRecycler()
		initObserve()
	}

	private fun initObserve() {
		exploreQueriesViewModel?.run {
			itemList.observe(viewLifecycleOwner) { listItems ->
				if (listItems.isNotEmpty()) {
					//show list
					visibleRecycler()
					val listProvider = ExploreQueriesProvider.itemList
					listProvider.clear()
					listProvider.addAll(listItems)
					adapter.notifyItemRangeChanged(0, listProvider.size)
				} else {
					// show message
					fragmentExploreQueryBinding?. run {
						tvMsg1.setText(R.string.empty_data_explore_queries)
					}
				}
			}

			relatedQueryPagination.observe(viewLifecycleOwner) { relatedQueryPagination ->
				ExploreQueriesProvider.pagination = relatedQueryPagination

				relatedQueryPagination.run {
					fragmentExploreQueryBinding?.run {
						llPager.visibility = View.VISIBLE
						tvFirstPage.text = "1"
						if (totalPages >= currentPage) {
							tvLastPage.text = "$totalPages"
						}

						when(currentPage) {
							1, totalPages -> {

								tvCenterPage.text = "..."
							}
							else -> {
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

	private fun visibleRecycler() {
		fragmentExploreQueryBinding?.run {
			rvRelatedQueries.visibility = View.VISIBLE
		}
	}

	private fun initRecycler() {
		adapter = ExploreQueriesAdapter(ExploreQueriesProvider.itemList) { item ->
			//call service
			println("item to request $item")
		}
		fragmentExploreQueryBinding?.run {
			//rvRelatedQueries.visibility = View.VISIBLE
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
		}
	}


}