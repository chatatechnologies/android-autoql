package chata.can.chata_ai.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

class PagerOptions: RelativeLayout, View.OnClickListener
{
	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyle: Int)
		: super(context, attrs, defStyle) { init() }

	private lateinit var llMenu: View
	private lateinit var rlChat: View
	private lateinit var ivChat: ImageView
	private lateinit var rlTips: View
	private lateinit var ivTips: ImageView
	private lateinit var rlNotify: View
	private lateinit var ivNotify: ImageView
	private lateinit var frmLocal: View

	private var rlSelected: View ?= null
	private var ivSelected: ImageView ?= null

	fun init()
	{
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = inflater.inflate(R.layout.view_pager_options, nullParent)

		view.run {
			llMenu = findViewById(R.id.llMenu)
			rlChat = findViewById(R.id.rlChat)
			ivChat = findViewById(R.id.ivChat)
			rlTips = findViewById(R.id.rlTips)
			ivTips = findViewById(R.id.ivTips)
			rlNotify = findViewById(R.id.rlNotify)
			ivNotify = findViewById(R.id.ivNotify)
			frmLocal = findViewById(R.id.frmLocal)
		}

		rlSelected = rlChat
		ivSelected = ivChat
		setListener()
		setColors()

		addView(view)
	}

	fun setStatusGUI(isVisible: Boolean)
	{
		val iVisible = if (isVisible) View.VISIBLE else View.GONE
		llMenu.visibility = iVisible
		frmLocal.visibility = iVisible
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.llMenu ->
				{
					BubbleHandle.isOpenChat = false
					BubbleHandle.instance.isVisible = true
					setStatusGUI(false)
				}
				R.id.rlChat -> { changeColor(rlChat, ivChat) }
				R.id.rlTips -> { changeColor(rlTips, ivTips) }
				R.id.rlNotify -> { changeColor(rlNotify, ivNotify) }
			}
		}
	}

	private fun setListener()
	{
		llMenu.setOnClickListener(this)
		rlChat.setOnClickListener(this)
		rlTips.setOnClickListener(this)
		rlNotify.setOnClickListener(this)
	}

	private fun setColors()
	{
		context?.run {
			ivChat.setColorFilter(getParsedColor(R.color.black))
			ivTips.setColorFilter(getParsedColor(R.color.white))
			ivNotify.setColorFilter(getParsedColor(R.color.white))
		}
	}

	private fun changeColor(rlNew: View, ivNew: ImageView)
	{
		context?.run {
			if (rlSelected != rlNew && ivSelected != ivNew)
			{
				rlSelected?.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
				ivSelected?.setColorFilter(getParsedColor(R.color.white))
				rlNew.setBackgroundColor(getParsedColor(R.color.white))
				ivNew.setColorFilter(getParsedColor(R.color.black))
				rlSelected = rlNew
				ivSelected = ivNew
			}
		}
	}
}