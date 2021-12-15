package chata.can.chata_ai.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup

fun View.marginAll(margin: Float)
{
	margin(margin, margin, margin, margin)
}

fun View.margin(
	start: Float? = null,
	top: Float? = null,
	end: Float? = null,
	bottom: Float? = null)
{
	layoutParams<ViewGroup.MarginLayoutParams>
	{
		start?.run { marginStart = dpToPx(this) }
		top?.run { topMargin = dpToPx(this) }
		end?.run { marginEnd = dpToPx(this) }
		bottom?.run { bottomMargin = dpToPx(this) }
	}
}

fun View.paddingAll(padding: Float)
{
	val dpVal = dpToPx(padding)
	setPadding(dpVal, dpVal, dpVal, dpVal)
}

fun View.paddingAll(
	left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null)
{
	setPadding(
		left?.let { dpToPx(it) } ?: 0,
		top?.let { dpToPx(it) } ?: 0,
		right?.let { dpToPx(it) } ?: 0,
		bottom?.let { dpToPx(it) } ?: 0)
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit)
{
	if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)

fun Context.dpToPx(dp: Float): Int
{
	val scale = resources.displayMetrics.density
	return (dp * scale + 0.6).toInt()
}