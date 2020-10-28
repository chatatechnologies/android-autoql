package chata.can.chata_ai.dialog

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewPresenter
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

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
			llParent.setBackgroundColor(getParsedColor(ThemeColor.currentColor.drawerBackgroundColor))
			tvTitle.setTextColor(getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary))
			tvMessage.setTextColor(getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary))
			etMessage.background = getBackgroundColor(
				getParsedColor(ThemeColor.currentColor.drawerBackgroundColor),
				getParsedColor(ThemeColor.currentColor.drawerBorderColor))
			etMessage.setTextColor(getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary))
			iView1.setBackgroundColor(getParsedColor(ThemeColor.currentColor.drawerBorderColor))
			iView2.setBackgroundColor(getParsedColor(ThemeColor.currentColor.drawerBorderColor))
			btnReport.background = getBackgroundColor(
				getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
			btnReport.setTextColor(getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary))
			btnCancel.background = getBackgroundColor(
				getParsedColor(ThemeColor.currentColor.drawerBackgroundColor),
				getParsedColor(ThemeColor.currentColor.drawerBorderColor))
			btnCancel.setTextColor(getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary))
		}

		val title = "Report a Problem"
		tvTitle.text = title
		val msg = "Please tell us more about the problem you are experiencing:"
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
					WebViewPresenter(chatView).run {
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