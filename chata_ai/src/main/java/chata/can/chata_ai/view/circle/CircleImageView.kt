package chata.can.chata_ai.view.circle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import kotlin.math.min
import kotlin.math.pow

/**
 * @author https://github.com/hdodenhof
 */
class CircleImageView: AppCompatImageView
{
	private val scaleType = ScaleType.CENTER_CROP
	private val bitmapConfig = Bitmap.Config.ARGB_8888
	private var colorDrawableDimension = 2

	private var defaultBorderWidth = 0
	private var defaultBorderColor = Color.BLACK
	private var defaultCircleBackgroundColor = Color.TRANSPARENT
	private var defaultBorderOverlay = false

	private var mDrawableRect = RectF()
	private var mBorderRect = RectF()

	private var mShaderMatrix = Matrix()
	private var mBitmapPaint = Paint()
	private var mBorderPaint = Paint()
	private var mCircleBackgroundPaint = Paint()

	private var mBorderColor = defaultBorderColor
	private var mBorderWidth = defaultBorderWidth
	private var mCircleBackgroundColor = defaultCircleBackgroundColor

	private var mBitmap: Bitmap ?= null
	private lateinit var mBitmapShader: BitmapShader
	private var mBitmapWidth = 0
	private var mBitmapHeight = 0

	private var mDrawableRadius = 0f
	private var mBorderRadius = 0f

	private var mColorFilter: ColorFilter ?= null

	private var mReady = false
	private var mSetupPending = false
	private var mBorderOverlay = false
	private var mDisableCircularTransformation = false

	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int)
		: super(context, attrs, defStyle)
	{
		with(context.obtainStyledAttributes(
			attrs, R.styleable.CircleImageView, defStyle, 0))
		{
			mBorderWidth = getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, defaultBorderWidth)
			mBorderColor = getColor(R.styleable.CircleImageView_civ_border_color, defaultBorderColor)
			mBorderOverlay = getBoolean(R.styleable.CircleImageView_civ_border_overlay, defaultBorderOverlay)
			mCircleBackgroundColor = getColor(R.styleable.CircleImageView_civ_circle_background_color, defaultCircleBackgroundColor)
			recycle()
			init()
		}
	}

	private fun init()
	{
		super.setScaleType(scaleType)
		mReady = true

		outlineProvider = OutlineProvider()

		if (mSetupPending)
		{
			setup()
			mSetupPending = false
		}
	}

	override fun getScaleType() = scaleType

	override fun setScaleType(scaleType: ScaleType?)
	{
		if (scaleType != scaleType)
		{
			throw IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType))
		}
	}

	override fun setAdjustViewBounds(adjustViewBounds: Boolean)
	{
		if (adjustViewBounds)
		{
			throw IllegalArgumentException("adjustViewBounds not supported.")
		}
	}

	override fun onDraw(canvas: Canvas?)
	{
		if (mDisableCircularTransformation)
		{
			super.onDraw(canvas)
			return
		}

		if (mBitmap == null)
		{
			return
		}

		if (mCircleBackgroundColor != Color.TRANSPARENT)
		{
			canvas?.drawCircle(
				mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mCircleBackgroundPaint)
		}
		canvas?.drawCircle(
			mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mBitmapPaint)
		if (mBorderWidth > 0)
		{
			canvas?.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint)
		}
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
	{
		super.onSizeChanged(w, h, oldw, oldh)
		setup()
	}

	override fun setPadding(left: Int, top: Int, right: Int, bottom: Int)
	{
		super.setPadding(left, top, right, bottom)
		setup()
	}

	override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int)
	{
		super.setPaddingRelative(start, top, end, bottom)
		setup()
	}

	//fun getBorderColor() = mBorderColor

	/*fun setBorderColor(borderColor: Int)
	{
		if (borderColor == mBorderColor)
		{
			return
		}

		mBorderColor = borderColor
		mBorderPaint.color = mBorderColor
		invalidate()
	}*/

