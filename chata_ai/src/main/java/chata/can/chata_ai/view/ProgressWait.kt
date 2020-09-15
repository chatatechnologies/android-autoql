package chata.can.chata_ai.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.R


object ProgressWait
{
	private var dialog: AlertDialog ?= null

	fun showProgressDialog(context: Context, message: String): AlertDialog?
	{
		dialog?.dismiss()
		val dialogBuilder = AlertDialog.Builder(context)
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val dialogView = inflater.inflate(R.layout.dialog_progress, nullParent).apply {
			findViewById<TextView>(R.id.tv)?.run {
				text = message
			}
		}

		dialogBuilder.setView(dialogView)
		dialogBuilder.setCancelable(false)
		dialog = dialogBuilder.create().apply {
			show()
		}
		return dialog
	}

	fun hideProgressDialog()
	{
		dialog?.dismiss()
	}
}