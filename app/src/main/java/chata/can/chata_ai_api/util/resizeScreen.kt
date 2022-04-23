package chata.can.chata_ai_api.util

import android.os.Build
import android.view.Window
import android.view.WindowManager

@Suppress("deprecation")
fun resizeWindow(window: Window) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		window.setDecorFitsSystemWindows(false)
	} else {
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
	}
}