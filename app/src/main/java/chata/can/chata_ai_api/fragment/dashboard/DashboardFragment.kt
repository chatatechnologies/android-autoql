package chata.can.chata_ai_api.fragment.dashboard

import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.adapter.GridAdapter
import chata.can.chata_ai_api.putArgs

class DashboardFragment: BaseFragment(), View.OnClickListener, DashboardContract
{
	override fun initViews(view: View)
	{
		swLoad = view.findViewById(R.id.swLoad)
		btnExecute = view.findViewById(R.id.btnExecute)
		rvDashboard = view.findViewById(R.id.rvDashboard)
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

	override fun onResume()
	{
		super.onResume()
		loadDashboard()
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btnExecute ->
				{
					//adapter.notifyDataSetChanged()
				}
			}
		}
	}

	override fun setDashboards()
	{
		activity?.let {
			adapter = GridAdapter(SinglentonDashboard.mModel)

			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = adapter
		}
	}

	fun loadDashboard()
	{
		presenter.getDashboards()
	}


	companion object {
		const val nameFragment = "Dashboard"
		fun newInstance() = DashboardFragment()
			.putArgs {
				putInt("LAYOUT", R.layout.fragment_slide_page)
			}
	}

	private lateinit var swLoad: Switch
	private lateinit var btnExecute: TextView
	private lateinit var rvDashboard: RecyclerView
	private lateinit var adapter: GridAdapter
	private var presenter = DashboardPresenter(this)
}