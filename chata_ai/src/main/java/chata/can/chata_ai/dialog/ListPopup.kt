package chata.can.chata_ai.dialog

import android.view.View
import android.widget.PopupMenu
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.activity.dataMessenger.holder.webView.WebViewPresenter

object ListPopup
{
	fun showListPopup(view: View, queryId: String, chatView: ChatContract.View?)
	{
		val presenter = WebViewPresenter(chatView)
		PopupMenu(view.context, view).run {
			menuInflater.inflate(R.menu.popup_menu, menu)
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