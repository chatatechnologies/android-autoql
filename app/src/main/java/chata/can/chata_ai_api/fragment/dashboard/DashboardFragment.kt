package chata.can.chata_ai_api.fragment.dashboard

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.base.ItemSelectedListener
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
		btnDashboard = view.findViewById(R.id.btnDashboard)
		spDashboard = view.findViewById(R.id.spDashboard)
		rvDashboard = view.findViewById(R.id.rvDashboard)
	}

	override fun setColors()
	{
		btnExecute.backgroundGrayWhite()
		btnDashboard.backgroundGrayWhite()
	}

	override fun initListener()
	{
		btnExecute.setOnClickListener(this)
		btnDashboard.setOnClickListener(this)
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
		hideKeyboard()
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
				R.id.btnDashboard ->
				{
					spDashboard.performClick()
				}
				else -> {}
			}
		}
	}

	override fun setDashboards()
	{
		activity?.let {
			isLoaded = true
			adapter = GridAdapter(SinglentonDashboard.getCurrentDashboard())
			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = adapter

			val aData = SinglentonDashboard.getDashboardNames()
			val adapter = ArrayAdapter(
				it,
				android.R.layout.simple_spinner_item,
				aData)
			spDashboard.adapter = adapter
			btnDashboard.text = aData.firstOrNull() ?: ""

			spDashboard.onItemSelectedListener = object: ItemSelectedListener
			{
				override fun onSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
				{
					parent?.getItemAtPosition(position)?.let { content ->
						if (content is String)
						{
							btnDashboard.text = content
						}
					}
				}
			}

			spDashboard.dropDownVerticalOffset = btnDashboard.height
			spDashboard.dropDownWidth = btnDashboard.width
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
	private lateinit var btnDashboard: TextView
	private lateinit var spDashboard: Spinner
	private lateinit var rvDashboard: RecyclerView
	private lateinit var adapter: GridAdapter
	private var presenter = DashboardPresenter(this)
	private var isAutomatic = false
	private var isLoaded = false
}