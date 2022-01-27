package chata.can.chata_ai_api.fragment.dashboard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setOnItemSelected
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.databinding.FragmentDashboardBinding
import chata.can.chata_ai_api.fragment.dashboard.adapter.DashboardSpinnerAdapter
import chata.can.chata_ai_api.fragment.dashboard.adapter.GridAdapter

class DashboardFragment: Fragment(), View.OnClickListener, DashboardContract
{
	companion object {
		const val nameFragment = "Dashboard"
		fun newInstance() = DashboardFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_dashboard)
		}
	}

	private lateinit var binding: FragmentDashboardBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentDashboardBinding.inflate(inflater, container, false)
		return binding.root
	}

	private lateinit var gridAdapter: GridAdapter
	private var presenter = DashboardPresenter(this)
	private val mModel = BaseModelList<Dashboard>()
	private var isQueryClean = true
	private var isAutomatic = false
	private var isLoaded = false

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setResources()

		SinglentonDrawer.aThemeMethods[nameFragment] = {
			setColors()
			if (::gridAdapter.isInitialized)
			{
				gridAdapter.notifyItemRangeChanged(0, mModel.countData())
			}
		}

		setColors()
		initListener()
	}

	/**
	 * set color to view
	 * here is used runN@ for reference run object
	 */
	private fun setColors()
	{
		binding.run run1@ {
			ThemeColor.currentColor.run run2@ {
				activity?.run run3@ {
					svParent.setBackgroundColor(pDrawerColorSecondary)
					val backgroundColor = getParsedColor(R.color.white)
					val border = getParsedColor(R.color.border_widget_dashboard)
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
	}

	private fun initListener()
	{
		binding.run {
			btnExecute.setOnClickListener(this@DashboardFragment)
			btnDashboard.setOnClickListener(this@DashboardFragment)
			swLoad.setOnCheckedChangeListener {
				_, isChecked ->
				isAutomatic = isChecked
				if (isAutomatic)
				{
					getDashboardQueries()
				}
			}
		}
	}

	override fun onResume()
	{
		super.onResume()
		binding.llOption.setBackgroundColor(Color.parseColor(SinglentonDashboard.dashboardColor))
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
		//hide keyboard
		(requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
			?.hideSoftInputFromWindow(view?.windowToken, 0)
	}

	override fun onPause()
	{
		super.onPause()
		if (isLoaded && !isQueryClean)
		{
			isQueryClean = true
			presenter.resetDashboards(false)
			gridAdapter.notifyItemRangeChanged(0, mModel.countData())
		}
	}

	override fun onClick(view: View?)
	{
		binding.run {
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
	}

	override fun setDashboards()
	{
		binding.run {
			activity?.run {
				isLoaded = true
				rvDashboard.visibility = View.VISIBLE
				mModel.addAll(SinglentonDashboard.getCurrentDashboard().getData())
				gridAdapter = GridAdapter(mModel, presenter)
				rvDashboard.layoutManager = LinearLayoutManager(this)
				rvDashboard.adapter = gridAdapter
				rvDashboard.itemAnimator = null

				val aData = SinglentonDashboard.getDashboardNames()

				val adapter = DashboardSpinnerAdapter(this, aData)
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
							gridAdapter.notifyItemRangeChanged(0, mModel.countData())

							configDashboard()
						}
					}
				}

				spDashboard.dropDownVerticalOffset = btnDashboard.height
				spDashboard.dropDownWidth = btnDashboard.width
//			hideDialog()
			}
		}
	}

	override fun notifyQueryAtIndex(index: Int)
	{
		gridAdapter.notifyItemChanged(index)
	}

	private fun setResources()
	{
		StringContainer.run {
			columnHidden = getString(R.string.column_hidden)
			errorId = getString(R.string.errorId)
			notRecognized = getString(R.string.not_recognized)
			success = getString(R.string.success)
		}
	}

	private fun loadDashboard()
	{
		if (!isLoaded)
		{
			presenter.getDashboards()
		}
	}

	private fun configDashboard()
	{
		binding.run {
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
	}

	private fun getDashboardQueries()
	{
		isQueryClean = false
		presenter.updateModel()
		presenter.getDashboardQueries()
	}
}