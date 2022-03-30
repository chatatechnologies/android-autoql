package chata.can.chata_ai_api.fragment.dashboard.holder

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.dialog.reportProblem.ReportProblemDialog
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.view.popup.PopupMenu.buildPopup
import chata.can.chata_ai_api.R

class ContentHolder(itemView: View): DashboardHolder(itemView) {

	private val ll1 = itemView.findViewById<View>(R.id.ll1)
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
	private val ivOption = itemView.findViewById<ImageView>(R.id.ivOption)

	init {
		ll1.backgroundWhiteGray()
		tvTitle.setTextColor(SinglentonDrawer.currentAccent)

		tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		ivOption.backgroundWhiteGray()
		ivOption.setColorFilter(SinglentonDrawer.currentAccent)
	}

	override fun onRender(dashboard: Dashboard) {
		val titleToShow =
			dashboard.title.ifEmpty {
				dashboard.query.ifEmpty { itemView.context.getString(R.string.untitled) }
			}
		tvTitle?.text = titleToShow
		//region for DashboardEntity
		tvContent.text = dashboard.contentFromViewModel
		//endregion

		dashboard.queryBase?.run {
			if (isLoadingHTML && contentHTML.isEmpty()) {
				isLoadingHTML = false
				val message = when
				{
					simpleText.isNotEmpty() ->
					{
						aColumn.firstOrNull()?.let { column ->
							tvContent.setOnClickListener { view ->
								showDrillDown(view, this)
							}
							simpleText.formatWithColumn(column)
						} ?: ""
					}
					aRows.size == 0 ->
					{
						tvContent.setOnClickListener(null)
						reportLink(message)
					}
					message.isNotEmpty() -> message
					else -> message
				}
				tvContent.text = message
			} else {
				if (contentHTML.isEmpty())
				{
					tvContent.setOnClickListener(null)
					tvContent.text = reportLink(message)
				}
				else
				{
					aColumn.firstOrNull()?.let { _ ->
						tvContent.setOnClickListener { view ->
							showDrillDown(view, this)
						}
					}
					tvContent.text = contentHTML
				}
			}

			ivOption?.setOnClickListener { view ->
				buildPopup(view, listOf(4), sql)
			}
		}
	}

	private fun showDrillDown(view: View, queryBase: QueryBase)
	{
		if (SinglentonDrawer.mIsEnableDrillDown)
		{
			DrillDownDialog(view.context, queryBase).show()
		}
	}

	private fun reportLink(message: String): CharSequence
	{
		//region report link
		return if (message.contains("<") && message.contains(">"))
		{
			val index = message.indexOf("<")
			val index2 = message.indexOf(">") - 1
			val message1 = message.replace("<","").replace(">","")
			val spannable = SpannableString(message1)
			val clickable = object: ClickableSpan()
			{
				override fun onClick(widget: View)
				{
					ReportProblemDialog(tvContent.context, "", null).show()
				}

				override fun updateDrawState(textPaint: TextPaint)
				{
					textPaint.run {
						try {
							color = tvContent.getParsedColor(chata.can.chata_ai.R.color.chata_drawer_accent_color)
						} finally {
							tvContent.context?.let {
								bgColor = ThemeColor.currentColor.pDrawerBackgroundColor
							}
						}
						isUnderlineText = false
					}
				}
			}
			spannable.setSpan(clickable, index, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
			tvContent.movementMethod = LinkMovementMethod.getInstance()
			spannable
		}
		else
			message
		//endregion
	}
}