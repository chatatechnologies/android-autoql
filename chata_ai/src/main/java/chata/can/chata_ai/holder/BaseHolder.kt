package chata.can.chata_ai.holder

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.setColorFilter
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.request.drillDown.DrillDownPresenter

open class BaseHolder(
	itemView: View,
	private val view: ChatAdapterContract? = null,
	private val chatView: ChatContract.View? = null
): Holder(itemView), View.OnClickListener
{
	val tvContentTop: TextView = itemView.findViewById(R.id.tvContentTop)

	val tvContent: TextView = itemView.findViewById(R.id.tvContent)

	private val rlDelete = itemView.findViewById<View>(R.id.rlDelete) ?: null
	private val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete) ?: null

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = ContextCompat.getColor(context, R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val accentColor = ContextCompat.getColor(context, ThemeColor.currentColor.drawerAccentColor)
			val queryDrawable = DrawableBuilder.setGradientDrawable(accentColor,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)
		}

		val gray = ContextCompat.getColor(
			tvContent.context,
			ThemeColor.currentColor.drawerColorPrimary)
		tvContent.setTextColor(gray)
		tvContent.backgroundGrayWhite()

		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}
		ivDelete?.setColorFilter()

		val animation = AnimationUtils.loadAnimation(tvContent.context, R.anim.scale)
		tvContent.startAnimation(animation)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		when(item)
		{
			is ChatData ->
			{
				if (item.message.isNotEmpty())
				{
					rlDelete?.visibility = View.GONE
					tvContent.text = item.message

					tvContentTop.visibility = View.GONE
				}
				else
				{
					item.simpleQuery?.let {
						if (it is QueryBase)
						{
							processQueryBase(it)
						}
					}
				}
			}
			is QueryBase ->
			{
				processQueryBase(item)
			}
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.rlDelete ->
				{
					//region delete query
					view?.deleteQuery(adapterPosition)
					//endregion
				}
				else -> {}
			}
		}
	}

	private fun processQueryBase(simpleQuery: QueryBase)
	{
		tvContentTop.text = simpleQuery.query
		val message = when
		{
			simpleQuery.isSimpleText ->
			{
				rlDelete?.visibility = View.VISIBLE
				when
				{
					simpleQuery.contentHTML.isNotEmpty() ->
					{
						tvContent.setOnClickListener {
							DrillDownPresenter(simpleQuery, chatView).postDrillDown()
						}
						simpleQuery.isLoadingHTML = false
						simpleQuery.contentHTML
					}
					else -> simpleQuery.simpleText
				}
			}
			simpleQuery.aRows.size == 0 ->
			{
				if (simpleQuery.message.isNotEmpty())
				{
					rlDelete?.visibility = View.VISIBLE
					simpleQuery.message
				}
				else
				{
					"Uh oh.. It looks like you don't have access to this resource. Please double check that all the required authentication fields are provided."
				}
			}
			else -> "display type not recognized: ${simpleQuery.displayType}"
		}

		tvContent.text = message
	}
}