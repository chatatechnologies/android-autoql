package chata.can.chata_ai.view.popup

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.dialog.sql.DisplaySQLDialog
import chata.can.chata_ai.pojo.SinglentonDrawer

object PopupMenu
{
	fun buildPopup(view: View, sql: String)
	{
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.popupMenuStyle2
		else R.style.popupMenuStyle1
		val wrapper = ContextThemeWrapper(view.context, theme)

		PopupMenu(wrapper, view).run {
			menu?.run {
				add(4, R.id.iGenerateSQL, 4, R.string.view_generated_sql).setIcon(R.drawable.ic_database)
			}
			ListPopup.insertMenuItemIcons(view.context, this)
			setOnMenuItemClickListener { menu ->
				when(menu.itemId)
				{
					R.id.iGenerateSQL -> DisplaySQLDialog(view.context, sql).show()
				}
				true
			}
			show()
		}
	}
}