package chata.can.chata_ai.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

abstract class BaseDialog(
	context: Context,
	private val intRest: Int = 0,
	private val isFull: Boolean = true,
	private var rootView: View ?= null
): Dialog(context)
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setCanceledOnTouchOutside(false)
		rootView?.let {
			setContentView(it)
		} ?: run {
			setContentView(intRest)
		}

		window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
			if (isFull) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT)
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