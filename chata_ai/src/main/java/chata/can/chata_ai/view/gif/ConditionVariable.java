package chata.can.chata_ai.view.gif;

public class ConditionVariable
{
	private volatile boolean mCondition;

	synchronized void set(boolean state) {
		if (state) {
			open();
		} else {
			close();
		}
	}

	synchronized void open() {
		boolean old = mCondition;
		mCondition = true;
		if (!old) {
			this.notify();
		}
	}

	synchronized void close() {
		mCondition = false;
	}

	synchronized void block() throws InterruptedException {
		while (!mCondition) {
			this.wait();
		}
	}
}
