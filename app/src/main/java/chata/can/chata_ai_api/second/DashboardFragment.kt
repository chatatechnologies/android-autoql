package chata.can.chata_ai_api.second

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.model.Dashboard
import chata.can.chata_ai_api.putArgs

class DashboardFragment: BaseFragment()
{
	companion object {
//		const val nameClass = "DashboardFragment"
		fun newInstance() = DashboardFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_slide_page)
		}
	}

	private lateinit var rvDashboard: RecyclerView

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let {
			val model = BaseModelList<Dashboard>()
			val adapter = GridAdapter(model)

			model.addAll(aDashboard)

			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = adapter
		}
	}

	override fun initViews(view: View)
	{
		rvDashboard = view.findViewById(R.id.rvDashboard)
	}

	override fun setColors()
	{

	}

	//region DATA
	private val aDashboard = arrayListOf(
		Dashboard("Total sales")
//		Dashboard("Accounts Receivable")
	)
	//endregion

	override fun initListener()
	{

	}
}