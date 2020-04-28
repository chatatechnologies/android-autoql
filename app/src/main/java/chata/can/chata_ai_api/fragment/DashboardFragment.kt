package chata.can.chata_ai_api.fragment

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.putArgs

class DashboardFragment: BaseFragment(), View.OnClickListener
{
	companion object {
		const val nameFragment = "Dashboard"
		fun newInstance() = DashboardFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_slide_page)
		}
	}

	private lateinit var btnExecute: TextView
	private lateinit var rvDashboard: RecyclerView
	private lateinit var adapter: GridAdapter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let {
			adapter = GridAdapter(SinglentonDashboard.mModel)

			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = adapter
		}
	}

	override fun initViews(view: View)
	{
		rvDashboard = view.findViewById(R.id.rvDashboard)
		btnExecute = view.findViewById(R.id.btnExecute)
	}

	override fun setColors()
	{
		activity?.let { activity ->
			val white = ContextCompat.getColor(
				activity,
				ThemeColor.currentColor.drawerBackgroundColor)
			val gray = ContextCompat.getColor(activity, ThemeColor.currentColor.drawerColorPrimary)
			btnExecute.background = DrawableBuilder.setGradientDrawable(white,18f,1, gray)
		}
	}

	override fun initListener()
	{
		btnExecute.setOnClickListener(this)
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btnExecute ->
				{
					adapter.notifyDataSetChanged()
				}
			}
		}
	}
}