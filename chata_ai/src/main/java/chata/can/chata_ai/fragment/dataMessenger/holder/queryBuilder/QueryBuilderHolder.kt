package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.pager.PagerActivity
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter.QueryBuilderAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList

class QueryBuilderHolder(
	view: View,
	private val pagerActivity: Activity): Holder(view)
{
	private var llContent = view.findViewById<LinearLayout>(R.id.llContent) ?: null
	private var tvMsg = view.findViewById<TextView>(R.id.tvMsg) ?: null
	private var tvLink = view.findViewById<TextView>(R.id.tvLink) ?: null

	private var ivBackExplore = view.findViewById<ImageView>(R.id.ivBackExplore) ?: null

	private var rvExplore = view.findViewById<RecyclerView>(R.id.rvExplore)

	private val tmpClick2 = view.findViewById<View>(R.id.tmpClick2)

	private var model: BaseModelList<String> ?= null
	private var qbAdapter: QueryBuilderAdapter ?= null

	override fun onPaint()
	{
		llContent?.run {
			backgroundGrayWhite()
			val animation = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animation)
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		//region set content
		val linkMessage = "Use Explore Queries to further explore the possibilities."
		val spannable = SpannableString(linkMessage)
		val clickable = object: ClickableSpan()
		{
			override fun onClick(widget: View)
			{
				(pagerActivity as? PagerActivity)?.run {
					selectPage(1)
				}
			}

			override fun updateDrawState(textPaint: TextPaint)
			{
				textPaint.run {
					try {
						color = Color.parseColor("#0000EE")
					}
					finally {
						bgColor = Color.parseColor("#FFFFFF")
						isUnderlineText = false
					}
				}
			}
		}
		spannable.setSpan(clickable, 4, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		tvLink?.run {
			text = spannable
			movementMethod = LinkMovementMethod.getInstance()
		}
		//endregion
		initList()

		ivBackExplore?.setOnClickListener {
			ObjectAnimator.ofFloat(rvExplore, "translationX", 0f).run {
				duration = 500
				start()
			}
		}
	}

	private fun setData() = arrayListOf("Sales", "Items", "Expenses", "Purchase Orders")

	private fun initList()
	{
		model = BaseModelList()
		model?.let {
			qbAdapter = QueryBuilderAdapter(it, object: OnItemClickListener
			{
				override fun onItemClick(any: Any)
				{
					ObjectAnimator.ofFloat(rvExplore, "translationX", -widthParent).run {
						duration = 500
						start()
					}
				}
			})
			it.addAll(setData())
		}

		rvExplore?.layoutManager = LinearLayoutManager(pagerActivity)
		rvExplore?.adapter = qbAdapter
	}

	private val widthParent
		get() = llContent?.measuredWidth?.toFloat() ?: 0f
}