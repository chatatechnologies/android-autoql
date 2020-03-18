package chata.can.chata_ai.request

import android.text.TextUtils

class Header(
	val mName: String,
	val mValue: String)
{
	override fun equals(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false

		val header = other as Header
		return TextUtils.equals(mName, header.mName) && TextUtils.equals(mValue, header.mValue)
	}

	override fun hashCode(): Int
	{
		var result = mName.hashCode()
		result = 31 * result + mValue.hashCode()
		return result
	}

	override fun toString() = "Header[name=$mName,value=$mValue]"
}