package chata.can.chata_ai.retrofit.core.extension

import android.view.View

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun View.customVisibility(isVisible: Boolean) {
	visibility = if (isVisible) View.VISIBLE else View.GONE
}