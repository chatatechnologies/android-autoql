package chata.can.chata_ai

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment()
{
	var parentActivity: Activity?= null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?): View?
	{
		return arguments?.let {
			activity?.let { activity ->
				parentActivity = activity
			}
			val iLayout = it.getInt("LAYOUT", 0)
			val view = inflater.inflate(iLayout, container, false)
			onRenderViews(view)
			view
		} ?: run { null }
	}

	open fun onRenderViews(view: View)
	{
		initViews(view)
		setColors()
		initListener()
	}

	abstract fun initViews(view: View)
	abstract fun setColors()
	abstract fun initListener()
}