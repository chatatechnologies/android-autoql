package chata.can.chata_ai.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup

abstract class BaseDialog(context: Context, private val intRest: Int): Dialog(context)
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setCanceledOnTouchOutside(false)
		setContentView(intRest)
		window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
		onCreateView()
	}

	open fun onCreateView()
	{
		setViews()
		setColors()
	}

	abstract fun setViews()
	abstract fun setColors()
}