//	fun getCircleBackgroundColor() = mCircleBackgroundColor

	private fun setCircleBackgroundColor(circleBackgroundColor: Int)
	{
		if (circleBackgroundColor == mCircleBackgroundColor)
		{
			return
		}

		mCircleBackgroundColor = circleBackgroundColor
		mCircleBackgroundPaint.color = circleBackgroundColor
		invalidate()
	}

	fun setCircleBackgroundColorResource(circleBackgroundRes: Int)
	{
		context?.let {
			setCircleBackgroundColor(it.getParsedColor(circleBackgroundRes))
		}
	}

	//fun getBorderWidth() = mBorderWidth

	/*fun setBorderWidth(borderWidth: Int)
	{
		if (borderWidth == mBorderWidth)
		{
			return
		}
		mBorderWidth = borderWidth
		setup()
	}*/

	//fun isBorderOverlay() = mBorderOverlay

	/*fun setBorderOverlay(borderOverlay: Boolean)
	{
		if (borderOverlay == mBorderOverlay)
		{
			return
		}
		mBorderOverlay = borderOverlay
		setup()
	}*/

	//fun isDisableCircularTransformation() = mDisableCircularTransformation

	/*fun setDisableCircularTransformation(disableCircularTransformation: Boolean)
	{
		if (mDisableCircularTransformation == disableCircularTransformation)
		{
			return
		}
		mDisableCircularTransformation = disableCircularTransformation
		initializeBitmap()
	}*/

	override fun setImageBitmap(bm: Bitmap?)
	{
		super.setImageBitmap(bm)
		initializeBitmap()
	}

	override fun setImageDrawable(drawable: Drawable?)
	{
		super.setImageDrawable(drawable)
		initializeBitmap()
	}

	override fun setImageResource(resId: Int)
	{
		super.setImageResource(resId)
		initializeBitmap()
	}

	override fun setImageURI(uri: Uri?)
	{
		super.setImageURI(uri)
		initializeBitmap()
	}

	override fun setColorFilter(cf: ColorFilter?)
	{
		if (cf == mColorFilter)
		{
			return
		}
		cf?.let { mColorFilter = it }
		applyColorFilter()
		invalidate()
	}

	override fun getColorFilter() = mColorFilter

	private fun applyColorFilter()
	{
		mBitmapPaint.colorFilter = mColorFilter
	}

	private fun getBitmapFromDrawable(drawable: Drawable): Bitmap?
	{
		if (drawable is BitmapDrawable)
		{
			return drawable.bitmap
		}

		try {
			val bitmap = if (drawable is ColorDrawable)
			{
				Bitmap.createBitmap(colorDrawableDimension, colorDrawableDimension, bitmapConfig)
			}
			else
			{
				Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, bitmapConfig)
			}

			val canvas = Canvas(bitmap)
			drawable.setBounds(0, 0, canvas.width, canvas.height)
			drawable.draw(canvas)
			return bitmap
		}
		catch(ex: Exception)
		{
			ex.printStackTrace()
			return null
		}
	}

	private fun initializeBitmap()
	{
		mBitmap = if (mDisableCircularTransformation)
		{
			null
		}
		else
		{
			getBitmapFromDrawable(drawable)
		}
		setup()
	}

	private fun setup()
	{
		if (!mReady)
		{
			mSetupPending = true
			return
		}

		if (width == 0 && height == 0)
		{
			return
		}

		if (mBitmap == null)
		{
			invalidate()
			return
		}

		mBitmap?.let {
				mBitmap ->
			mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

			mBitmapPaint.isAntiAlias = true
			mBitmapPaint.isDither = true
			mBitmapPaint.isFilterBitmap = true
			mBitmapPaint.shader = mBitmapShader

			mBorderPaint.style = Paint.Style.STROKE
			mBorderPaint.isAntiAlias = true
			mBorderPaint.color = mBorderColor
			mBorderPaint.strokeWidth = mBorderWidth.toFloat()

			mCircleBackgroundPaint.style = Paint.Style.FILL
			mCircleBackgroundPaint.isAntiAlias = true
			mCircleBackgroundPaint.color = mCircleBackgroundColor

			mBitmapHeight = mBitmap.height
			mBitmapWidth = mBitmap.width

			mBorderRect.set(calculateBounds())
			mBorderRadius = min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f)

			mDrawableRect.set(mBorderRect)
			if (!mBorderOverlay && mBorderWidth > 0)
			{
				mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
			}
			mDrawableRadius = min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)

			applyColorFilter()
			updateShaderMatrix()
			invalidate()
		}
	}

	private fun calculateBounds(): RectF
	{
		val availableWidth = width - paddingLeft - paddingRight
		val availableHeight = height - paddingTop - paddingBottom

		val sideLength = min(availableWidth, availableHeight)

		val left = paddingLeft + (availableWidth - sideLength) / 2f
		val top = paddingTop + (availableHeight - sideLength) / 2f

		return RectF(left, top, left + sideLength, top + sideLength)
	}

	private fun updateShaderMatrix()
	{
		val scale: Float
		var dx = 0f
		var dy = 0f

		mShaderMatrix.set(null)

		if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight)
		{
			scale = mDrawableRect.height() / mBitmapHeight
			dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
		}
		else
		{
			scale = mDrawableRect.width() / mBitmapWidth
			dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
		}

		mShaderMatrix.setScale(scale, scale)
		mShaderMatrix.postTranslate((dx + 0.5f) + mDrawableRect.left, (dy + 0.5f) + mDrawableRect.top)

		mBitmapShader.setLocalMatrix(mShaderMatrix)
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent?): Boolean
	{
		if (mDisableCircularTransformation)
		{
			return super.onTouchEvent(event)
		}

		return inTouchableArea(event?.x ?: 0f, event?.y ?: 0f) && super.onTouchEvent(event)
	}

	private fun inTouchableArea(x: Float, y: Float): Boolean
	{
		if (mBorderRect.isEmpty)
		{
			return true
		}
		return (x - mBorderRect.centerX().toDouble()).pow(2) + (y - mBorderRect.centerY().toDouble()).pow(2) <= mBorderRadius.toDouble().pow(2)
	}

	inner class OutlineProvider: ViewOutlineProvider()
	{
		override fun getOutline(view: View?, outline: Outline?)
		{
			if (mDisableCircularTransformation)
			{
				BACKGROUND.getOutline(view, outline)
			}
			else
			{
				val bounds = Rect()
				mBorderRect.roundOut(bounds)
				outline?.setRoundRect(bounds, bounds.width() / 2.0f)
			}
		}
	}
}