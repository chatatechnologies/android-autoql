package chata.can.chata_ai.view.popup

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.pojo.SinglentonDrawer

object PopupMenu
{
	fun buildPopup(
		view: View,
		aConfig: List<Int>,
		sql: String = "")
	{
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.popupMenuStyle2
		else R.style.popupMenuStyle1
		val wrapper = ContextThemeWrapper(view.context, theme)

		PopupMenu(wrapper, view).run {
			menu?.run {
				val mData = PopupConfig.mIndexMethod
				for (index in aConfig)
				{
					val oPopup = mData[index]
					add(index + 1, oPopup.idMenu, index + 1, oPopup.iText).setIcon(oPopup.iDrawable)
				}
			}
			ListPopup.insertMenuItemIcons(view.context, this)
			setOnMenuItemClickListener { menu ->
				when(menu.itemId)
				{
					R.id.iGenerateSQL -> PopupConfig.generateSQL(view.context, sql)
				}
				true
			}
			show()
		}
	}
}