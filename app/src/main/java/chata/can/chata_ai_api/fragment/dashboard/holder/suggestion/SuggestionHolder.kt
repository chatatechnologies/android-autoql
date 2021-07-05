package chata.can.chata_ai_api.fragment.dashboard.holder.suggestion

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.ItemSelectedListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.adapter.DashboardSpinnerAdapter
import chata.can.chata_ai_api.fragment.dashboard.holder.BaseHolder

class SuggestionHolder(
	itemView: View,
	private val presenter: DashboardPresenter
): BaseHolder(itemView)
{
	private val tvContent: TextView = itemView.findViewById(R.id.tvContent)
	private val llSuggestion: LinearLayout = itemView.findViewById(R.id.llSuggestion)
	private lateinit var tvSuggestion: TextView
	private lateinit var spSuggestion: Spinner

	override fun onPaint()
	{
		super.onPaint()
		tvContent.setTextColor(drawerColorPrimary)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.let { queryBase ->
				tvContent.context?.let { context ->
					val introMessageRes = context.getStringResources(R.string.msg_suggestion)
					val message = String.format(introMessageRes, queryBase.message)
					tvContent.text = message
				}

				val rows = queryBase.aRows
				llSuggestion.removeAllViews()
				if (queryBase.typeSuggestion != "table")
				{
					for (index in 0 until rows.size)
					{
						val singleRow = rows[index]
						singleRow.firstOrNull()?.let { suggestion ->
							//add new view for suggestion
							val tv = buildSuggestionView(llSuggestion.context, suggestion, item)
							llSuggestion.addView(tv)
						}
					}
				}
				else
				{
					val context = llSuggestion.context
					llSuggestion.addView(
						RelativeLayout(context).apply {
							layoutParams = LinearLayout.LayoutParams(-1, -2)
							background = DrawableBuilder.setGradientDrawable(
								0,
								3f,
							3,
								SinglentonDrawer.currentAccent)
							paddingAll(8f, 4f, 8f, 4f)
							//region view hidden
							spSuggestion = Spinner(context).apply {
								visibility = View.GONE
								layoutParams = RelativeLayout.LayoutParams(-1,-2)
								val aItem = rows.map { it[0] }
								adapter = DashboardSpinnerAdapter(context, aItem)
								setSelection(0, false)
//								onItemSelectedListener = object: ItemSelectedListener {
//									override fun onSelected(
//										parent: AdapterView<*>?, view: View?, position: Int, id: Long)
//									{
//										tvSuggestion.text = rows[position][0]
//									}
//								}
							}
							addView(spSuggestion)
							spSuggestion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
								override fun onItemSelected(
									parent: AdapterView<*>?,
									view: View?,
									position: Int,
									id: Long
								) {
									println("Hola 1")
								}

								override fun onNothingSelected(parent: AdapterView<*>?) {
									println("Hola 2")
								}
							}
							//endregion
							//view on Right
							addView(
								ImageView(context).apply {
									layoutParams = RelativeLayout.LayoutParams(dpToPx(24f), dpToPx(24f)).apply {
										addRule(RelativeLayout.ALIGN_PARENT_END)
									}
									gravity = Gravity.CENTER
									id = R.id.ivAction
									setImageResource(R.drawable.ic_down)
									setColorFilter(SinglentonDrawer.currentAccent)
									setOnClickListener { spSuggestion.performClick() }
								}
							)
							//region front text
							tvSuggestion = TextView(context).apply {
								layoutParams = RelativeLayout.LayoutParams(-1,-2).apply {
									addRule(RelativeLayout.CENTER_VERTICAL)
									addRule(RelativeLayout.START_OF, R.id.ivAction)
								}
								setTextColor(drawerColorPrimary)
								paddingAll(4f)
								if (rows.isNotEmpty())
								{
									val child = rows[0]
									if (child.isNotEmpty())
									{
										val firstText = child[0]
										text = firstText
									}
								}
								setOnClickListener { spSuggestion.performClick() }
							}
							//endregion
							addView(tvSuggestion)
						}
					)
				}
			}
		}
	}

	private fun buildSuggestionView(context: Context, content: String, dashboard: Dashboard): TextView
	{
		return TextView(context).apply {
			backgroundWhiteGray()
			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
			setPadding(15,15,15,15)
			text = content
			setTextColor(drawerColorPrimary)
			setOnClickListener {
				val index = SinglentonDashboard.indexDashboard(dashboard)
				if (index != -1)
				{
					presenter.notifyQueryByIndex(index)
					presenter.callQuery(
						dashboard.apply {
							query = content
							title = content
							isWaitingData = true
							queryBase = null
						}
					)
				}
			}
		}
	}
}