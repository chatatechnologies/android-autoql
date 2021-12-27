package chata.can.chata_ai.dialog.reportProblem

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.view.container.LayoutParams

object ReportProblemDesign
{
	fun getDesign(context: Context): LinearLayout
	{
		return LinearLayout(context).apply {
			id = R.id.llParent
			layoutParams = LayoutParams.getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
			//region rlTitle
			addView(RelativeLayout(context).apply {
				id = R.id.rlTitle
				layoutParams = LayoutParams.getLinearLayoutParams(-1, dpToPx(36f))
				//region tvTitle
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvTitle
					layoutParams = LayoutParams.getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						.apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					setTypeface(typeface, Typeface.BOLD)
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				})
				//endregion
				//region ivCancel
				addView(ImageView(context).apply {
					id = R.id.ivCancel
					layoutParams = LayoutParams.getRelativeLayoutParams(dpToPx(24f), dpToPx(24f)).apply {
						addRule(RelativeLayout.CENTER_VERTICAL)
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					setImageResource(R.drawable.ic_cancel)
				})
				//endregion
			})
			//endregion
			//region iView1
			addView(View(context).apply {
				id = R.id.iView1
				layoutParams = LayoutParams.getLinearLayoutParams(-1, dpToPx(1f))
			})
			//endregion
			//region LinearLayout
			addView(LinearLayout(context).apply {
				layoutParams = LayoutParams.getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				marginAll(16f)
				orientation = LinearLayout.VERTICAL
				//region tvMessage
				addView(TextView(context).apply {
					id = R.id.tvMessage
					layoutParams = LayoutParams.getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				})
				//endregion
				//region etMessage
				addView(EditText(context).apply {
					gravity = Gravity.TOP or Gravity.START
					id = R.id.etMessage
					inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT
					setLines(2)
					layoutParams = LayoutParams.getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					margin(top = 8f)
					maxLines = 4
					minLines = 2
					paddingAll(4f)
					isVerticalScrollBarEnabled = true
					isHorizontalScrollBarEnabled = false
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
					{
						importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
					}
				})
				//endregion
			})
			//endregion
			//region iView2
			addView(View(context).apply {
				id = R.id.iView2
				layoutParams = LayoutParams.getLinearLayoutParams(-1, dpToPx(1f))
			})
			//endregion
			addView(LinearLayout(context).apply {
				gravity = Gravity.END
				val params = LayoutParams.getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				params.gravity = Gravity.END
				layoutParams = params
				paddingAll(16f)
				//region btnCancel
				addView(Button(context).apply {
					id = R.id.btnCancel
					layoutParams = LayoutParams.getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					margin(end = 2f)
					isAllCaps = false
				})
				//endregion
				//region btnReport
				addView(Button(context).apply {
					id = R.id.btnReport
					layoutParams = LayoutParams.getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					margin(end = 2f)
					isAllCaps = false
				})
				//endregion
			})
		}
	}
}