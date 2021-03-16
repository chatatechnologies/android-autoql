package chata.can.chata_ai_api.test

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R

class TestPopupActivity1: BaseActivity(R.layout.main)
{
	private var p: Point ?= null
	private var button: Button ?= null

	override fun onCreateView()
	{
		findViewById<Button>(R.id.show_popup)?.apply {
			setOnClickListener {
				p?.let {
					showPopup(this@TestPopupActivity1)
				}
			}
		}
	}

	override fun onWindowFocusChanged(hasFocus: Boolean)
	{
		val location = IntArray(2)
		button?.getLocationOnScreen(location)
		p = Point(location[0], location[1])
	}

	private fun showPopup(context: Activity)
	{
		val popupWidth = 200
		val popupHeight = 150
		// Inflate the popup_layout.xml
		val viewGroup = context.findViewById<LinearLayout>(R.id.popup)
		val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup)
		// Creating the PopupWindow
		val popup = PopupWindow(this)
		popup.contentView = layout
		popup.height = popupHeight
		popup.width = popupWidth
		popup.isFocusable = true
		// Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
		val OFFSET_X = 30
		val OFFSET_Y = 30
		// Clear the default translucent background
		popup.setBackgroundDrawable(BitmapDrawable())
		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p?.x ?: 0 + OFFSET_X, p?.y ?: 0 + OFFSET_Y)
		// Getting a reference to Close button, and close the popup when clicked.
		val close = layout.findViewById<Button>(R.id.close)
		close.setOnClickListener {
			popup.dismiss()
		}
	}
}