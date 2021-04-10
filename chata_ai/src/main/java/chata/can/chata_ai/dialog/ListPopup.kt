package chata.can.chata_ai.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.graphics.drawable.DrawableCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.hideColumn.HideDialog
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
			menu?.run {
				if (dataPopup?.isReduce == true)
				{
					add(1, R.id.iColumn, 1, R.string.show_hide_column).setIcon(R.drawable.ic_hide)
					add(2, R.id.iReportProblem, 2, R.string.report_problem).setIcon(R.drawable.ic_report)
					add(3, R.id.iDelete, 3, R.string.delete_response).setIcon(R.drawable.ic_delete)
				}
				add(4, R.id.iGenerateSQL, 4, R.string.view_generated_sql).setIcon(R.drawable.ic_database)
			}
			insertMenuItemIcons(view.context, this)
			setOnMenuItemClickListener { item ->
				dataPopup?.run {
					when(item.itemId)
					{
						R.id.iColumn ->
						{
							HideDialog(view.context).show()
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

	private fun insertMenuItemIcons(context: Context, popupMenu: PopupMenu)
	{
		val menu = popupMenu.menu
		if (hasIcon(menu))
		{
			for (index in 0 until menu.size())
				insertMenuItemIcon(context, menu.getItem(index))
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

	private fun insertMenuItemIcon(context: Context, menuItem: MenuItem)
	{
		var icon = menuItem.icon
		if (icon == null)
			icon = ColorDrawable(Color.TRANSPARENT)
		else
		{
			val drawableWrap = DrawableCompat.wrap(icon).mutate()
			DrawableCompat.setTint(drawableWrap, SinglentonDrawer.currentAccent)
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