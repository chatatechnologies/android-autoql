package chata.can.chata_ai_api.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.model.Dashboard
import chata.can.chata_ai_api.putArgs

class DashboardFragment: Fragment()
{
	companion object {
//		const val nameClass = "DashboardFragment"
		fun newInstance() = DashboardFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_slide_page)
		}
	}

	private lateinit var rvDashboard: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?): View?
	{
		return arguments?.let {
			val iLayout = it.getInt("LAYOUT", 0)
			val view = inflater.inflate(iLayout, container, false)
			rvDashboard = view.findViewById(R.id.rvDashboard)
			initList()
			view
		} ?: run { null }
	}

	//region DATA
	private val aDashboard = arrayListOf(
		Dashboard("Total sales")
//		Dashboard("Accounts Receivable")
	)
	//endregion

	private fun initList()
	{
		activity?.let {
			val model = BaseModelList<Dashboard>()
			val adapter = GridAdapter(model)

			model.addAll(aDashboard)

			rvDashboard.layoutManager = LinearLayoutManager(it)
			rvDashboard.adapter = adapter
		}
	}
}