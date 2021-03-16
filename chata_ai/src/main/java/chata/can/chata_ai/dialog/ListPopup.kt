package chata.can.chata_ai.dialog

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.listPopup.DataPopup
import chata.can.chata_ai.dialog.sql.DisplaySQLDialog
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter
import chata.can.chata_ai.pojo.SinglentonDrawer

object ListPopup
{
	fun showPointsPopup(
		view: View,
		query: String,
		dataPopup: DataPopup ?= null)
	{
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.popupMenuStyle2
		else R.style.popupMenuStyle1
		val wrapper = ContextThemeWrapper(view.context, theme)

		PopupMenu(wrapper, view).run {
			menuInflater.inflate(R.menu.menu_complete, menu)
			show()
		}

//		PopupMenu(wrapper, view).run {
//			menu?.run {
//				if (dataPopup?.isReduce == true)
//				{
//					add(1, R.id.iReportProblem, 1, R.string.report_problem).setIcon(R.mipmap.ic_launcher_tmp)
//					add(2, R.id.iDelete, 2, R.string.delete_response).setIcon(R.mipmap.ic_launcher_tmp)
//				}
//				add(3, R.id.iGenerateSQL, 3, R.string.view_generated_sql).setIcon(R.mipmap.ic_launcher_tmp)
//			}
//			setOnMenuItemClickListener { item ->
//				dataPopup?.run {
//					when(item.itemId)
//					{
//						R.id.iReportProblem ->
//						{
//							showListPopup(viewRoot, queryId, chatView)
//						}
//						R.id.iDelete ->
//						{
//							adapterView?.deleteQuery(adapterPosition)
//						}
//						R.id.iGenerateSQL ->
//						{
//							DisplaySQLDialog(view.context, query).show()
//						}
//						else -> {}
//					}
//				} ?: run {
//					DisplaySQLDialog(view.context, query).show()
//				}
//				true
//			}
//			show()
//		}
	}

	fun showListPopup(
		view: View,
		queryId: String,
		chatView: ChatContract.View?)
	{
		val presenter = WebViewPresenter(chatView)
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.popupMenuStyle2
		else R.style.popupMenuStyle1
		val wrapper = ContextThemeWrapper(view.context, theme)
		PopupMenu(wrapper, view).run {
			menu?.run {
				add(1, R.id.iIncorrect, 1, R.string.the_data_is_incorrect)
				add(2, R.id.iIncomplete, 2, R.string.the_data_is_incomplete)
				add(3, R.id.iOther, 3, R.string.other)
			}
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