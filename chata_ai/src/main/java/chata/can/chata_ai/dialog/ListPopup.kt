package chata.can.chata_ai.dialog

import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import chata.can.chata_ai.R

object ListPopup
{
	fun showListPopup(view: View)
	{
		PopupMenu(view.context, view).run {
			menuInflater.inflate(R.menu.popup_menu, menu)
			setOnMenuItemClickListener { item ->
				Toast.makeText(view.context, "", Toast.LENGTH_SHORT).show()
				true
			}
			show()
		}
	}
}