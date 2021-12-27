package chata.can.chata_ai.dialog

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.*
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

class ReportProblemDialog(
	context: Context,
	private val queryId: String,
	private val chatView: ChatContract.View?
): BaseDialog(context,
	isFull = false,
	rootView = ReportProblemDesign.getDesign(context)
), View.OnClickListener
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
				btnReport.setTextColor(Color.WHITE)
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

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}