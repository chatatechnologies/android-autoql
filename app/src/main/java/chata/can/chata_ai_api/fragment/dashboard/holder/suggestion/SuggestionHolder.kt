package chata.can.chata_ai_api.fragment.dashboard.holder.suggestion

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.*
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.databinding.CardSuggestionBinding
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.DashboardHolder

class SuggestionHolder(
	itemView: View,
	private val presenter: DashboardPresenter
	): DashboardHolder(itemView) {
	private val binding = CardSuggestionBinding.bind(itemView)

	private lateinit var tvSuggestion: TextView
	private lateinit var spSuggestion: Spinner

	init {
		binding.run {
			ll1.backgroundWhiteGray()
			tvTitle.setTextColor(SinglentonDrawer.currentAccent)
			tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		}
	}

	override fun onRender(dashboard: Dashboard) {
		binding.run {
			val titleToShow =
				dashboard.title.ifEmpty {
					dashboard.query.ifEmpty { itemView.context.getString(R.string.untitled) }
				}
			tvTitle.text = titleToShow

			dashboard.queryBase?.let { queryBase: QueryBase ->
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
							val tv = buildSuggestionView(llSuggestion.context, suggestion, dashboard)
							llSuggestion.addView(tv)
						}
					}
				}
				else
				{
					val context = llSuggestion.context
					llSuggestion.addView(
						RelativeLayout(context).apply {
							layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
							background = DrawableBuilder.setGradientDrawable(
								ThemeColor.currentColor.pDrawerBackgroundColor,
								3f,
								3,
								SinglentonDrawer.currentAccent)
							paddingAll(8f, 4f, 8f, 4f)
							//region view hidden
							spSuggestion = Spinner(context).apply {
								layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
								val aItem = rows.map { it[0] }
								adapter = SuggestionAdapter(context, aItem)
								setSelection(0, false)
								setOnItemSelected { _, _, position, _ ->
									val text = aItem[position]
									(adapter as? SuggestionAdapter)?.updatePositionSelected(position)
									tvSuggestion.text = text
								}
							}
							addView(spSuggestion)
							//endregion
							//view on Right
							addView(
								ImageView(context).apply {
									layoutParams = getRelativeLayoutParams(dpToPx(24f), dpToPx(24f)).apply {
										addRule(RelativeLayout.CENTER_VERTICAL)
										addRule(RelativeLayout.ALIGN_PARENT_END)
									}
									gravity = Gravity.CENTER
									id = R.id.ivAction
									setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
									setImageResource(R.drawable.ic_down)
									setColorFilter(SinglentonDrawer.currentAccent)
									setOnClickListener { spSuggestion.performClick() }
								}
							)
							//region front text
							tvSuggestion = TextView(context).apply {
								layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
									addRule(RelativeLayout.CENTER_VERTICAL)
									addRule(RelativeLayout.START_OF, R.id.ivAction)
								}
								setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
								setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
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
			layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			textSize(16f)
			setPadding(15,15,15,15)
			text = content
			setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
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