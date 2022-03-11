package chata.can.chata_ai.retrofit.core

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer

class ShowAlertDialogModel(
	val context: Context,
	val message: String,
	val positiveText: String,
	val negativeText: String,
	val positiveMethod: () -> Unit
)

class AlertCustomBuilder(context: Context) {
	private val theme = if (SinglentonDrawer.themeColor == "dark")
		R.style.AlertDialogCustom2
	else R.style.AlertDialogCustom1
	private val wrapper = ContextThemeWrapper(context, theme)
	private val builder = AlertDialog.Builder(wrapper)

	fun setMessage(message: String) {
		builder.setMessage(message)
	}

	fun setMessage(resMessage: Int) {
		builder.setMessage(resMessage)
	}

	fun setPositiveText(positiveText: String) {
		builder.setPositiveButton(positiveText, null)
	}

	fun setNeutralText(positiveText: String) {
		builder.setNeutralButton(positiveText, null)
	}

	fun setNegativeText(negativeText: String) {
		builder.setNegativeButton(negativeText, null)
	}

	fun show(): AlertDialog = builder.show()


}

fun updateAlertCustomBuilder(dialog: AlertDialog) {
	dialog.apply {
		val color = context.getParsedColor(
			if (SinglentonDrawer.themeColor == "dark") R.color.text_color_primary_2
			else R.color.text_color_primary_1)
		getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
		getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
	}
}

fun showAlertDialog(model: ShowAlertDialogModel) {
	model.run {
		val theme = if (SinglentonDrawer.themeColor == "dark")
			R.style.AlertDialogCustom2
		else R.style.AlertDialogCustom1
		val wrapper = ContextThemeWrapper(context, theme)
		val dialog = AlertDialog.Builder(wrapper)
			.setMessage(message)
			.setPositiveButton(positiveText) { _, _ ->
				positiveMethod()
			}
			.setNegativeButton(negativeText, null)
			.show()
		dialog.apply {
			val color = context.getParsedColor(
				if (SinglentonDrawer.themeColor == "dark") R.color.text_color_primary_2
				else R.color.text_color_primary_1)
			getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
			getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
		}
	}
}