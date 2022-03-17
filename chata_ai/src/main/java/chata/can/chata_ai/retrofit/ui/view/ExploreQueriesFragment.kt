package chata.can.chata_ai.retrofit.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.databinding.FragmentExploreQueriesBinding
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.ui.viewModel.ExploreQueriesViewModel

class ExploreQueriesFragment: Fragment() {
	companion object {
		const val nameFragment = "Explore Queries"
	}

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
			//region on click listener
			ivSearch.setOnClickListener {
				if (AutoQLData.wasLoginIn) {
					val query = etQuery.text.toString()
					exploreQueriesViewModel?.validateQuery(query)
				}
			}
			//endregion

			if (BuildConfig.DEBUG) {
				etQuery.setText("revenue")
			}
		}
	}
}