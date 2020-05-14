package chata.can.chata_ai.fragment.exploreQueries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.nullValue

class ExploreQueriesAdapter(model: BaseModelList<*>) : BaseAdapter(model)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return QueryHolder(layoutInflater.inflate(R.layout.row_explore_queries, nullValue))
	}
}