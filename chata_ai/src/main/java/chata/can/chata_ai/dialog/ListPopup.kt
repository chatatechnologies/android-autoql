package chata.can.chata_ai.dialog

import android.view.View
import android.widget.PopupMenu
import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter

object ListPopup
{
	fun showListPopup(view: View, queryId: String, chatView: ChatContract.View?)
	{
		val presenter = WebViewPresenter(chatView)
		PopupMenu(view.context, view).run {
			menu?.run {
				add(1, R.id.iIncorrect, 1, R.string.the_data_is_incomplete)
				add(2, R.id.iIncorrect, 2, R.string.the_data_is_incomplete)
				add(3, R.id.iOther, 3, R.string.other)
			}
			//menuInflater.inflate(R.menu.popup_menu, menu)
			setOnMenuItemClickListener { item ->
				when(item.itemId)
				{
					R.id.iIncorrect, R.id.iIncomplete ->
					{
						presenter.putReport(queryId, item.title.toString())
					}
					R.id.iOther ->
					{
						ReportProblemDialog(view.context, queryId, chatView).show()
					}
					else -> {}
				}
				true
			}
			show()
		}
	}
}