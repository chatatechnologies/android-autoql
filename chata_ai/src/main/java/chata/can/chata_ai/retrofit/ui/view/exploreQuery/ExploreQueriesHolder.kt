package chata.can.chata_ai.retrofit.ui.view.exploreQuery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.databinding.CardExploreQueriesBinding
import chata.can.chata_ai.pojo.color.ThemeColor

class ExploreQueriesHolder(view: View): RecyclerView.ViewHolder(view) {

	private val binding = CardExploreQueriesBinding.bind(view)

	fun render(item: String, onClickListener: (String) -> Unit) {
		binding.tvQuery.run {
			setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
			text = item
			setOnClickListener {
				onClickListener(item)
			}
		}
	}
}