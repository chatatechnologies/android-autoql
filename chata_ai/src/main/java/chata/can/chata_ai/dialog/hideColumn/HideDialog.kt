package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.pojo.color.ThemeColor

class HideDialog(
	context: Context
) : BaseDialog(context, R.layout.dialog_hide, false), View.OnClickListener
{
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var vBorder: View
	private lateinit var btnCancel: Button
	private lateinit var btnApply: Button

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	private fun setData()
	{
		tvTitle.text = context.getString(R.string.show_hide_column)
		ivCancel.setOnClickListener(this)
		btnCancel.setOnClickListener(this)
		btnApply.setOnClickListener(this)
	}

	override fun setViews()
	{
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		vBorder = findViewById(R.id.vBorder)
		btnCancel = findViewById(R.id.btnCancel)
		btnApply = findViewById(R.id.btnApply)
	}

	override fun setColors()
	{
		ThemeColor.currentColor.run {
			tvTitle.setTextColor(pDrawerTextColorPrimary)
			vBorder.setBackgroundColor(pDrawerBorderColor)
		}
	}

	override fun onClick(view: View?)
	{
		view?.let { it ->
			when(it.id)
			{
				R.id.ivCancel -> { dismiss() }
				R.id.btnCancel -> {  }
				R.id.btnApply -> {  }
			}
		}
	}
}