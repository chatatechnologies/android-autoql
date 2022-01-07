package chata.can.chata_ai.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import chata.can.chata_ai.view.gif.KGifView

object DataMessengerRow
{
	fun getRowQueryBuilder(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			//region main container
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				margin(end = 50f)

				//region llContent
				addView(LinearLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					marginAll(5f)
					orientation = LinearLayout.VERTICAL
					id = R.id.llContent
					//region tvMsg
					addView(TextView(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
						margin(10f,5f,0f,5f)
						text = context.resources.getString(R.string.you_can_ask_me)
						id = R.id.tvMsg
					})
					//endregion
					//region llQueries & rvExplore
					addView(RelativeLayout(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						//region llQueries
						addView(LinearLayout(context).apply {
							layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
							margin(2f, 0f, 2f)
							orientation = LinearLayout.HORIZONTAL
							id = R.id.llQueries

							//region ivBackExplore
							addView(ImageView(context).apply {
								layoutParams = getLinearLayoutParams(dpToPx(20f), dpToPx(20f))
								setImageResource(R.drawable.ic_back)
								id = R.id.ivBackExplore
							})
							//endregion
							//region view
							addView(View(context).apply {
								layoutParams = getLinearLayoutParams(dpToPx(0.5f), -1)
								setBackgroundColor(Color.BLACK)
								margin(end = 5f)
							})
							//endregion

							//region current explore
							addView(LinearLayout(context).apply {
								layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
								orientation = LinearLayout.VERTICAL

								//region tvCurrentExplore
								addView(TextView(context).apply {
									layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
									textSize(16f)
									setTypeface(typeface, Typeface.BOLD)
									id = R.id.tvCurrentExplore
								})
								//endregion
								//region rvQueries
								addView(RecyclerView(context).apply {
									layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
									id = R.id.rvQueries
									isVerticalScrollBarEnabled = false
									isHorizontalScrollBarEnabled = false
								})
								//endregion
							})
						//endregion
						})
						//endregion
						//region rvExplore
						addView(RecyclerView(context).apply {
							layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
							isVerticalScrollBarEnabled = false
							isHorizontalScrollBarEnabled = false
							margin(2f, end = 2f)
							paddingAll(22f, right = 22f)
							id = R.id.rvExplore
						})
						//endregion
					})
					//endregion
					//region link data
					addView(TextView(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
						margin(10f, 5f, 0f, 5f)
						id = R.id.tvLink
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}

	fun getRowSuggestion(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			//region rvContentTop
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				id = R.id.rvContentTop
				//region tvContentTop
				addView(TextView(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					marginAll(5f)
					paddingAll(8f)
					id = R.id.tvContentTop
				})
				//endregion
			})
			//endregion
			//region bottom container
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
					addRule(RelativeLayout.BELOW, R.id.rvContentTop)
				}
				//region llContent
				addView(LinearLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					margin(top = 28f)
					paddingAll(5f)
					orientation = LinearLayout.VERTICAL
					id = R.id.llContent
					//region tvContent
					addView(TextView(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
						margin(5f)
						id = R.id.tvContent
					})
					//endregion
					//region llSuggestion
					addView(LinearLayout(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						paddingAll(5f)
						orientation = LinearLayout.VERTICAL
						id = R.id.llSuggestion
					})
					//endregion
				})
				//endregion
				//region rlDelete
				addView(RelativeLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					marginAll(5f)
					id = R.id.rlDelete
					//region ivReport
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(40f), dpToPx(40f))
						paddingAll(4f)
						setImageResource(R.drawable.ic_report)
						id = R.id.ivReport
					})
					//endregion
					//region ivDelete
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(40f), dpToPx(40f)).apply {
							addRule(RelativeLayout.END_OF, R.id.ivReport)
						}
						paddingAll(4f)
						setImageResource(R.drawable.ic_delete)
						id = R.id.ivDelete
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}

	private val aCharts = arrayListOf(
		Pair(R.id.ivTable, R.drawable.ic_table),
		Pair(R.id.ivPivot, R.drawable.ic_table_data),
		Pair(R.id.ivColumn, R.drawable.ic_column),
		Pair(R.id.ivBar, R.drawable.ic_bar),
		Pair(R.id.ivLine, R.drawable.ic_line),
		Pair(R.id.ivPie, R.drawable.ic_pie),
		Pair(R.id.ivHeat, R.drawable.ic_heat),
		Pair(R.id.ivBubble, R.drawable.ic_bubble),
		Pair(R.id.ivStackedBar, R.drawable.ic_stacked_bar),
		Pair(R.id.ivStackedColumn, R.drawable.ic_stacked_column),
		Pair(R.id.ivStackedArea, R.drawable.ic_stacked_area),
	)

	fun getRowWebView(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			//region rvContentTop
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				margin(5f)
				id = R.id.rvContentTop
				//region tvContentTop
				addView(TextView(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					paddingAll(8f)
					id = R.id.tvContentTop
				})
				//endregion
			})
			//endregion
			//region bottom container
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
					addRule(RelativeLayout.BELOW, R.id.rvContentTop)
				}

				addView(LinearLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					id = R.id.llParent
					orientation = LinearLayout.VERTICAL

					addView(RelativeLayout(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					})

					//region interpreted as content
					addView(TextView(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						setBackgroundColor(Color.RED)
						textSize(12f)
						id = R.id.tvInterpreter
					})
					//endregion
				})

				//region rvParent
				addView(RelativeLayout(context).apply {
					layoutParams = getRelativeLayoutParams(-1, dpToPx(300f))

					id = R.id.rvParent
					//region wbQuery
					addView(WebView(context).apply {
						layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
						marginAll(5f)
						id = R.id.wbQuery
					})
					//endregion
					//region rlLoad
					addView(RelativeLayout(context).apply {
						layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
						id = R.id.rlLoad
						//region KGifView
						addView(KGifView(context).apply {
							layoutParams = getRelativeLayoutParams(dpToPx(80f), dpToPx(80f)).apply {
								addRule(RelativeLayout.CENTER_IN_PARENT)
							}
						})
						//endregion
					})
					//endregion
					//region ivAlert
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(24f), dpToPx(24f)).apply {
							addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
						visibility = View.GONE
						setImageResource(R.drawable.ic_alert)
						id = R.id.ivAlert
					})
					//endregion
				})
				//endregion

				//region HorizontalScrollView
				addView(HorizontalScrollView(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					//region llCharts
					addView(LinearLayout(context).apply {
						layoutParams = FrameLayout.LayoutParams(-2, -2)
						orientation = LinearLayout.HORIZONTAL
						marginAll(5f)
						id = R.id.llCharts

						for (pair in aCharts)
						{
							addView(ImageView(context).apply {
								layoutParams = getLinearLayoutParams(dpToPx(40f), dpToPx(40f))
								paddingAll(4f)
								setImageResource(pair.second)
								visibility = View.GONE
								id = pair.first
							})
						}
					})
					//endregion
				})
				//endregion
				//region rlCharts
				addView(RelativeLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					marginAll(5f)
					id = R.id.rlCharts
					//region ivCharts
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(40f), dpToPx(40f))
						paddingAll(8f)
						setImageResource(R.drawable.ic_charts)
						id = R.id.ivCharts
					})
					//endregion
				})
				//endregion
				//region rlDelete
				addView(RelativeLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					marginAll(5f)
					id = R.id.rlDelete
					//region ImageView
					addView(ImageView(context).apply {
						layoutParams = 	getRelativeLayoutParams(dpToPx(40f), dpToPx(40f))
						paddingAll(8f)
						setImageResource(R.drawable.ic_points)
						id = R.id.ivPoints
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}
}