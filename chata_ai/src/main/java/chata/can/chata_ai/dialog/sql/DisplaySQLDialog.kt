package chata.can.chata_ai.dialog.sql

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.animationAlert.AnimationAlert

class DisplaySQLDialog(
	context: Context,
	private val query: String
): BaseDialog(context, R.layout.dialog_display_sql), View.OnClickListener
{
	private lateinit var rlParent: View
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var vBorder: View
	private lateinit var etQuery: TextView
	private lateinit var ivCopy: ImageView
	private lateinit var animationAlert: AnimationAlert

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	override fun setColors()
	{
		ivCancel.setColorFilter(
			context.getParsedColor(R.color.chata_drawer_background_color_dark))
		ThemeColor.currentColor.run {
			context.run {
				ivCancel.setColorFilter(getParsedColor(R.color.chata_drawer_background_color_dark))
				rlParent.setBackgroundColor(getParsedColor(drawerBackgroundColor))
				tvTitle.setTextColor(getParsedColor(drawerTextColorPrimary))
				vBorder.setBackgroundColor(getParsedColor(drawerBorderColor))
				etQuery.setTextColor(getParsedColor(drawerTextColorPrimary))
				etQuery.background = DrawableBuilder.setGradientDrawable(
					getParsedColor(drawerColorSecondary),
					1f,
					1,
					getParsedColor(drawerBorderColor))
				ivCopy.setColorFilter(getParsedColor(drawerTextColorPrimary))
				ivCopy.background = DrawableBuilder.setGradientDrawable(
					getParsedColor(drawerBackgroundColor),
					1f,
					1,
					getParsedColor(drawerBorderColor))
			}
		}
	}

	override fun setViews()
	{
		rlParent = findViewById(R.id.rlParent)
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		vBorder = findViewById(R.id.vBorder)
		etQuery = findViewById(R.id.etQuery)
		ivCopy = findViewById(R.id.ivCopy)
		animationAlert = AnimationAlert(findViewById(R.id.rlAlert))
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel -> dismiss()
				R.id.ivCopy ->
				{
					val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
					val clip = ClipData.newPlainText("", query)
					clipboard.setPrimaryClip(clip)
					showAlert()
				}
				else -> {}
			}
		}
	}

	private fun setData()
	{
		tvTitle.setText(R.string.generated_sql)
		etQuery.text = formatterSQL(query)

		ivCancel.setOnClickListener(this)
		ivCopy.setOnClickListener(this)
	}

	private fun showAlert()
	{
		animationAlert.run {
			setText("Successfully copied generated query to clipboard!")
			setResource(R.drawable.ic_done)
			showAlert()
			Handler(Looper.getMainLooper()).postDelayed({ hideAlert() }, 1500)
		}
	}

	private val aKeywords = arrayListOf("select", "from", "where")
	private fun formatterSQL(query: String): String
	{
		var iHead = -1
		val sb = StringBuilder("")
		while (iHead < query.length)
		{
			//search white
			val index = query.indexOf(" ", iHead + 1)
			iHead = if (index != -1)
			{
				val word = query.substring(iHead + 1, index)
				val extra = if (word in aKeywords) "\n" else ""
				sb.append(
					if (extra.isEmpty()) "\t$word"
					else "$extra$word$extra")
				index
			}
			else
			{
				sb.append(query.substring(iHead, query.length))
				query.length
			}
		}
		return sb.toString()
	}
}