package chata.can.chata_ai.activity.chat.holder

import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.base.ItemSelectedListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.extension.margin

class FullSuggestionHolder(itemView: View): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val llSuggestion = itemView.findViewById<LinearLayout>(R.id.llSuggestion)
	private val rlRunQuery = itemView.findViewById<View>(R.id.rlRunQuery)

	override fun onPaint()
	{
		val textColor = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_color_primary)
		tvContent.setTextColor(textColor)
		llContent.background = buildBackgroundGrayWhite()
		rlRunQuery.background = buildBackgroundGrayWhite()
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			val simpleQuery = item.simpleQuery
			if (simpleQuery is FullSuggestionQuery)
			{
				tvContent.context?.let {
					context ->
					context.resources?.let {
						resources ->
						val message = resources.getString(R.string.msg_full_suggestion)
						tvContent.text = message
					}

					llSuggestion.removeAllViews()
					val aWords = simpleQuery.initQuery.split(" ")
					val mapSuggestion = simpleQuery.mapSuggestion

					var subRow: LinearLayout ?= null

					for(index in aWords.indices)
					{
						val childCount = llSuggestion.childCount
						if (childCount == 0 || index % 3 == 0)
						{
							subRow = LinearLayout(context)
							subRow.layoutParams = LinearLayout.LayoutParams(-1, -2, 3f)
							subRow.orientation = LinearLayout.HORIZONTAL

							llSuggestion.addView(subRow)
						}

						val currentText = aWords[index]
						mapSuggestion[currentText]?.let {
							//region parent view
							val relativeLayout = RelativeLayout(context).apply {
								layoutParams = LinearLayout.LayoutParams(0, -2).apply {
									weight = 1f
								}
							}
							//endregion
							val spinner = Spinner(context)
							val textView = TextView(context)
							//region spinner
							it.add(0, currentText)
							val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, it)
							spinner.apply {
								layoutParams = LinearLayout.LayoutParams(-1, -2)
								setAdapter(adapter)
								isEnabled = false
								setSelection(0, false)
								onItemSelectedListener = object: ItemSelectedListener {
									override fun onSelected(
										parent: AdapterView<*>?, view: View?, position: Int, id: Long)
									{
										parent?.let {
											adapterView ->
											adapterView.adapter?.let {
												adapter ->
												val newContent = adapter.getItem(position)?.toString() ?: ""
												textView.text = newContent
											}
										}
									}
								}
							}
							//endregion
							//region message
							textView.apply {
								background = buildBackgroundGrayWhite()
								layoutParams = LinearLayout.LayoutParams(-1, -2)
								margin(4f, 4f, 4f, 4f)
								setPadding(9,9,9,9)
								gravity = Gravity.CENTER
								text = currentText

								setOnClickListener { spinner.performClick() }
							}
							//endregion
							relativeLayout.addView(spinner)
							relativeLayout.addView(textView)
							subRow?.addView(relativeLayout)
						} ?: run {
							val textView = TextView(context).apply {
								background = buildBackgroundGrayWhite()
								layoutParams = LinearLayout.LayoutParams(0, -2).apply {
									weight = 1f
								}
								margin(5f, 0f, 5f, 3f)
								setPadding(15,15,15,15)
								gravity = Gravity.CENTER
								text = currentText
							}
							subRow?.addView(textView)
						}
					}
				}
			}
		}
	}

	private fun buildBackgroundGrayWhite(): GradientDrawable
	{
		val white = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_background_color)
		val gray = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_color_primary)
		return DrawableBuilder.setGradientDrawable(white,18f,1, gray)
	}
}