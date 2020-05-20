package chata.can.chata_ai.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle

abstract class BaseDialog(context: Context, private val intRest: Int): Dialog(context)
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setCanceledOnTouchOutside(false)
		setContentView(intRest)
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