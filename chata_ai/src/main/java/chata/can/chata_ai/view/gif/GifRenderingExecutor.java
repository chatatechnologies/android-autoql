package chata.can.chata_ai.view.gif;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GifRenderingExecutor extends ScheduledThreadPoolExecutor {

	// Lazy initialization via inner-class holder
	private static final class InstanceHolder {
		private static final GifRenderingExecutor INSTANCE = new GifRenderingExecutor();
	}

	static GifRenderingExecutor getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private GifRenderingExecutor() {
		super(1, new DiscardPolicy());
	}
}
