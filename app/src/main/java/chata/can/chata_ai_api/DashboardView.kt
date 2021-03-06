package chata.can.chata_ai_api

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.view.gif.KGifView

object DashboardView
{
	private const val MATCH_PARENT = -1
	private const val WRAP_CONTENT = -2

	private const val GRAVITY_CENTER = 17

	private fun layoutParams(): RelativeLayout.LayoutParams
	{
		return RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
	}

	private fun getLinearLayoutBase(context: Context): LinearLayout
	{
		return LinearLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
		}
	}

	private fun getRelativeLayoutBase(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
		}
	}

	private fun getHeaderDashboard(context: Context): LinearLayout
	{
		return getLinearLayoutBase(context).apply {
			context.run {
				val tvTitle = TextView(this).apply {
					layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
					setTextColor(SinglentonDrawer.currentAccent)
					setTypeface(typeface, Typeface.BOLD)
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
					id = R.id.tvTitle
				}
				val view = View(context).apply {
					layoutParams = LinearLayout.LayoutParams(dpToPx(100f), dpToPx(1f))
					setBackgroundColor(getParsedColor(R.color.short_line_dashboard))
					margin(bottom = 5f, top = 5f)
				}
				addView(tvTitle)
				addView(view)
			}
		}
	}

	fun getExecute(context: Context, idView: Int): TextView
	{
		return TextView(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, dpToPx(180f))
			gravity = GRAVITY_CENTER
			val message = context.getString(R.string.execute_run_dashboard)
			text = message
			id = idView
		}
	}

	//region row execute
	fun getRowExecute(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			context.run {
				val ll1 = getLinearLayoutBase(context).apply {
					margin(8f, 8f, 8f, 8f)
					paddingAll(8f)
					id = R.id.ll1

					val llHeader = getHeaderDashboard(context)
					val tvExecute = getExecute(context, R.id.tvExecute)
					val iView = View(context).apply {
						layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(1f))
						setBackgroundColor(getParsedColor(R.color.selected_gray))
						margin(start = 4f, end = 4f)
						id = R.id.iView
					}
					val tvExecute2 = getExecute(context, R.id.tvExecute2)

					addView(llHeader)
					addView(tvExecute)
					addView(iView)
					addView(tvExecute2)
				}
				addView(ll1)
			}
		}
	}
	//endregion
	//region row loading
	fun getChildLoading(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, dpToPx(180f))
			id = R.id.rlLoad

			val gifView = KGifView(context).apply {
				val layoutParams1 = RelativeLayout.LayoutParams(dpToPx(80f), dpToPx(80f))
				layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT)
				layoutParams = layoutParams1
			}
			addView(gifView)
		}
	}

	fun getRowLoading(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val rlLoad = getChildLoading(context)

				addView(llHeader)
				addView(rlLoad)
			}
			addView(ll1)
		}
	}
	//endregion
	//region row support
	fun getChildContent(context: Context) =
		context.run {
			TextView(context).apply {
				layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, dpToPx(80f))
				gravity = GRAVITY_CENTER
				setTextColor(getParsedColor( R.color.black))
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				id = R.id.tvContent
			}
		}

	fun getRowContent(context: Context, hasOption: Boolean = false): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val tvContent = getChildContent(context)

				addView(llHeader)
				addView(tvContent)
			}
			addView(ll1)
			if (hasOption)
				addView(getPointsAction(context))
		}
	}
	//endregion
	//region suggestion
	/**
	 * view with id main (llMainSuggestion)
	 */
	fun getChildSuggestion(context: Context): LinearLayout
	{
		return getLinearLayoutBase(context).apply {
			margin(start = 12f, end = 12f)
			paddingAll(5f)
			id = R.id.llMainSuggestion

			val tvContent = TextView(context).apply {
				layoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
				margin(5f, 5f, 5f, 5f)
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				id = R.id.tvContent
			}
			val llSuggestion = getLinearLayoutBase(context).apply {
				margin(5f, 5f, 5f, 5f)
				id = R.id.llSuggestion
			}
			addView(tvContent)
			addView(llSuggestion)
		}
	}

	fun getRowSuggestion(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val llRoot = getChildSuggestion(context)
				addView(llHeader)
				addView(llRoot)
			}
			addView(ll1)
		}
	}
	//endregion

	//region view dynamic
	fun getRowTwin(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			context.run {
				val ll1 = getLinearLayoutBase(this).apply {
					margin(8f, 8f, 8f, 8f)
					paddingAll(8f)
					id = R.id.ll1

					val llHeader = getHeaderDashboard(context)
					val ll1 = getRelativeLayoutBase(context).apply {
						id = R.id.lls1
					}

					val iView = View(context).apply {
						layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(1f))
						setBackgroundColor(getParsedColor(R.color.separator_dashboard))
						margin(bottom = 6f, top = 6f)
						id = R.id.iView
					}

					val ll2 = getRelativeLayoutBase(context).apply {
						id = R.id.lls2
					}

					addView(llHeader)
					addView(ll1)
					addView(iView)
					addView(ll2)
				}
				addView(ll1)
			}
		}
	}
	//endregion

	//region receiver data for generate views for dual holder
	/**
	 * view with id main (rlWebView)
	 */
	fun getChildWebView(context: Context, newId: Int = R.id.rlWebView): RelativeLayout
	{
		val pData = if (newId == R.id.rlWebView)
			 Triple(R.id.webView, R.id.rlLoad, R.id.ivOption)
		else Triple(R.id.webView2, R.id.rlLoad2, R.id.ivOption2)

		return RelativeLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, dpToPx(240f))
			id = newId
			val webView = WebView(context).apply {
				layoutParams = layoutParams()
				id = pData.first
			}
			val rlLoad = RelativeLayout(context).apply {
				layoutParams = layoutParams()
				id = pData.second

				val gifView = KGifView(context).apply {
					val layoutParams1 = RelativeLayout.LayoutParams(dpToPx(80f), dpToPx(80f))
					layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT)
					layoutParams = layoutParams1
				}
				addView(gifView)
			}
			addView(webView)
			addView(rlLoad)
			addView(ImageView(context).apply {
				layoutParams = RelativeLayout.LayoutParams(dpToPx(42f), dpToPx(42f)).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
					addRule(RelativeLayout.ALIGN_BOTTOM, pData.first)
				}
				paddingAll(8f)
				setImageResource(R.drawable.ic_points)
				setColorFilter(SinglentonDrawer.currentAccent)
				backgroundWhiteGray()
				id = pData.third
			})
		}
	}

	fun getRowWebView(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val rlWebView = getChildWebView(context)

				addView(llHeader)
				addView(rlWebView)
			}
			addView(ll1)
		}
	}

	private fun getPointsAction(context: Context) = ImageView(context).apply {
		layoutParams = RelativeLayout.LayoutParams(dpToPx(42f), dpToPx(42f)).apply {
			addRule(RelativeLayout.ALIGN_PARENT_END)
			addRule(RelativeLayout.ALIGN_BOTTOM, R.id.ll1)
		}
		margin(0f,0f,12f,4f)
		paddingAll(8f)
		setImageResource(R.drawable.ic_points)
		id = R.id.ivOption
	}
	//endregion
}