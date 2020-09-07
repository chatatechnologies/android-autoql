package chata.can.chata_ai.dialog

import android.view.View
import android.widget.PopupMenu
import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter

object ListPopup
{
	fun showListPopup(view: View, queryId: String)
	{
		val presenter = WebViewPresenter()
		PopupMenu(view.context, view).run {
			menuInflater.inflate(R.menu.popup_menu, menu)
			setOnMenuItemClickListener { item ->
				when(item.itemId)
				{
					R.id.iIncorrect, R.id.iIncomplete ->
					{
						presenter.putReport(queryId, item.title.toString())
					}
					R.id.iOther -> {}
					else -> {}
				}
				true
			}
			show()
		}
	}
}