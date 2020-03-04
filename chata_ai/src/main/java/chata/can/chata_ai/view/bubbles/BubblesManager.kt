package chata.can.chata_ai.view.bubbles

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

open class BubblesManager(context: Context)
{
	companion object {
		private var INSTANCE: BubblesManager ?= null

		fun getInstance(context: Context): BubblesManager?
		{
			if (INSTANCE == null)
			{
				INSTANCE = BubblesManager(context)
			}
			return INSTANCE
		}
	}

	private var context: Context ?= context
	private var bounded = true
	private var bubblesService: BubblesService ?= null
	private var trashLayoutResourceId = 0
	private var listener: OnInitializedCallback ?= null

	private val bubbleServiceConnection = object: ServiceConnection
	{
		override fun onServiceConnected(name: ComponentName?, service: IBinder?)
		{
			service?.let {
				(it as? BubblesService.BubblesServiceBinder)?.let {
						binder ->
					bubblesService = binder.getService()
					configureBubblesService()
					bounded = true
					listener?.onInitialized()
				}
			}
		}

		override fun onServiceDisconnected(name: ComponentName?)
		{
			bounded = false
		}
	}

	private fun configureBubblesService()
	{
		bubblesService?.addTrash(trashLayoutResourceId)
	}

	fun initialize()
	{
		context?.bindService(
			Intent(context, BubblesService::class.java),
			bubbleServiceConnection,
			Context.BIND_AUTO_CREATE)
	}

	fun recycle()
	{
		context?.unbindService(bubbleServiceConnection)
	}

	fun addBubble(bubble: BubbleLayout, x: Int, y: Int)
	{
		if (bounded)
		{
			bubblesService?.addBubble(bubble, x, y)
		}
	}

	fun removeBubble(bubble: BubbleLayout)
	{
		if (bounded)
		{
			bubblesService?.removeBubble(bubble)
		}
	}

	class Builder(context: Context) {
		private var bubblesManager: BubblesManager ?= null

		init {
			bubblesManager = getInstance(context)
		}

		fun setInitializationCallback(listener: () -> Unit): Builder
		{
			bubblesManager?.listener = object: OnInitializedCallback {
				override fun onInitialized()
				{
					listener()
				}
			}
			return this
		}

		fun setTrashLayout(trashLayoutResourceId: Int): Builder
		{
			bubblesManager?.trashLayoutResourceId = trashLayoutResourceId
			return this
		}

		fun build(): BubblesManager?
		{
			return bubblesManager
		}
	}
}