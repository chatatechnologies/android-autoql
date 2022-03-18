package chata.can.chata_ai.retrofit.ui.view.exploreQuery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R

class ExploreQueriesAdapter(
	private val itemList: List<String>,
	private val onClickListener: (String) -> Unit
	): RecyclerView.Adapter<ExploreQueriesHolder>() {

	override fun getItemCount(): Int = itemList.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreQueriesHolder {
		val inflater = LayoutInflater.from(parent.context)
		return ExploreQueriesHolder(
			inflater.inflate(R.layout.card_explore_queries, parent, false)
		)
	}

	override fun onBindViewHolder(holder: ExploreQueriesHolder, position: Int) {
		val item = itemList[position]
		holder.render(item, onClickListener)
	}
}