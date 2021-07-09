package chata.can.chata_ai_api.fragment.dashboard

import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setOnItemSelected
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.adapter.DashboardSpinnerAdapter
import chata.can.chata_ai_api.fragment.dashboard.adapter.GridAdapter

class DashboardFragment: BaseFragment(), View.OnClickListener, DashboardContract
{
	companion object {
		const val nameFragment = "Dashboard"
		fun newInstance() = DashboardFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_slide_page)
		}
	}

	private lateinit var svParent: View
	private lateinit var llOption: View
	private lateinit var swLoad: SwitchCompat
	private lateinit var btnExecute: TextView
	private lateinit var btnDashboard: TextView
	private lateinit var spDashboard: Spinner
	private lateinit var rvDashboard: RecyclerView
	private lateinit var tvEmptyDashboard: TextView
	private lateinit var gridAdapter: GridAdapter
	private var presenter = DashboardPresenter(this)
	private val mModel = BaseModelList<Dashboard>()
	private var isQueryClean = true
	private var isAutomatic = false
	private var isLoaded = false

	override fun initViews(view: View)
	{
		view.run {
			svParent = findViewById(R.id.svParent)
			llOption = findViewById(R.id.llOption)
			swLoad = findViewById(R.id.swLoad)
			btnExecute = findViewById(R.id.btnExecute)
			btnDashboard = findViewById(R.id.btnDashboard)
			spDashboard = findViewById(R.id.spDashboard)
			rvDashboard = findViewById(R.id.rvDashboard)
			tvEmptyDashboard = findViewById(R.id.tvEmptyDashboard)
		}

		SinglentonDrawer.aThemeMethods[nameFragment] = {
			setColors()
			if (::gridAdapter.isInitialized)
			{
				gridAdapter.notifyDataSetChanged()
			}
		}
	}

	override fun setColors()
	{
		ThemeColor.currentColor.run {
			activity?.let {
				svParent.setBackgroundColor(pDrawerColorSecondary)
				val backgroundColor = it.getParsedColor(R.color.white)
				val border = it.getParsedColor(R.color.border_widget_dashboard)
				rvDashboard.setBackgroundColor(pDrawerColorSecondary)
				btnExecute.background = DrawableBuilder.setGradientDrawable(
					backgroundColor,18f, 3, border)
				btnDashboard.background = DrawableBuilder.setGradientDrawable(
					backgroundColor,18f, 3, border)
				spDashboard.setPopupBackgroundDrawable(
					DrawableBuilder.setGradientDrawable(
						backgroundColor,18f, 3, border))
				tvEmptyDashboard.setTextColor(pDrawerTextColorPrimary)
			}
		}
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
		llOption.setBackgroundColor(Color.parseColor(SinglentonDashboard.dashboardColor))
		if (SinglentonDashboard.isEmpty())
		{
			isQueryClean = true
			isAutomatic = false
			isLoaded = false
		}
		loadDashboard()
		if (isAutomatic)
		{
			getDashboardQueries()
		}
		hideKeyboard()
	}

	override fun onPause()
	{
		super.onPause()
		if (isLoaded && !isQueryClean)
		{
			isQueryClean = true
			presenter.resetDashboards(false)
			gridAdapter.notifyDataSetChanged()
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btnExecute ->
				{
					val unExecute = getString(R.string.un_execute_dashboard)
					btnExecute.text = if (btnExecute.text.toString() == unExecute)
					{
						presenter.getDashboardQueries(false)
						getString(R.string.execute_dashboard)
					}
					else
					{
						getDashboardQueries()
						unExecute
					}
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
			rvDashboard.visibility = View.VISIBLE
			mModel.addAll(SinglentonDashboard.getCurrentDashboard().getData())
			gridAdapter = GridAdapter(mModel, presenter)
			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = gridAdapter

			val aData = SinglentonDashboard.getDashboardNames()

			val adapter = DashboardSpinnerAdapter(it, aData)
			spDashboard.adapter = adapter

			btnDashboard.text = aData.firstOrNull() ?: ""

			configDashboard()

			spDashboard.setSelection(0, false)
			spDashboard.setOnItemSelected { parent, _, position, _ ->
				adapter.positionSelect = position
				parent?.getItemAtPosition(position)?.let { content ->
					if (content is String)
					{
						btnExecute.text = getString(R.string.execute_dashboard)
						btnDashboard.text = content
						SinglentonDashboard.clearDashboard()
						SinglentonDashboard.setDashboardIndex(position)

						val model = SinglentonDashboard.getCurrentDashboard()
						mModel.clear()
						mModel.addAll(model.getData())
						gridAdapter.notifyDataSetChanged()

						configDashboard()
					}
				}
			}

			spDashboard.dropDownVerticalOffset = btnDashboard.height
			spDashboard.dropDownWidth = btnDashboard.width
//			hideDialog()
		}
	}

	override fun notifyQueryAtIndex(index: Int)
	{
		gridAdapter.notifyItemChanged(index)
	}

	private fun loadDashboard()
	{
		if (!isLoaded)
		{
			presenter.getDashboards()
		}
//		else
//		{
//			if (isAutomatic)
//			{
//				presenter.resetDashboards(!isAutomatic)
//			}
//		}
	}

	private fun configDashboard()
	{
		if (mModel.countData() == 0)
		{
			tvEmptyDashboard.setText(R.string.empty_dashboard)
			tvEmptyDashboard.visibility = View.VISIBLE
			rvDashboard.visibility = View.GONE
		}
		else
		{
			tvEmptyDashboard.visibility = View.GONE
			rvDashboard.visibility = View.VISIBLE
		}
	}

	private fun getDashboardQueries()
	{
		isQueryClean = false
		presenter.updateModel()
		presenter.getDashboardQueries()
	}
}