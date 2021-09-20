package chata.can.chata_ai_api.fragment.dashboard.holder

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.dialog.ReportProblemDialog
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.dialog.sql.DisplaySQLDialog
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.view.popup.PopupMenu.buildPopup
import chata.can.chata_ai_api.R

class ContentHolder(itemView: View): BaseHolder(itemView)
{
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
	private val ivOption = itemView.findViewById<ImageView>(R.id.ivOption)

	override fun onPaint()
	{
		super.onPaint()
		tvContent.context?.let {
			tvContent.setTextColor(drawerColorPrimary)
			ivOption?.backgroundWhiteGray()
			ivOption?.setColorFilter(SinglentonDrawer.currentAccent)
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.run {
				tvContent?.let {
					if (isLoadingHTML && contentHTML.isEmpty())
					{
						isLoadingHTML = false
						val message = when
						{
							simpleText.isNotEmpty() ->
							{
								aColumn.firstOrNull()?.let { column ->
									it.setOnClickListener { view ->
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
					}
					else
						if (contentHTML.isEmpty())
						{
							tvContent.setOnClickListener(null)
							tvContent.text = reportLink(message)
						}
						else
						{
							aColumn.firstOrNull()?.let { _ ->
								it.setOnClickListener { view ->
									showDrillDown(view, this)
								}
							}
							tvContent.text = contentHTML
						}
				}
				ivOption?.setOnClickListener { view ->
					buildPopup(view, sql)
				}
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
							tvContent.context?.let { context ->
								color = context.getParsedColor(chata.can.chata_ai.R.color.chata_drawer_accent_color)
							}
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