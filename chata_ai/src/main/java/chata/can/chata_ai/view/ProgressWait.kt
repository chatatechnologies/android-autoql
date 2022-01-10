package chata.can.chata_ai.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object ProgressWait
{
	private var dialog: AlertDialog ?= null

	private fun getDesign(context: Context): RelativeLayout {
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			setBackgroundColor(Color.TRANSPARENT)
			addView(TableRow(context).apply {
				setBackgroundColor(Color.WHITE)
				layoutParams = getRelativeLayoutParams(-2, dpToPx(64f)).apply {
					addRule(RelativeLayout.CENTER_IN_PARENT)
				}
				marginAll(30f)
				//region ProgressBar
				addView(ProgressBar(context, null, android.R.attr.progressBarStyle).apply {
					id = R.id.pb
					layoutParams = TableRow.LayoutParams(LayoutParams.WRAP_CONTENT_MATCH_PARENT)
					margin(10f, end = 10f)
				})
				//endregion
				//region TextView
				addView(TextView(context).apply {
					gravity = Gravity.CENTER or Gravity.START
					id = R.id.tv
					layoutParams = TableRow.LayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					setTextColor(Color.WHITE)
					textSize(18f)
					visibility = View.GONE
				})
				//endregion
			})
		}
	}

	fun showProgressDialog(context: Context, message: String): AlertDialog?
	{
		dialog?.dismiss()
		val dialogBuilder = AlertDialog.Builder(context)
		val dialogView = getDesign(context).apply {
			findViewById<TextView>(R.id.tv)?.run {
				text = message
			}
		}

		dialogBuilder.setView(dialogView)
		dialogBuilder.setCancelable(false)
		dialog = dialogBuilder.create().apply {
			window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
			show()
		}
		return dialog
	}

	fun hideProgressDialog()
	{
		dialog?.dismiss()
	}
}