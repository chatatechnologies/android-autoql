package chata.can.chata_ai_api.fragment.dashboard

import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.base.ItemSelectedListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.adapter.DashboardSpinnerAdapter
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
		tvEmptyDashboard = view.findViewById(R.id.tvEmptyDashboard)
	}

	override fun setColors()
	{
		btnExecute.backgroundGrayWhite()
		btnDashboard.backgroundGrayWhite()

		spDashboard.context.run {
			val white = getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
			val gray = getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
			spDashboard.setPopupBackgroundDrawable(
				DrawableBuilder.setGradientDrawable(white,18f,2, gray))
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
			mModel.addAll(SinglentonDashboard.getCurrentDashboard().getData())
			gridAdapter = GridAdapter(mModel, presenter)
			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = gridAdapter

			val aData = SinglentonDashboard.getDashboardNames()
			val adapter = DashboardSpinnerAdapter(it, aData)
			spDashboard.adapter = adapter

			btnDashboard.text = aData.firstOrNull() ?: ""

			spDashboard.setSelection(0, false)
			spDashboard.onItemSelectedListener = object: ItemSelectedListener
			{
				override fun onSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
				{
					adapter.positionSelect = position
					parent?.getItemAtPosition(position)?.let { content ->
						if (content is String)
						{
							btnDashboard.text = content
							SinglentonDashboard.clearDashboard()
							SinglentonDashboard.setDashboardIndex(position)

							val model = SinglentonDashboard.getCurrentDashboard()
							mModel.clear()
							mModel.addAll(model.getData())
							gridAdapter.notifyDataSetChanged()

							if (model.countData() == 0)
							{
								tvEmptyDashboard.visibility = View.VISIBLE
								rvDashboard.visibility = View.GONE
							}
							else
							{
								tvEmptyDashboard.visibility = View.GONE
								rvDashboard.visibility = View.VISIBLE
							}
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

	private fun getDashboardQueries()
	{
		isQueryClean = false
		presenter.updateModel()
		presenter.getDashboardQueries()
	}

	companion object {
		const val nameFragment = "Dashboard"
		fun newInstance() = DashboardFragment()
			.putArgs {
				putInt("LAYOUT", R.layout.fragment_slide_page)
			}
	}

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
}