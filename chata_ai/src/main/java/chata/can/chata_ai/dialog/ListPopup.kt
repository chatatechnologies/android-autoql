package chata.can.chata_ai.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.graphics.drawable.DrawableCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.hideColumn.HideDialog
import chata.can.chata_ai.dialog.listPopup.DataPopup
import chata.can.chata_ai.dialog.sql.DisplaySQLDialog
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase

object ListPopup
{
	fun showPointsPopup(
		view: View,
		query: String,
		dataPopup: DataPopup ?= null,
		queryBase: QueryBase ?= null,
		webViewView: WebViewContract ?= null)
	{
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.popupMenuStyle2
		else R.style.popupMenuStyle1
		val wrapper = ContextThemeWrapper(view.context, theme)

		PopupMenu(wrapper, view).run {
			menu?.run {
				dataPopup?.let {
					if (dataPopup.isTable)
					{
						if (!dataPopup.isDataPivot)
						{
							add(1, R.id.iColumn, 1, R.string.show_hide_column).setIcon(
								if (queryBase?.allVisible() == true) R.drawable.ic_eye_1 else R.drawable.ic_eye_2)
						}
						if (queryBase?.aRows?.count() != 1)
						{
							add(2, R.id.iFilterTable, 2, R.string.filter_table).setIcon(R.drawable.ic_filter)
						}
					}
					add(3, R.id.iReportProblem, 3, R.string.report_problem).setIcon(R.drawable.ic_report_m)
					add(4, R.id.iDelete, 4, R.string.delete_response).setIcon(R.drawable.ic_delete_m)
				}
				add(5, R.id.iGenerateSQL, 5, R.string.view_generated_sql).setIcon(R.drawable.ic_database)
			}
			insertMenuItemIcons(view.context, this)
			setOnMenuItemClickListener { item ->
				dataPopup?.run {
					when(item.itemId)
					{
						R.id.iColumn ->
						{
							HideColumnsDialog.showHideColumnsDialog(view.context)
							//HideDialog(view.context, queryBase).show()
						}
						R.id.iFilterTable ->
						{
							webViewView?.showFilter()
						}
						R.id.iReportProblem ->
						{
							showListPopup(viewRoot, queryId, chatView)
						}
						R.id.iDelete ->
						{
							adapterView?.deleteQuery(adapterPosition)
						}
						R.id.iGenerateSQL ->
						{
							DisplaySQLDialog(view.context, query).show()
						}
						else -> {}
					}
				} ?: run {
					DisplaySQLDialog(view.context, query).show()
				}
				true
			}
			show()
		}
	}

	fun showListPopup(
		view: View,
		queryId: String,
		chatView: ChatContract.View?)
	{
		val presenter = WebViewPresenter(
			chatView, view.context.getString(R.string.thank_you_feedback))
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

	fun insertMenuItemIcons(context: Context, popupMenu: PopupMenu)
	{
		val menu = popupMenu.menu
		if (hasIcon(menu))
		{
			for (index in 0 until menu.size())
				insertMenuItemIcon(context, menu.getItem(index), index)
		}
	}

	private fun hasIcon(menu: Menu): Boolean
	{
		for (index in 0 until menu.size())
		{
			if (menu.getItem(index).icon != null) return true
		}
		return false
	}

	private fun insertMenuItemIcon(context: Context, menuItem: MenuItem, index: Int = -1)
	{
		var icon = menuItem.icon
		if (icon == null)
			icon = ColorDrawable(Color.TRANSPARENT)
		else
		{
			if (index != 0)
			{
				val drawableWrap = DrawableCompat.wrap(icon).mutate()
				DrawableCompat.setTint(drawableWrap, SinglentonDrawer.currentAccent)
			}
		}
		val iconSize = context.resources.getDimensionPixelSize(R.dimen.menu_item_icon_size)
		icon.setBounds(0, 0, iconSize, iconSize)
		val imageSpan = ImageSpan(icon)

		val ssb = SpannableStringBuilder("    " + menuItem.title)
		ssb.setSpan(imageSpan, 0, 1, 0)
		menuItem.title = ssb

		menuItem.icon = null
	}
}