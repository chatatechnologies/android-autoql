package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.pojo.dashboard.Dashboard

abstract class DashboardHolder(view: View): RecyclerView.ViewHolder(view) {
	abstract fun onRender(dashboard: Dashboard)
}