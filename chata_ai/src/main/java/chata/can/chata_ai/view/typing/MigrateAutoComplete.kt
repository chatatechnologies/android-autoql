package chata.can.chata_ai.view.typing

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class MigrateAutoComplete: AppCompatAutoCompleteTextView
{
	var mText: CharSequence = ""
	var mIndex = 0
	var mDelay = 150L
	private var onFinishAnimateListener: OnFinishAnimateListener ?= null

	constructor(context: Context): super(context)

	constructor(context: Context, attrs: AttributeSet): super(context, attrs)

	private val mHandler = Handler(Looper.getMainLooper())

	private val characterAdder = object: Runnable
	{
		override fun run()
		{
			setText(mText.subSequence(0, mIndex++))
			if (mIndex <= mText.length)
			{
				mHandler.postDelayed(this, mDelay)
			}
			else
			{
				onFinishAnimateListener?.onFinishAnimate()
			}
		}
	}

	fun setFinishAnimationListener(onFinishAnimateListener: () -> Unit)
	{
		this.onFinishAnimateListener = object : OnFinishAnimateListener
		{
			override fun onFinishAnimate()
			{
				onFinishAnimateListener()
			}
		}
	}

	fun animateText(txt: CharSequence)
	{
		mText = txt
		mIndex = 0
		setText("")
		mHandler.removeCallbacks(characterAdder)
		mHandler.postDelayed(characterAdder, mDelay)
	}

	fun setCharacterDelay(m: Long)
	{
		mDelay = m
	}
}