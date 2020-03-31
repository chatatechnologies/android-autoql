package chata.can.chata_ai.view.relinker;

public final class TextUtils
{
	/**
	 * Returns true if the string is null or 0-length.
	 *
	 * @param str the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}
}
