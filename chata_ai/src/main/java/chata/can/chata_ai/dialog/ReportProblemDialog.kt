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
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var tvMessage: TextView
	private lateinit var etMessage: EditText
	private lateinit var btnReport: Button
	private lateinit var btnCancel: Button

	override fun setViews()
	{
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		tvMessage = findViewById(R.id.tvMessage)
		etMessage = findViewById(R.id.etMessage)
		btnReport = findViewById(R.id.btnReport)
		btnCancel = findViewById(R.id.btnCancel)
	}

	override fun setColors()
	{
		context.run {
			tvTitle.setTextColor(getParsedColor(R.color.black))
			tvMessage.setTextColor(getParsedColor(R.color.black))
			etMessage.setTextColor(getParsedColor(R.color.black))
			btnReport.setTextColor(getParsedColor(R.color.white))
			btnCancel.setTextColor(getParsedColor(R.color.black))

			etMessage.background = getBackgroundColor(
				getParsedColor(R.color.white), getParsedColor(R.color.selected_gray))
			btnCancel.background = getBackgroundColor(
				getParsedColor(R.color.white), getParsedColor(R.color.selected_gray))
			val accent = ThemeColor.currentColor.drawerAccentColor
			btnReport.background = getBackgroundColor(
				getParsedColor(accent), getParsedColor(accent))
		}

		val title = "Report a Problem"
		tvTitle.text = title
		val msg = "Please tell us more about the problem you experiencing:"
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
		DrawableBuilder.setGradientDrawable(color, 12f, 1, borderColor)
}