package chata.can.chata_ai.fragment.dataMessenger.holder

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.base.ItemSelectedListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.setColorFilter
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.fragment.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class FullSuggestionHolder(
	itemView: View,
	private val view: ChatContract.View,
	private val adapterView: ChatAdapterContract?,
	private val servicePresenter: ChatServicePresenter
): BaseHolder(itemView), View.OnClickListener
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val llSuggestion = itemView.findViewById<LinearLayout>(R.id.llSuggestion)
	private val rlRunQuery = itemView.findViewById<View>(R.id.rlRunQuery)

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = ContextCompat.getColor(context, R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val accentColor = ContextCompat.getColor(context, ThemeColor.currentColor.drawerAccentColor)
			val queryDrawable = DrawableBuilder.setGradientDrawable(accentColor,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)
		}

		val textColor = ContextCompat.getColor(
			tvContent.context, ThemeColor.currentColor.drawerColorPrimary)
		tvContent.setTextColor(textColor)
		llContent.backgroundGrayWhite()
		rlRunQuery.backgroundGrayWhite()

		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}
		ivDelete?.setColorFilter()

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		llContent.startAnimation(animation)
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

					llSuggestion.removeAllViews()
					val aWords = simpleQuery.initQuery.split(" ")
					val mapSuggestion = simpleQuery.mapSuggestion

					var subRow: LinearLayout ?= null

					for(index in aWords.indices)
					{
						val childCount = llSuggestion.childCount
						if (childCount == 0 || index % 3 == 0)
						{
							subRow = LinearLayout(context).apply {
								layoutParams = LinearLayout.LayoutParams(-1, -2)
								orientation = LinearLayout.HORIZONTAL
							}
							llSuggestion.addView(subRow)
						}

						val currentText = aWords[index]
						mapSuggestion[currentText]?.let {
							//region parent view
							val relativeLayout = RelativeLayout(context).apply {
								layoutParams = LinearLayout.LayoutParams(0, -2).apply {
									weight = 1f
								}
								margin(5f, 0f, 5f, 0f)
							}
							//endregion
							val spinner = Spinner(context)
							val llSelectedView = LinearLayout(context)
							val tvFirst = TextView(context)
							//region spinner
							it.add("$currentText (Origin term)")//it.add(0, "$currentText (Origin term)")
							val adapter = object: ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, it)
							{
								override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
								{
									var view = convertView
									if (view == null)
									{
										view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, null)
									}

									view?.findViewById<TextView>(android.R.id.text1)?.run {
										val content = getItem(position) ?: ""
										val aParts = content.split(" (")
										if (aParts.isNotEmpty())
										{
											text = aParts[0]
										}
									}

									return super.getView(position, convertView, parent)
								}
							}
							spinner.apply {
								layoutParams = LinearLayout.LayoutParams(-1, -2)
								setAdapter(adapter)
								isEnabled = false
								//setSelection(0, false)
								onItemSelectedListener = object: ItemSelectedListener {
									override fun onSelected(
										parent: AdapterView<*>?, view: View?, position: Int, id: Long)
									{
										parent?.let {
											adapterView ->
											adapterView.adapter?.let {
												adapter ->
												val newContent = adapter.getItem(position)?.toString() ?: ""
												val aParts = newContent.split(" (")
												tvFirst.text = aParts[0]
											}
										}
									}
								}
							}
							//endregion
							//region message
							llSelectedView.apply {
								backgroundGrayWhite()
								layoutParams = LinearLayout.LayoutParams(-1, -2).apply {
									setGravity(Gravity.CENTER)
								}
								orientation = LinearLayout.HORIZONTAL
								//setPadding(9,9,9,9)

								tvFirst.apply {
									layoutParams = RelativeLayout.LayoutParams(-2, -2)
									setPadding(15,15,15,15)
									gravity = Gravity.CENTER
									text = currentText
									setOnClickListener { spinner.performClick() }
								}
								val doubleArrow = ImageView(context).apply {
									layoutParams = RelativeLayout.LayoutParams(dpToPx(16f), dpToPx(16f))
									setImageResource(R.drawable.ic_double_arrow)
									setOnClickListener { spinner.performClick() }
								}

								addView(tvFirst)
								addView(doubleArrow)
							}
							//endregion

							relativeLayout.addView(spinner)
							relativeLayout.addView(llSelectedView)
							subRow?.addView(relativeLayout)
						} ?: run {
							val textView = TextView(context).apply {
								backgroundGrayWhite()
								layoutParams = LinearLayout.LayoutParams(0, -2).apply {
									weight = 1f
								}
								margin(5f, 0f, 5f, 0f)
								setPadding(15,15,15,15)
								gravity = Gravity.CENTER
								text = currentText
							}
							subRow?.addView(textView)
						}
					}

					rlRunQuery.setOnClickListener {
						val query = buildQuery()
						//view.addChatMessage(2, query)
						servicePresenter.getQuery(query)
					}
				}
			}
		}
	}

	private fun buildQuery(): String
	{
		val finalQuery = StringBuilder()
		llSuggestion?.let {
			val childCount = it.childCount
			for (index in 0 until childCount)
			{
				it.getChildAt(index)?.let {
					child0 ->
					if (child0 is LinearLayout)
					{
						val child0Count = child0.childCount
						for (index1 in 0 until child0Count)
						{
							child0.getChildAt(index1)?.let {
								child ->
								val pieceQuery = when(child)
								{
									is TextView -> {
										child.text
									}
									is RelativeLayout ->
									{
										if (child.childCount == 2)
										{
											child.getChildAt(1)?.let {
												linearLayout ->
												if (linearLayout is LinearLayout)
												{
													linearLayout.getChildAt(0)?.let {
														textView ->
														if (textView is TextView)
														{
//															textView.text
															val aWords = textView.text.split(" (")
															aWords[0]
														}
														else ""
													} ?: run { "" }
												}
												else ""
											}
										}
										else ""
									}
									else -> ""
								}
								finalQuery.append("$pieceQuery ")
							}
						}
					}
				}
			}
		}
		return finalQuery.toString().trimEnd()
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
}