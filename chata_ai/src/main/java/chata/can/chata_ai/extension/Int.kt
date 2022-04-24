package chata.can.chata_ai.extension

import android.content.Context

fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()