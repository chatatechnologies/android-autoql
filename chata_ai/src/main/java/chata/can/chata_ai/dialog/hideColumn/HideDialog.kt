package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.pojo.color.ThemeColor

class HideDialog(
	context: Context
) : BaseDialog(context, R.layout.dialog_hide, false)
{
	private lateinit var tvTitle: TextView
	private lateinit var vBorder: View

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	private fun setData()
	{
		tvTitle.text = "Show/Hide Columns"
	}

	override fun setViews()
	{
		tvTitle = findViewById(R.id.tvTitle)
		vBorder = findViewById(R.id.vBorder)
	}

	override fun setColors()
	{
		ThemeColor.currentColor.run {
			tvTitle.setTextColor(pDrawerTextColorPrimary)
			vBorder.setBackgroundColor(pDrawerBorderColor)
		}
	}
}