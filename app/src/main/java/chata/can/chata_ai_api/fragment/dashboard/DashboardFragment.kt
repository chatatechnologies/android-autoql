package chata.can.chata_ai_api.fragment.dashboard

import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.activity.chat.PropertyChatActivity
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.adapter.GridAdapter
import chata.can.chata_ai_api.putArgs

class DashboardFragment: BaseFragment(), View.OnClickListener, DashboardContract
{
	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let {
			PropertyChatActivity.context = it
		}
	}

	override fun initViews(view: View)
	{
		swLoad = view.findViewById(R.id.swLoad)
		btnExecute = view.findViewById(R.id.btnExecute)
		rvDashboard = view.findViewById(R.id.rvDashboard)
	}

	override fun setColors()
	{
		btnExecute.backgroundGrayWhite()
	}

	override fun initListener()
	{
		btnExecute.setOnClickListener(this)
		swLoad.setOnCheckedChangeListener {
			_, isChecked ->
			isAutomatic = isChecked
			if (isAutomatic)
			{
				getDashboardQueries()
			}
		}
	}

	override fun onResume()
	{
		super.onResume()
		loadDashboard()
		if (isAutomatic)
		{
			getDashboardQueries()
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btnExecute ->
				{
					getDashboardQueries()
				}
			}
		}
	}

	override fun setDashboards()
	{
		activity?.let {
			isLoaded = true
			adapter = GridAdapter(model)
			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = adapter
		}
	}

	override fun notifyQueryAtIndex(index: Int)
	{
		adapter.notifyItemChanged(index)
	}

	private fun loadDashboard()
	{
		if (!isLoaded)
		{
			presenter.getDashboards()
		}
		else
		{
			presenter.resetDashboards(!isAutomatic)
		}
	}

	private fun getDashboardQueries()
	{
		presenter.getDashboardQueries()
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
	private var isAutomatic = false
	private var isLoaded = false
	val model = SinglentonDashboard.mModel
}