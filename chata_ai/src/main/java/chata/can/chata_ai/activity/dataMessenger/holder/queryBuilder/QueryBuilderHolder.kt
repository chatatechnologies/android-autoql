package chata.can.chata_ai.activity.dataMessenger.holder.queryBuilder

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.DataMessengerActivity
import chata.can.chata_ai.activity.exploreQueries.ExploreQueriesActivity
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setAnimator
import chata.can.chata_ai.activity.dataMessenger.holder.queryBuilder.adapter.OptionAdapter
import chata.can.chata_ai.activity.dataMessenger.holder.queryBuilder.adapter.QueryAdapter
import chata.can.chata_ai.activity.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.color.ThemeColor

class QueryBuilderHolder(
	view: View,
	private val pagerActivity: Activity,
	private val servicePresenter: ChatServicePresenter
): Holder(view)
{
	private var llContent = view.findViewById<LinearLayout>(R.id.llContent) ?: null
	private val tvMsg = view.findViewById<TextView>(R.id.tvMsg) ?: null
	private var tvLink = view.findViewById<TextView>(R.id.tvLink) ?: null

	private var ivBackExplore = view.findViewById<ImageView>(R.id.ivBackExplore) ?: null
	private var llQueries = view.findViewById<View>(R.id.llQueries)
	private var tvCurrentExplore = view.findViewById<TextView>(R.id.tvCurrentExplore)
	private var rvExplore = view.findViewById<RecyclerView>(R.id.rvExplore)
	private var rvQueries = view.findViewById<RecyclerView>(R.id.rvQueries)

	private var modelRoot: BaseModelList<String> ?= null
	private var modelQueries: BaseModelList<String> ?= null
	private var qbAdapter: OptionAdapter ?= null
	private var queriesAdapter: QueryAdapter ?= null

	private var heightRoot = 0

	override fun onPaint()
	{
		llContent?.run {
			backgroundGrayWhite()
			context.run {
				val white = getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
				val gray = getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
				tvLink?.setTextColor(gray)
				tvMsg?.setTextColor(gray)
				tvCurrentExplore?.setTextColor(gray)

				rvExplore?.setBackgroundColor(white)
				ivBackExplore?.setColorFilter(gray, PorterDuff.Mode.SRC_ATOP)

				val animation = AnimationUtils.loadAnimation(this, R.anim.scale)
				startAnimation(animation)
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		//region set content
		val linkMessage = "Use💡Explore Queries to further explore the possibilities."
		val spannable = SpannableString(linkMessage)
		val clickable = object: ClickableSpan()
		{
			override fun onClick(widget: View)
			{
				(pagerActivity as? DataMessengerActivity)?.run {
					startActivity(Intent(this, ExploreQueriesActivity::class.java))
					overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_down)
				}
			}

			override fun updateDrawState(textPaint: TextPaint)
			{
				textPaint.run {
					try {
						tvLink?.context?.let {
							color = it.getParsedColor(ThemeColor.currentColor.drawerAccentColor)
						}
					}
					finally {
						bgColor = Color.parseColor("#FFFFFF")
						isUnderlineText = false
					}
				}
			}
		}
		spannable.setSpan(clickable, 5, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		tvLink?.run {
			text = spannable
			movementMethod = LinkMovementMethod.getInstance()
		}
		//endregion
		initListRoot()
		initListQueries()
		initHeightRoot()

		ivBackExplore?.setOnClickListener {
			configViews(true)
		}
	}

	private fun mainData() = QueryBuilderData.aMainData

	private fun secondaryData(path: String): ArrayList<String>
	{
		return QueryBuilderData.mMainQuery[path] ?: arrayListOf()
	}

	private fun initListRoot()
	{
		modelRoot = BaseModelList()
		modelRoot?.let {
			qbAdapter = OptionAdapter(it, object: OnItemClickListener
			{
				override fun onItemClick(any: Any)
				{
					if (any is String)
					{
						tvCurrentExplore?.text = any
						configViews(false)
						setListQueries(any)
					}
				}
			})
			it.addAll(mainData())
		}

		rvExplore?.layoutManager = LinearLayoutManager(pagerActivity)
		rvExplore?.adapter = qbAdapter
	}

	private fun initHeightRoot()
	{

		rvExplore?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener
		{
			override fun onGlobalLayout()
			{
				rvExplore?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
				if (heightRoot == 0)
				{
					heightRoot = rvExplore?.measuredHeight ?: 0
				}
				configViews(true)
			}
		})
	}

	private fun configViews(isPrimary: Boolean)
	{
		var visible1 = View.VISIBLE
		var visible2 = View.GONE
		var widthAnimator = widthParent
		var heightFinal = heightRoot

		if (!isPrimary)
		{
			visible1 = View.GONE
			visible2 = View.VISIBLE
			widthAnimator = 0f
			heightFinal = -2
		}

		rvExplore?.visibility = visible1
		llQueries?.visibility = visible2
		llQueries?.setAnimator(widthAnimator, "translationX")
		llQueries?.layoutParams = (llQueries?.layoutParams as? RelativeLayout.LayoutParams)?.apply {
			height = heightFinal
		}
	}

	private fun initListQueries()
	{
		modelQueries = BaseModelList()
		modelQueries?.let {
			model ->
			val first = modelRoot?.get(0) ?: ""
			secondaryData(first).let {
				model.addAll(it)
			}
			queriesAdapter = QueryAdapter(model, object: OnItemClickListener
			{
				override fun onItemClick(any: Any)
				{
					configViews(true)
					if (any is String)
					{
						servicePresenter.getQuery(any)
					}
				}
			})
		}
		rvQueries?.layoutManager = LinearLayoutManager(pagerActivity)
		rvQueries?.adapter = queriesAdapter
	}

	private fun setListQueries(wordExplore: String)
	{
		modelQueries?.clear()
		secondaryData(wordExplore).let {
			modelQueries?.addAll(it)
		}
		queriesAdapter?.notifyDataSetChanged()
	}

	private val widthParent
		get() = llContent?.measuredWidth?.toFloat() ?: 0f
}