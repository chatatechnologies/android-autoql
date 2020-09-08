package chata.can.chata_ai.dialog

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R

class ReportProblem(
	context: Context
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
				R.id.btnReport -> {}
			}
		}
	}
}