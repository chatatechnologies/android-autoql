package chata.can.chata_ai.dialog.manageData

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase

object ManageDataPopup
{
	fun showPlainOptions(
		view: View,
		queryBase: QueryBase)
	{
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.popupMenuStyle2
		else R.style.popupMenuStyle1
		val wrapper = ContextThemeWrapper(view.context, theme)

		PopupMenu(wrapper, view).run {
			menu?.run {
				for (column in queryBase.aColumn)
				{
					add(column.displayName)
				}
			}
			show()
		}
	}
}