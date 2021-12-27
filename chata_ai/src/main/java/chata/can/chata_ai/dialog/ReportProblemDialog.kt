package chata.can.chata_ai.dialog

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

class ReportProblemDialog(
	context: Context,
	private val queryId: String,
	private val chatView: ChatContract.View?
): BaseDialog(context, R.layout.dialog_report_dialog, false), View.OnClickListener
{
	private lateinit var llParent: View
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var tvMessage: TextView
	private lateinit var etMessage: EditText
	private lateinit var iView1: View
	private lateinit var iView2: View
	private lateinit var btnReport: Button
	private lateinit var btnCancel: Button

	override fun setViews()
	{
		llParent = findViewById(R.id.llParent)
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		tvMessage = findViewById(R.id.tvMessage)
		etMessage = findViewById(R.id.etMessage)
		iView1 = findViewById(R.id.iView1)
		iView2 = findViewById(R.id.iView2)
		btnReport = findViewById(R.id.btnReport)
		btnCancel = findViewById(R.id.btnCancel)
	}

	override fun setColors()
	{
		context.run {
			ThemeColor.currentColor.run {
				llParent.setBackgroundColor(pDrawerBackgroundColor)
				tvTitle.setTextColor(pDrawerTextColorPrimary)
				tvMessage.setTextColor(pDrawerTextColorPrimary)
				etMessage.background = getBackgroundColor(pDrawerBackgroundColor, pDrawerBorderColor)
				etMessage.setTextColor(pDrawerTextColorPrimary)
				iView1.setBackgroundColor(pDrawerBorderColor)
				iView2.setBackgroundColor(pDrawerBorderColor)
				btnReport.background = getBackgroundColor(
					getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
				btnReport.setTextColor(pDrawerTextColorPrimary)
				btnCancel.background = getBackgroundColor(pDrawerBackgroundColor, pDrawerBorderColor)
				btnCancel.setTextColor(pDrawerTextColorPrimary)
			}
		}

		val title = "Report a Problem"
		tvTitle.text = title
		val msg = context.getString(R.string.tell_more_about)
		tvMessage.text = msg
		val ok = "Report"
		btnReport.text = ok
		val cancel = "Cancel"
		btnCancel.text = cancel

		ivCancel.setOnClickListener(this)
		btnCancel.setOnClickListener(this)
		btnReport.setOnClickListener(this)
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel, R.id.btnCancel -> dismiss()
				R.id.btnReport ->
				{
					val message = etMessage.text.toString()
					val feedbackMessage = context.getString(R.string.thank_you_feedback)
					WebViewPresenter(chatView, feedbackMessage).run {
						putReport(queryId, message)
						dismiss()
					}
				}
			}
		}
	}

	private fun getDesign(context: Context): LinearLayout
	{
		return LinearLayout(context).apply {
			id = R.id.llParent
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
			//region rlTitle
			addView(RelativeLayout(context).apply {
				id = R.id.rlTitle
				layoutParams = getLinearLayoutParams(-1, dpToPx(36f))
				//region tvTitle
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvTitle
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					setTypeface(typeface, Typeface.BOLD)
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				})
				//endregion
				//region ivCancel
				addView(ImageView(context).apply {
					id = R.id.ivCancel
					layoutParams = getRelativeLayoutParams(dpToPx(24f), dpToPx(24f)).apply {
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
				layoutParams = getLinearLayoutParams(-1, dpToPx(1f))
			})
			//endregion
			//region LinearLayout
			addView(LinearLayout(context).apply {
				layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				marginAll(16f)
				orientation = LinearLayout.VERTICAL
				//region tvMessage
				addView(TextView(context).apply {
					id = R.id.tvMessage
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				})
				//endregion
				//region etMessage
				addView(EditText(context).apply {
					gravity = Gravity.TOP or Gravity.START
					id = R.id.etMessage
					inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT
					setLines(2)
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
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
				layoutParams = getLinearLayoutParams(-1, dpToPx(1f))
			})
			//endregion
			addView(LinearLayout(context).apply {
				gravity = Gravity.END
				val params = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				params.gravity = Gravity.END
				layoutParams = params
				margin(16f)
				//region btnCancel
				addView(Button(context).apply {
					id = R.id.btnCancel
					layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					margin(end = 2f)
					isAllCaps = false
				})
				//endregion
				//region btnReport
				addView(Button(context).apply {
					id = R.id.btnReport
					layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					margin(end = 2f)
					isAllCaps = false
				})
				//endregion
			})
		}
	}

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}