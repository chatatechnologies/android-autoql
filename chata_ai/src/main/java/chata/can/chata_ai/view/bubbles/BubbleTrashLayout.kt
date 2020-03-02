package chata.can.chata_ai.view.bubbles

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import chata.can.chata_ai.R

class BubbleTrashLayout: BubbleBaseLayout
{
	private val vibrationDurationInMS = 70L
	private var magnetismApplied = false
	private var attachedToWindow = false

	constructor(context: Context): super(context)

	constructor(context: Context, attrs: AttributeSet): super(context, attrs)

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr)

	override fun onAttachedToWindow()
	{
		super.onAttachedToWindow()
		attachedToWindow = true
	}

	override fun onDetachedFromWindow()
	{
		super.onDetachedFromWindow()
		attachedToWindow = false
	}

	override fun setVisibility(visibility: Int)
	{
		if (attachedToWindow)
		{
			if (visibility != getVisibility())
			{
				if (visibility == VISIBLE)
				{
					playAnimation(R.animator.bubble_trash_shown_animator)
				}
				else
				{
					playAnimation(R.animator.bubble_trash_hide_animator)
				}
			}
		}
		super.setVisibility(visibility)
	}

	fun applyMagnetism()
	{
		if (!magnetismApplied)
		{
			magnetismApplied = true
			playAnimation(R.animator.bubble_trash_shown_magnetism_animator)
		}
	}

	fun vibrate()
	{
		(context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator)?.let {
			if (Build.VERSION.SDK_INT >= 26)
			{
				it.vibrate(VibrationEffect.createOneShot(
					vibrationDurationInMS, VibrationEffect.DEFAULT_AMPLITUDE))
			}
			else
			{
				@Suppress("DEPRECATION")
				it.vibrate(vibrationDurationInMS)
			}
		}
	}

	fun releaseMagnetism()
	{
		if (magnetismApplied)
		{
			magnetismApplied = false
			playAnimation(R.animator.bubble_trash_hide_magnetism_animator)
		}
	}

	private fun playAnimation(animationResourceId: Int)
	{
		if (!isInEditMode)
		{
			(AnimatorInflater.loadAnimator(context, animationResourceId) as? AnimatorSet)?.let {
				it.setTarget(getChildAt(0))
				it.start()
			}
		}
	}
}