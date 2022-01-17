package chata.can.chata_ai.fragment.dataMessenger.holder

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.suggestion.RequestSuggestion
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.textViewSpinner.TermAdapter
import chata.can.chata_ai.view.textViewSpinner.model.Suggestion
import com.google.android.flexbox.FlexboxLayout

class FullSuggestionHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val view: ChatContract.View
): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
//	private val stvContent = itemView.findViewById<SpinnerTextView>(R.id.stvContent)
	private val rlRunQuery = itemView.findViewById<View>(R.id.rlRunQuery)
	private val tvRunQuery = itemView.findViewById<TextView>(R.id.tvRunQuery)
	private val ivRunQuery = itemView.findViewById<ImageView>(R.id.ivRunQuery)

	private val fbSuggestion = itemView.findViewById<FlexboxLayout>(R.id.fbSuggestion)
	private val spSuggestion = itemView.findViewById<Spinner>(R.id.spSuggestion)

	private var aData = ArrayList<Suggestion>()
	private var lastData = ArrayList<Pair<String, String>>()
	private var textDisplayed = ""
	private var valueLabel = ""
	private var canonical = ""
	private var itemText = ""
	private var start = 0
	private var end = 0

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = context.getParsedColor(R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val queryDrawable = DrawableBuilder.setGradientDrawable(SinglentonDrawer.currentAccent,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)
		}

		val textColor = ThemeColor.currentColor.pDrawerTextColorPrimary
		tvContent.setTextColor(textColor)
		tvRunQuery.setTextColor(textColor)
		ivRunQuery.setColorFilter(textColor)
		llContent.backgroundGrayWhite()
		val colorBase = ThemeColor.currentColor.pDrawerBackgroundColor
		val borderColor = ThemeColor.currentColor.pDrawerBorderColor
		rlRunQuery.background = DrawableBuilder.setGradientDrawable(colorBase, 18f, 3, borderColor)
		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		llContent.startAnimation(animation)

		llContent.resources?.displayMetrics?.let {
			spSuggestion.dropDownWidth = it.widthPixels
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			val simpleQuery = item.simpleQuery
			if (simpleQuery is FullSuggestionQuery)
			{
				tvContentTop.text = simpleQuery.query
				tvContent.context?.let {
					context ->
					context.resources?.let {
						resources ->
						val message = resources.getString(R.string.msg_full_suggestion)
						tvContent.text = message
					}
					setText(context, simpleQuery.aSuggestion)

					rlRunQuery.setOnClickListener {
						val query = textDisplayed
						if (query.isNotEmpty())
						{
							val requestSuggestion = RequestSuggestion(query, itemText, canonical, valueLabel, start, end)
							view.runTyping(requestSuggestion)
						}
					}
				}
			}
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.rlDelete ->
				{
					//region delete query
					adapterView?.deleteQuery(adapterPosition)
					//endregion
				}
				else -> {}
			}
		}
	}

	private fun setText(context: Context, aSuggestion: ArrayList<Suggestion> ?= null) {
		//region add options
		aSuggestion?.let {
			aData = aSuggestion
		}

		aData.forEach { suggestion ->
			val tv = TextView(context).apply {
				paddingAll(12f, 6f, 12f, 6f)
				textDisplayed += suggestion.text + " "
				text = suggestion.text

				val pData = suggestion.aSuggestion?.let {
					val textTmp = it[0].first
					val iStart = textTmp.indexOf("(") + 1
					val iEnd = textTmp.lastIndexOf(")")
					valueLabel = textTmp.substring(iStart, iEnd)
					canonical = it[0].second
					itemText = suggestion.text

					start = suggestion.start
					end = suggestion.end + 1

					setOnClickListener {
						getSpinnerOption(context, suggestion)
					}
					Pair(
						context.getParsedColor(R.color.blue_chata_circle),
						ThemeColor.currentColor.run {
							DrawableBuilder.setGradientDrawable(
								pDrawerBackgroundColor, 18f, 3, pDrawerBorderColor
							)
						}
					)
				} ?: run {
					Pair(ThemeColor.currentColor.pDrawerTextColorPrimary, null)
				}

				setTextColor(pData.first)
				background = pData.second
			}
			fbSuggestion.addView(tv)
		}
		textDisplayed = textDisplayed.removeSuffix(" ")
		//endregion
	}

	private fun getSpinnerOption(context: Context, suggestion: Suggestion) {
		suggestion.aSuggestion?.let { aData ->
			if (lastData != aData) {
				lastData = aData
				val aText = aData.map { it.first }
				spSuggestion.adapter = TermAdapter(context, aText)
				spSuggestion.setOnItemSelected { parent, _, position, _ ->
					if (suggestion.position != position) {
						//region for remove view on parent
						if (position == aData.size - 1) {
							this@FullSuggestionHolder.aData.run {
								remove(suggestion)
								setText(context)
							}
						}
						//endregion
						else {
							parent?.getItemAtPosition(position)?.let { item ->
								if (item is String) {
									val aItems = item.split(" (")
									val currentText = textDisplayed
									val currentSection = aItems[0]
									val newStart = suggestion.start
									val newEnd = suggestion.end
									val beforeSection = currentText.substring(newStart, newEnd)

									lastData[position].let { pair ->
										valueLabel = aItems[1].replace(")", "")
										canonical = pair.second
										itemText = suggestion.text
									}

									if (beforeSection != currentSection) {
										val newText = currentText.replace(beforeSection, currentSection)
										suggestion.text = currentSection
										suggestion.position = position
										//region updateIndex(newText)
										for (iSuggestion in this@FullSuggestionHolder.aData)
										{
											iSuggestion.run {
												start = newText.indexOf(text)
												end = start + text.length
											}
										}
										//endregion
										setText(context)
									}
								}
							}
						}
					}
				}

			}
			spSuggestion.performClick()
		}
	}
}