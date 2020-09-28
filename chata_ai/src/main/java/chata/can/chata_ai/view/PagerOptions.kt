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

	fun init()
	{
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = inflater.inflate(R.layout.view_pager_options, nullParent)

		llMenu = findViewById(R.id.llMenu)
		rlChat = findViewById(R.id.rlChat)
		ivChat = findViewById(R.id.ivChat)
		rlTips = findViewById(R.id.rlTips)
		ivTips = findViewById(R.id.ivTips)
		rlNotify = findViewById(R.id.rlNotify)
		ivNotify = findViewById(R.id.ivNotify)
		frmLocal = findViewById(R.id.frmLocal)
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
				R.id.rlChat -> {}
				R.id.rlTips -> {}
				R.id.rlNotify -> {}
			}
		}
	}

	private fun setListener()
	{
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
}