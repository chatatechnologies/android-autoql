package chata.can.chata_ai.retrofit.core

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder as Builder
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer

class AlertCustomBuilder(context: Context) {
	private val theme = if (SinglentonDrawer.themeColor == "dark")
		R.style.AlertDialogCustom2
	else R.style.AlertDialogCustom1
	private val wrapper = ContextThemeWrapper(context, theme)
	private val builder = Builder(wrapper)

	@Suppress("unused")
	fun setMessage(message: String): AlertCustomBuilder {
		builder.setMessage(message)
		return this
	}

	@Suppress("unused")
	fun setMessage(resMessage: Int): AlertCustomBuilder {
		builder.setMessage(resMessage)
		return this
	}

	@Suppress("unused")
	fun setPositiveButton(
		positiveText: String,
		positiveMethod: () -> Unit = {}
	): AlertCustomBuilder {
		builder.setPositiveButton(positiveText) { _, _ ->
			positiveMethod()
		}
		return this
	}

	@Suppress("unused")
	fun setNeutralButton(
		positiveText: String,
		neutralMethod: () -> Unit = {}
	): AlertCustomBuilder {
		builder.setNeutralButton(positiveText) { _,_ ->
			neutralMethod()
		}
		return this
	}

	@Suppress("unused")
	fun setNegativeButton(
		negativeText: String,
		negativeMethod: () -> Unit = {}
	): AlertCustomBuilder {
		builder.setNegativeButton(negativeText) { _, _ ->
			negativeMethod()
		}
		return this
	}

	fun setOnDismissListener(dismissMethod: () -> Unit = {}): AlertCustomBuilder {
		builder.setOnDismissListener {
			dismissMethod()
		}
		return this
	}

	fun show(): AlertDialog {
		val dialog = builder.show()
		dialog.apply {
			val color = context.getParsedColor(
				if (SinglentonDrawer.themeColor == "dark") R.color.text_color_primary_2
				else R.color.text_color_primary_1)
			getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
			getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
			getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)
		}
		return dialog
	}
}