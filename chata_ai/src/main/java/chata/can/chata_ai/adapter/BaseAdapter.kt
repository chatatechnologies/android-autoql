package chata.can.chata_ai.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.nullValue

open class BaseAdapter(
	private val model: BaseModelList<*>,
	private val listener: OnItemClickListener? = null
) :RecyclerView.Adapter<Holder>()
{
	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		with(holder)
		{
			onPaint()
			model.onBindAtPosition(this, position, listener)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return BaseHolder(layoutInflater.inflate(R.layout.row_base, nullValue))
	}

	override fun getItemCount(): Int
	{
		return model.countData()
	}

	private fun getRowBase(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -1)
			//region top container
			addView(RelativeLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
				}
				paddingAll(left = 50f)
				//region tv content top
				addView(TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-2,-2)
					marginAll(5f)
					paddingAll(8f)
				})
				//endregion
				id = R.id.rvContentTop
			})
			//endregion
			//region middle container
			addView(LinearLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-2, -2).apply {
					addRule(RelativeLayout.BELOW, R.id.rvContentTop)
				}
				orientation = LinearLayout.VERTICAL
				gravity = Gravity.END
				margin(end = 50f)
				id = R.id.llMainBase
//				region actions
				addView(RelativeLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-2, -2)
					margin(start = 5f, end = 5f)
//					region report button
					addView(ImageView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(dpToPx(40f), dpToPx(40f))
						paddingAll(4f)
						id = R.id.ivReport
						setImageResource(R.drawable.ic_report)
					})
//					endregion
//					region delete button
					addView(ImageView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(dpToPx(40f), dpToPx(40f)).apply {
							addRule(RelativeLayout.END_OF, R.id.ivReport)
						}
						paddingAll(4f)
						id = R.id.ivDelete
						setImageResource(R.drawable.ic_delete)
					})
//					endregion
//					region options
					addView(ImageView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(dpToPx(40f), dpToPx(40f)).apply {
							addRule(RelativeLayout.END_OF, R.id.ivDelete)
						}
						paddingAll(4f)
						id = R.id.ivPoints
						setImageResource(R.drawable.ic_points)
					})
//					endregion
					id = R.id.rlDelete
				})
//				endregion

//				region content
				addView(RelativeLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-2, -2)
					paddingAll(left = 4f, right = 4f)
					addView(TextView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(-2, -2)
						margin(1f)
						paddingAll(8f)
						id = R.id.tvContent
					})
					id = R.id.rvContent
				})
//				endregion
			})
			//endregion
		}
	}
}