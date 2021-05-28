package chata.can.chata_ai.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll

object HideColumnsDialog
{
	fun showHideColumnsDialog(context: Context)
	{
		val builder = AlertDialog.Builder(context)
		val rlView = LinearLayout(context).apply {
			layoutParams = LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
			paddingAll(8f)
			//region RelativeLayout
			val rl = RelativeLayout(context).apply {
				setBackgroundColor(Color.RED)
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT)
				//region Title
				addView(TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					gravity = Gravity.CENTER
					text = context.getString(R.string.show_hide_column)
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
					setTypeface(typeface, Typeface.BOLD)
				})
				//endregion
				//region close dialog
				addView(ImageView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(dpToPx(24f), dpToPx(24f))
						.apply {
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
					setImageResource(R.drawable.ic_cancel)
				})
				//endregion
			}
			addView(rl)
			//endregion
			//region top menu
			val rlTop = RelativeLayout(context).apply {
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT)
				margin(12f, end = 12f)
				id = R.id.rlList
				addView(LinearLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_VERTICAL)
						addRule(RelativeLayout.START_OF, R.id.cbAll)
					}
					orientation = LinearLayout.HORIZONTAL
					addView(TextView(context).apply {
						layoutParams = LinearLayout.LayoutParams(
							0, LinearLayout.LayoutParams.WRAP_CONTENT
						).apply { weight = 1f }
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
						text = context.getString(R.string.column_name)
						id = R.id.tvColumnName
					})
					addView(TextView(context).apply {
						layoutParams = LinearLayout.LayoutParams(
							0, LinearLayout.LayoutParams.WRAP_CONTENT
						).apply { weight = 1f }
						gravity = Gravity.END
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
						text = context.getString(R.string.visibility)
						id = R.id.tvVisibility
					})
				})
				//all checkBox
				addView(CheckBox(ContextThemeWrapper(context, R.style.checkBoxStyle)).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT)
						.apply {
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
					id = R.id.cbAll
				})
				//endregion
			}
			addView(rlTop)
			//endregion
			//region bottom actions
			val llBottom = LinearLayout(context).apply {
				setBackgroundColor(Color.GREEN)
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT)
				orientation = LinearLayout.HORIZONTAL
				gravity = Gravity.END
				addView(Button(context).apply {
					setText(R.string.cancel)
					isAllCaps = false
				})
				addView(Button(context).apply {
					setText(R.string.apply)
					isAllCaps = false
				})
			}
			addView(llBottom)
			//endregion
		}
		builder.setView(rlView)
		builder.show()
	}
}